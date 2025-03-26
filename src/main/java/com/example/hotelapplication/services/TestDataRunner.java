package com.example.hotelapplication.services;

import com.example.hotelapplication.entities.Chambre;
import com.example.hotelapplication.repositories.ChambreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestDataRunner {

    @Bean
    public CommandLineRunner demo(ChambreRepository chambreRepository) {
        return args -> {
            // Insert a test room
            Chambre testRoom = new Chambre();
            testRoom.setRoomNumber("50");
            testRoom.setType("Deluxeeeee");
            testRoom.setPrice(1550.00);
            testRoom.setStatus("AVAILABLE");

            chambreRepository.save(testRoom);
            System.out.println("Test room inserted!");
        };
    }
}