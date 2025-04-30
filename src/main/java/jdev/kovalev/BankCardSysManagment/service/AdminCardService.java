package jdev.kovalev.BankCardSysManagment.service;

import jdev.kovalev.BankCardSysManagment.dto.request.CardInfoRequestDto;
import jdev.kovalev.BankCardSysManagment.dto.response.FullCardInfoResponseDto;

import java.util.List;

public interface AdminCardService {
    FullCardInfoResponseDto getCardInformationById(String cardId);

    FullCardInfoResponseDto createCard(CardInfoRequestDto cardInfoRequestDto);

    String changeStatus(String cardId, String status);

    String delete(String cardId);

    List<FullCardInfoResponseDto> getAllCards();
}
