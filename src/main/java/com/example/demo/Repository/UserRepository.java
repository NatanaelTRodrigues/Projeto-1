package com.example.demo.repository; // PACOTE CORRETO

import com.example.demo.model.User; // Importa o model do pacote correto
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    java.util.Optional<User> findByUsername(String username);
}