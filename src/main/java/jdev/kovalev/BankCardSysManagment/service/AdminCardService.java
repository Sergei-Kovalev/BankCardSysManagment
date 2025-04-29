package jdev.kovalev.BankCardSysManagment.service;

import jdev.kovalev.BankCardSysManagment.dto.response.FullCardInfoResponseDto;

import java.util.UUID;

public interface AdminCardService {
    FullCardInfoResponseDto getCardInformationById(UUID cardId);
}
