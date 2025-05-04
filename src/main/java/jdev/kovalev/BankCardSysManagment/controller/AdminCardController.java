package jdev.kovalev.BankCardSysManagment.controller;

import jdev.kovalev.BankCardSysManagment.dto.request.CardInfoRequestDto;
import jdev.kovalev.BankCardSysManagment.dto.response.AdminCardInfoResponseDto;
import jdev.kovalev.BankCardSysManagment.service.AdminCardService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/cards")
public class AdminCardController {

    private final AdminCardService adminCardService;

    @GetMapping("/{cardId}")
    public ResponseEntity<AdminCardInfoResponseDto> getCardInformationByCardId(@PathVariable @UUID String cardId) {
        return new ResponseEntity<>(adminCardService.getCardInformationById(cardId), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AdminCardInfoResponseDto>> getAllCardsInformation() {
        return new ResponseEntity<>(adminCardService.getAllCards(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AdminCardInfoResponseDto> createCard(@Validated @RequestBody CardInfoRequestDto cardInfoRequestDto) {
        return new ResponseEntity<>(adminCardService.createCard(cardInfoRequestDto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> changeStatus(@RequestParam @UUID String cardId,
                                               @RequestParam String status) {
        return new ResponseEntity<>(adminCardService.changeStatus(cardId, status), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{cardId}", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> deleteCard(@PathVariable @UUID String cardId) {
        return new ResponseEntity<>(adminCardService.delete(cardId), HttpStatus.OK);
    }
}
