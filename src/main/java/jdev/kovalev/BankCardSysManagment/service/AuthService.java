package jdev.kovalev.BankCardSysManagment.service;

import jdev.kovalev.BankCardSysManagment.dto.request.JwtRequestDto;
import jdev.kovalev.BankCardSysManagment.dto.response.JwtResponseDto;

public interface AuthService {
    JwtResponseDto createAuthToken(JwtRequestDto authRequest);
}
