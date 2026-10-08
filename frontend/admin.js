const API = "https://movie-ticket-booking-backend-9jof.onrender.com/api";
let auth = { username: null, role: null, permissions: [] };

document.getElementById("loginBtn").addEventListener("click", async () => {
    const username = document.getElementById("adminUser").value.trim();
    const password = document.getElementById("adminPass").value;
    const role = document.getElementById("adminRole").value;

    if (!username || !password) {
        document.getElementById("loginError").textContent = "Please fill both fields";
        return;
    }

    try {
        const res = await fetch(`${API}/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password, role })
        }).then(r => r.json());

        if (!res.success) {
            document.getElementById("loginError").textContent = res.message || "Login failed";
            return;
        }

        auth = res;
        document.getElementById("loginScreen").classList.add("hidden");
        document.getElementById("dashboard").classList.remove("hidden");
        document.getElementById("loggedUser").textContent = res.username;
        document.getElementById("loggedRole").textContent = res.role;

        applyPermissions(res.permissions);
        loadStats();
        loadBookings();
    } catch (e) {
        document.getElementById("loginError").textContent = "Backend not running?";
        console.error(e);
    }
});

function applyPermissions(permissions) {
    const has = p => permissions.includes(p);
    document.querySelector('[data-tab="bookings"]').style.display = has("VIEW_BOOKINGS") ? "" : "none";
    document.querySelector('[data-tab="analytics"]').style.display = has("VIEW_REVENUE") ? "" : "none";
    document.querySelector('[data-tab="tools"]').style.display   = has("EXPORT_CSV") ? "" : "none";
}

document.getElementById("logoutBtn").addEventListener("click", () => location.reload());

document.querySelectorAll(".nav-item").forEach(item => {
    item.addEventListener("click", () => {
        document.querySelectorAll(".nav-item").forEach(i => i.classList.remove("active"));
        document.querySelectorAll(".tab").forEach(t => t.classList.remove("active"));
        item.classList.add("active");
        document.getElementById("tab-" + item.dataset.tab).classList.add("active");
        if (item.dataset.tab === "overview") loadStats();
        if (item.dataset.tab === "bookings") loadBookings();
        if (item.dataset.tab === "analytics") loadStats();
    });
});

async function loadStats() {
    try {
        const data = await fetch(`${API}/stats`).then(r => r.json());
        document.getElementById("kpiTotal").textContent = data.totalBookings;
        document.getElementById("kpiConfirmed").textContent = data.confirmedBookings;
        document.getElementById("kpiCancelled").textContent = data.cancelledBookings;
        document.getElementById("kpiRevenue").textContent = "₹" + data.totalRevenue.toFixed(0);

        const recentBookings = await fetch(`${API}/bookings`).then(r => r.json());
        renderRecent(recentBookings.slice(0, 5));

        renderBarChart("chartTicketType", data.bookingsByTicketType);
        renderBarChart("chartPayment", data.bookingsByPayment);
        renderBarChart("chartMovie", data.bookingsByMovie);
    } catch (e) { console.error(e); }
}

function renderRecent(bookings) {
    const tbody = document.getElementById("recentBookingsBody");
    if (!bookings.length) {
        tbody.innerHTML = `<tr><td colspan="5" style="text-align:center;color:#666">No bookings yet</td></tr>`;
        return;
    }
    tbody.innerHTML = bookings.map(b => `
        <tr>
            <td>${b.bookingId}</td>
            <td>${b.movieTitle}</td>
            <td>${(b.seats || []).join(", ")}</td>
            <td>₹${b.totalAmount}</td>
            <td><span class="status-badge ${b.status}">${b.status}</span></td>
        </tr>
    `).join("");
}

async function loadBookings() {
    const status = document.getElementById("statusFilter")?.value || "";
    const url = status ? `${API}/bookings?status=${status}` : `${API}/bookings`;

    try {
        const list = await fetch(url).then(r => r.json());
        const tbody = document.getElementById("bookingsBody");

        if (!list.length) {
            tbody.innerHTML = `<tr><td colspan="10" style="text-align:center;color:#666">No bookings found</td></tr>`;
            return;
        }

        tbody.innerHTML = list.map(b => `
            <tr>
                <td><b>${b.bookingId}</b></td>
                <td>${b.movieTitle}</td>
                <td>${b.showTime}</td>
                <td>${b.ticketType}</td>
                <td>${(b.seats || []).join(", ")}</td>
                <td>${(b.addOns || []).join(", ") || "—"}</td>
                <td>₹${b.totalAmount}</td>
                <td>${b.paymentMethod}</td>
                <td><span class="status-badge ${b.status}">${b.status}</span></td>
                <td>${b.status === "CONFIRMED"
                    ? `<button class="cancel-btn" onclick="cancelBooking('${b.bookingId}')">Cancel</button>`
                    : "—"}</td>
            </tr>
        `).join("");
    } catch (e) { console.error(e); }
}

window.cancelBooking = async function (id) {
    if (!auth.permissions.includes("CANCEL_BOOKING")) {
        alert("You don't have permission to cancel bookings.");
        return;
    }
    const reason = prompt("Reason for cancellation:", "Customer request");
    if (reason === null) return;

    try {
        await fetch(`${API}/bookings/${id}/cancel?reason=${encodeURIComponent(reason)}`, {
            method: "POST"
        });
        loadBookings();
        loadStats();
    } catch (e) { alert("Cancel failed"); console.error(e); }
};

document.getElementById("statusFilter")?.addEventListener("change", loadBookings);
document.getElementById("refreshBtn")?.addEventListener("click", () => { loadBookings(); loadStats(); });

function renderBarChart(containerId, dataMap) {
    const el = document.getElementById(containerId);
    if (!dataMap || Object.keys(dataMap).length === 0) {
        el.innerHTML = `<p class="muted">No data yet</p>`;
        return;
    }
    const entries = Object.entries(dataMap);
    const max = Math.max(...entries.map(([, v]) => v));

    el.innerHTML = entries.map(([k, v]) => `
        <div class="bar-row">
            <div class="bar-label">${k}</div>
            <div class="bar-track"><div class="bar-fill" style="width:${(v / max) * 100}%"></div></div>
            <div class="bar-value">${v}</div>
        </div>
    `).join("");
}

document.getElementById("exportCsvBtn")?.addEventListener("click", async () => {
    if (!auth.permissions.includes("EXPORT_CSV")) { alert("No permission"); return; }
    try {
        const blob = await fetch(`${API}/export/csv`).then(r => r.blob());
        const url = URL.createObjectURL(blob);
        const a = document.createElement("a");
        a.href = url;
        a.download = "bookings.csv";
        a.click();
        URL.revokeObjectURL(url);
    } catch (e) { alert("Export failed"); console.error(e); }
});

document.getElementById("resetBtn")?.addEventListener("click", async () => {
    if (!confirm("⚠️ Delete ALL bookings? This cannot be undone.")) return;
    try {
        await fetch(`${API}/reset`, { method: "POST" });
        loadStats();
        loadBookings();
        alert("✅ All bookings cleared.");
    } catch (e) { alert("Reset failed"); console.error(e); }
});
