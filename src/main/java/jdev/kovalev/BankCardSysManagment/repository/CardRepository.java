package jdev.kovalev.BankCardSysManagment.repository;

import jdev.kovalev.BankCardSysManagment.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {
}
