package com.moviebooking.patterns.strategy;

import java.util.Set;

public class ManagerRoleStrategy implements RoleStrategy {
    private static final Set<String> PERMISSIONS = Set.of(
            "VIEW_DASHBOARD", "VIEW_BOOKINGS", "VIEW_REVENUE");

    @Override public String getRoleName() { return "MANAGER"; }
    @Override public Set<String> getPermissions() { return PERMISSIONS; }
    @Override public boolean canPerform(String a) { return PERMISSIONS.contains(a); }
}
