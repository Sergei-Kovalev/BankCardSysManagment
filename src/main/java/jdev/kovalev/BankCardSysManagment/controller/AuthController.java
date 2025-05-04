package jdev.kovalev.BankCardSysManagment.controller;

import jdev.kovalev.BankCardSysManagment.dto.request.JwtRequestDto;
import jdev.kovalev.BankCardSysManagment.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<?> createToken(@Validated @RequestBody JwtRequestDto requestDto) {
        return ResponseEntity.ok(authService.createAuthToken(requestDto));
    }
}
