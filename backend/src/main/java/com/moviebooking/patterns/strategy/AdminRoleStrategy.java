package com.moviebooking.patterns.strategy;

import java.util.Set;

public class AdminRoleStrategy implements RoleStrategy {
    private static final Set<String> PERMISSIONS = Set.of(
            "VIEW_DASHBOARD", "VIEW_BOOKINGS", "CANCEL_BOOKING",
            "MANAGE_MOVIES", "EXPORT_CSV", "VIEW_REVENUE");

    @Override public String getRoleName() { return "ADMIN"; }
    @Override public Set<String> getPermissions() { return PERMISSIONS; }
    @Override public boolean canPerform(String a) { return PERMISSIONS.contains(a); }
}
