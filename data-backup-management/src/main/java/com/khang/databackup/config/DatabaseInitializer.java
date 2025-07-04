package com.khang.databackup.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.khang.databackup.entity.User;
import com.khang.databackup.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>> STARTING DATABASE INITIALIZATION <<<");
        long countUsers = this.userRepository.count();

        if (countUsers == 0) {
            List<User> arr = new ArrayList<>();
            arr.add(new User("Khang", "Nguyễn", "hoangkhang16112003@gmail.com"));
            arr.add(new User("Thịnh", "Lê", "thinhle0204@gmail.com"));
            arr.add(new User("Trang", "Lâm", "tranglam2908@gmail.com"));
            arr.add(new User("Oanh", "Trần", "oanhtran0105@gmail.com"));
            arr.add(new User("Ân", "Nguyễn", "annguyen0101@gmail.com"));

            this.userRepository.saveAll(arr);

        }

        if (countUsers > 0) {
            System.out.println(">>> DATABASE INITIALIZATION SKIPPED <<<");

        } else {
            System.out.println(">>> DATABASE INITIALIZATION COMPLETED <<<");
        }
    }

}
