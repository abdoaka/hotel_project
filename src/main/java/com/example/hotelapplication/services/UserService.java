//package com.example.hotelapplication.services;
//
//import com.example.hotelapplication.entities.Client;
//import com.example.hotelapplication.entities.User;
//import com.example.hotelapplication.repositories.UserRepository;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import java.util.Optional;
//
//@Service
//public class UserService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    ///////////// Spring Security Authentication /////////////
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
//    }
//
//    ///////////// Basic CRUD Operations /////////////
//    public User saveUser(User user) {
//        return userRepository.save(user);
//    }
//
//    public Optional<User> getUserById(Long id) {
//        return userRepository.findById(id);
//    }
//
//    public void deleteUser(Long id) {
//        userRepository.deleteById(id);
//    }
//
//    ///////////// Link User to Client /////////////
//    public void linkUserToClient(User user, Client client) {
//        user.setClient(client); // Uses setClient() (not setClientId)
//        userRepository.save(user);
//    }
//
//    // Optional: Find user by linked client
//    public Optional<User> findByClientId(Long clientId) {
//        return userRepository.findByClientId(clientId); // Now returns Optional<User>
//    }
//
//}