package com.moviebooking.patterns.strategy;

import java.util.Set;

public interface RoleStrategy {
    String getRoleName();
    Set<String> getPermissions();
    boolean canPerform(String action);
}
