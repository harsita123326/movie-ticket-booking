package com.moviebooking.service;

import com.moviebooking.dto.AdminLoginRequest;
import com.moviebooking.entity.AdminUser;
import com.moviebooking.patterns.strategy.*;
import com.moviebooking.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminUserRepository adminUserRepository;

    public Map<String, Object> login(AdminLoginRequest req) {
        Optional<AdminUser> userOpt = adminUserRepository.findByUsername(req.getUsername());
        if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(req.getPassword())) {
            return Map.of("success", false, "message", "Invalid credentials");
        }

        AdminUser user = userOpt.get();

        // STRATEGY PATTERN
        RoleStrategy strategy = "MANAGER".equalsIgnoreCase(user.getRole())
                ? new ManagerRoleStrategy()
                : new AdminRoleStrategy();

        return Map.of(
                "success", true,
                "username", user.getUsername(),
                "role", strategy.getRoleName(),
                "permissions", strategy.getPermissions()
        );
    }
}
