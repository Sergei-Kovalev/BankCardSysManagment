package jdev.kovalev.BankCardSysManagment.repository;

import jdev.kovalev.BankCardSysManagment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}
