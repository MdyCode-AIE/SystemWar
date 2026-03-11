package com.example.systemwar;

import com.example.systemwar.entities.LeaveType;
import com.example.systemwar.entities.Role;
import com.example.systemwar.entities.User;
import com.example.systemwar.repositories.LeaveTypeRepository;
import com.example.systemwar.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final LeaveTypeRepository leaveTypeRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor injection is highly recommended in Spring
    public DatabaseSeeder(UserRepository userRepository,
                          LeaveTypeRepository leaveTypeRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.leaveTypeRepository = leaveTypeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        // 1. Seed Leave Types (Only if table is empty)
        if (leaveTypeRepository.count() == 0) {
            LeaveType annual = new LeaveType();
            annual.setName("Annual Leave");

            LeaveType medical = new LeaveType();
            medical.setName("Medical Leave");

            LeaveType emergency = new LeaveType();
            emergency.setName("Emergency Leave");

            leaveTypeRepository.saveAll(Arrays.asList(annual, medical, emergency));
            System.out.println("✅ Default Leave Types seeded.");
        }

        // 2. Seed Test Users (Only if table is empty)
        if (userRepository.count() == 0) {

            // Create an Admin
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123")); // Spring Security needs this encoded!
            admin.setRole(Role.ADMIN);

            // Create a standard Employee User
            User aieman = new User();
            aieman.setUsername("aieman");
            aieman.setPassword(passwordEncoder.encode("user123")); // Spring Security needs this encoded!
            aieman.setRole(Role.USER);

            userRepository.saveAll(Arrays.asList(admin, aieman));
            System.out.println("✅ Test Users seeded.");
        }
    }
}
