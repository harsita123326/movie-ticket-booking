const API = "https://movie-ticket-booking-backend-9jof.onrender.com/api";
let selectedMovie=null,selectedShow=null,selectedSeats=[],movies=[];

window.addEventListener("DOMContentLoaded", async ()=>{
    movies = await fetch(`${API}/movies`).then(r=>r.json());
    const ms = document.getElementById("movieSelect");
    ms.innerHTML = movies.map(m=>`<option value="${m.id}">${m.title} (${m.language})</option>`).join("");
    ms.addEventListener("change", loadShows);
    await loadShows();
    renderSeats();
    document.getElementById("bookBtn").addEventListener("click", book);
});

async function loadShows(){
    const id = document.getElementById("movieSelect").value;
    selectedMovie = movies.find(m=>m.id==id);
    const shows = await fetch(`${API}/shows/${id}`).then(r=>r.json());
    document.getElementById("showList").innerHTML = shows.map(s=>
        `<button class="show-btn" data-time="${s.time}" data-price="${s.basePrice}">
            ${s.time} (₹${s.basePrice})
        </button>`).join("");
    selectedShow=null;
    document.querySelectorAll(".show-btn").forEach(b=>b.addEventListener("click",()=>{
        document.querySelectorAll(".show-btn").forEach(x=>x.classList.remove("active"));
        b.classList.add("active");
        selectedShow={time:b.dataset.time, price:parseFloat(b.dataset.price)};
    }));
}

const ROWS=["A","B","C","D","E"], COLS=8;
function renderSeats(){
    const g=document.getElementById("seatGrid"); g.innerHTML="";
    ROWS.forEach(r=>{for(let c=1;c<=COLS;c++){
        const id=r+c, d=document.createElement("div");
        d.className="seat"; d.textContent=id;
        if(Math.random()<0.15) d.classList.add("occupied");
        else d.addEventListener("click",()=>toggleSeat(d,id));
        g.appendChild(d);
    }});
}

function toggleSeat(el,id){
    if(selectedSeats.includes(id)){selectedSeats=selectedSeats.filter(s=>s!==id);el.classList.remove("selected");}
    else{selectedSeats.push(id);el.classList.add("selected");}
    document.getElementById("selectedSeats").textContent = selectedSeats.join(", ")||"None";
}

async function book(){
    if(!selectedShow) return alert("Select a show!");
    if(selectedSeats.length===0) return alert("Select at least one seat!");
    const addOns=[];
    if(document.getElementById("popcorn").checked) addOns.push("POPCORN");
    if(document.getElementById("drink").checked) addOns.push("DRINK");
    const req={
        movieTitle:selectedMovie.title, showTime:selectedShow.time,
        ticketType:document.querySelector('input[name="ticketType"]:checked').value,
        seats:selectedSeats, addOns,
        paymentMethod:document.getElementById("paymentMethod").value,
        userEmail:document.getElementById("userEmail").value,
        userPhone:document.getElementById("userPhone").value
    };
    try{
        const data = await fetch(`${API}/book`,{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify(req)}).then(r=>r.json());
        showResult(data);
    }catch(e){ alert("Backend not running?"); console.error(e); }
}

function showResult(d){
    const el=document.getElementById("result");
    el.classList.remove("hidden");
    el.innerHTML=`
        <h3>✅ Booking Confirmed!</h3>
        <p><b>Booking ID:</b> ${d.bookingId}</p>
        <p><b>Movie:</b> ${d.movieTitle}</p>
        <p><b>Show:</b> ${d.showTime}</p>
        <p><b>Ticket:</b> ${d.ticketType}</p>
        <p><b>Seats:</b> ${d.seats.join(", ")}</p>
        <p><b>Add-ons:</b> ${d.addOns.length?d.addOns.join(", "):"None"}</p>
        <p><b>Payment:</b> ${d.paymentMethod}</p>
        <p><b>Total:</b> ₹${d.totalAmount}</p>
        <p><b>Status:</b> ${d.status}</p>
        <h4>🔔 Notifications (Observer Pattern)</h4>
        <ul>${d.notifications.map(n=>`<li>${n}</li>`).join("")}</ul>`;
    el.scrollIntoView({behavior:"smooth"});
}
