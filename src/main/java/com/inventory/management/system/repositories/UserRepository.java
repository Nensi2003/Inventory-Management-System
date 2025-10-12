package com.inventory.management.system.repositories;

import com.inventory.management.system.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    //Per te marre te gjithe adminat
    List<User> findByRole(String role);
    boolean existsByEmail(String email);
    User findByUsername(String username);




}
