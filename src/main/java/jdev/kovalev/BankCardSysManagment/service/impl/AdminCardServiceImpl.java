package jdev.kovalev.BankCardSysManagment.service.impl;

import jdev.kovalev.BankCardSysManagment.dto.request.CardInfoRequestDto;
import jdev.kovalev.BankCardSysManagment.dto.response.AdminCardInfoResponseDto;
import jdev.kovalev.BankCardSysManagment.entity.Card;
import jdev.kovalev.BankCardSysManagment.entity.User;
import jdev.kovalev.BankCardSysManagment.entity.enums.CardStatus;
import jdev.kovalev.BankCardSysManagment.exception.CardNotFoundException;
import jdev.kovalev.BankCardSysManagment.exception.UserNotFoundException;
import jdev.kovalev.BankCardSysManagment.mapper.CardMapperForAdmin;
import jdev.kovalev.BankCardSysManagment.repository.CardRepository;
import jdev.kovalev.BankCardSysManagment.repository.UserRepository;
import jdev.kovalev.BankCardSysManagment.service.AdminCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminCardServiceImpl implements AdminCardService {
    private final static String SUCCESSFULLY_CHANGE_STATUS = "Статус карты с id %s успешно изменен";
    private final static String DELETED_SUCCESSFULLY = "Карта с ID: %s успешно удалёна";

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardMapperForAdmin mapper;

    @Override
    public AdminCardInfoResponseDto getCardInformationById(String cardId) {
        return cardRepository.findById(UUID.fromString(cardId))
                .map(mapper::toFullCardInfoResponseDto)
                .orElseThrow(CardNotFoundException::new);
    }

    @Override
    public List<AdminCardInfoResponseDto> getAllCards() {
        return cardRepository.findAll()
                .stream()
                .map(mapper::toFullCardInfoResponseDto)
                .toList();
    }

    @Override
    public AdminCardInfoResponseDto createCard(CardInfoRequestDto cardInfoRequestDto) {
        User user = userRepository.findById(UUID.fromString(cardInfoRequestDto.getUserId()))
                .orElseThrow(UserNotFoundException::new);
        Card card = mapper.toCard(cardInfoRequestDto);
        card.setUser(user);
        Card savedCard = cardRepository.save(card);
        return mapper.toFullCardInfoResponseDto(savedCard);
    }

    @Override
    public String changeStatus(String cardId, String status) {
        Card card = cardRepository.findById(UUID.fromString(cardId))
                .orElseThrow(CardNotFoundException::new);
        card.setCardStatus(CardStatus.valueOf(status.toUpperCase()));
        return String.format(SUCCESSFULLY_CHANGE_STATUS, cardId);
    }

    @Override
    public String delete(String cardId) {
        cardRepository.deleteById(UUID.fromString(cardId));
        return String.format(DELETED_SUCCESSFULLY, cardId);
    }
}
