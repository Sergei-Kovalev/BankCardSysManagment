package jdev.kovalev.BankCardSysManagment.controller;

import jdev.kovalev.BankCardSysManagment.dto.response.UserCardsResponseDto;
import jdev.kovalev.BankCardSysManagment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<UserCardsResponseDto> getAllCardsInformation(@RequestHeader @UUID String userId,
                                                                       @RequestParam(required = false) LocalDate expireFrom,
                                                                       @RequestParam(required = false) LocalDate expireTo,
                                                                       @RequestParam(required = false) String cardStatus,
                                                                       @RequestParam(defaultValue = "1") int page,
                                                                       @RequestParam(defaultValue = "5") int pageSize) {
        return new ResponseEntity<>(userService.getAllCards(userId, expireFrom, expireTo, cardStatus, page, pageSize), HttpStatus.OK);
    }

    @PutMapping(value = "/block/{cardId}", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> blockCard(@RequestHeader @UUID String userId, @PathVariable @UUID String cardId) {
        return new ResponseEntity<>(userService.block(userId, cardId), HttpStatus.OK);
    }

    @PutMapping(value = "/transfer", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> transferBetweenCards(@RequestHeader @UUID String userId,
                                                       @RequestParam @UUID String idCardFrom,
                                                       @RequestParam @UUID String idCardTo,
                                                       @RequestParam BigDecimal amount) {
        return new ResponseEntity<>(userService.transfer(userId, idCardFrom, idCardTo, amount), HttpStatus.OK);
    }

    @GetMapping(value = "/balance/{cardId}", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> getBalance(@RequestHeader @UUID String userId, @PathVariable @UUID String cardId) {
        return new ResponseEntity<>(userService.getBalance(userId, cardId), HttpStatus.OK);
    }
}
