package jdev.kovalev.BankCardSysManagment.service;

import jdev.kovalev.BankCardSysManagment.dto.request.CardInfoRequestDto;
import jdev.kovalev.BankCardSysManagment.dto.response.AdminCardInfoResponseDto;

import java.util.List;

public interface AdminCardService {
    AdminCardInfoResponseDto getCardInformationById(String cardId);

    AdminCardInfoResponseDto createCard(CardInfoRequestDto cardInfoRequestDto);

    String changeStatus(String cardId, String status);

    String delete(String cardId);

    List<AdminCardInfoResponseDto> getAllCards();
}
