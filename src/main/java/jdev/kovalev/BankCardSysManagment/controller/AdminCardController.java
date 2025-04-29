package jdev.kovalev.BankCardSysManagment.controller;

import jdev.kovalev.BankCardSysManagment.dto.response.FullCardInfoResponseDto;
import jdev.kovalev.BankCardSysManagment.service.AdminCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/cards")
public class AdminCardController {
    private final AdminCardService adminCardService;

    @GetMapping("/{cardId}")
    public ResponseEntity<FullCardInfoResponseDto> getCardInformationByCardId(@PathVariable UUID cardId) {
        return new ResponseEntity<>(adminCardService.getCardInformationById(cardId), HttpStatus.OK);
    }
}
