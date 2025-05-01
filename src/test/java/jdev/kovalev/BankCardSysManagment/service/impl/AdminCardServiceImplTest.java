package jdev.kovalev.BankCardSysManagment.service.impl;

import jdev.kovalev.BankCardSysManagment.dto.request.CardInfoRequestDto;
import jdev.kovalev.BankCardSysManagment.dto.response.FullCardInfoResponseDto;
import jdev.kovalev.BankCardSysManagment.entity.Card;
import jdev.kovalev.BankCardSysManagment.entity.User;
import jdev.kovalev.BankCardSysManagment.entity.enums.CardStatus;
import jdev.kovalev.BankCardSysManagment.exception.CardNotFoundException;
import jdev.kovalev.BankCardSysManagment.exception.UserNotFoundException;
import jdev.kovalev.BankCardSysManagment.exception.WrongCardStatusException;
import jdev.kovalev.BankCardSysManagment.mapper.CardMapperForAdmin;
import jdev.kovalev.BankCardSysManagment.repository.CardRepository;
import jdev.kovalev.BankCardSysManagment.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminCardServiceImplTest {
    @Mock
    private CardRepository cardRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CardMapperForAdmin mapper;
    @InjectMocks
    private AdminCardServiceImpl adminCardService;

    private String cardId;
    private String cardId2;
    private String userId;
    private User user;
    private Card card;
    private Card card2;
    private FullCardInfoResponseDto fullCardInfoResponseDto;
    private FullCardInfoResponseDto fullCardInfoResponseDto2;
    private CardInfoRequestDto cardInfoRequestDto;

    @BeforeEach
    void setUp() {
        cardId = "e619c532-218e-4f2c-8d30-c75cf29e0276";
        cardId2 = "af5f2191-4c4c-4bf3-983d-f02caf7b4c38";
        userId = "e487f331-5b7d-40b9-b49d-b41f94b2c960";
        user = User.builder()
                .userId(UUID.fromString(userId))
                .firstAndLastName("Siarhei Kavaleu")
                .build();
        card = Card.builder()
                .cardId(UUID.fromString(cardId))
                .cardNumber("1230 4560 7890 0120")
                .user(user)
                .expirationDate(LocalDate.now().plusYears(1))
                .cardStatus(CardStatus.ACTIVE)
                .balance(BigDecimal.valueOf(22.2))
                .build();
        card2 = Card.builder()
                .cardId(UUID.fromString(cardId2))
                .cardNumber("9999 4560 7890 9999")
                .user(user)
                .expirationDate(LocalDate.now().plusYears(1))
                .cardStatus(CardStatus.ACTIVE)
                .balance(BigDecimal.valueOf(33.3))
                .build();
        fullCardInfoResponseDto = FullCardInfoResponseDto.builder()
                .cardId(UUID.fromString(cardId))
                .cardNumber("1230 4560 7890 0120")
                .firstAndLastName("Siarhei Kavaleu")
                .expirationDate(LocalDate.now().plusYears(1))
                .cardStatus(CardStatus.ACTIVE.toString())
                .balance(BigDecimal.valueOf(22.2))
                .build();
        fullCardInfoResponseDto2 = FullCardInfoResponseDto.builder()
                .cardId(UUID.fromString(cardId))
                .cardNumber("9999 4560 7890 9999")
                .firstAndLastName("Siarhei Kavaleu")
                .expirationDate(LocalDate.now().plusYears(1))
                .cardStatus(CardStatus.ACTIVE.toString())
                .balance(BigDecimal.valueOf(33.3))
                .build();
        cardInfoRequestDto = CardInfoRequestDto.builder()
                .cardNumber("1230 4560 7890 0120")
                .userId(userId)
                .expirationDate(LocalDate.now().plusYears(1))
                .cardStatus("ACTIVE")
                .balance(BigDecimal.valueOf(22.2))
                .build();
    }

    @Nested
    class GetCardInformationById {
        @Test
        void getCardInformationByIdWhenPresent() {
            when(cardRepository.findById(any()))
                    .thenReturn(Optional.of(card));
            when(mapper.toFullCardInfoResponseDto(card))
                    .thenReturn(fullCardInfoResponseDto);

            FullCardInfoResponseDto actual = adminCardService.getCardInformationById(cardId);

            assertThat(actual)
                    .isEqualTo(fullCardInfoResponseDto);
        }

        @Test
        void getCardInformationByIdWhenNotPresent() {
            when(cardRepository.findById(any()))
                    .thenReturn(Optional.empty());

            assertThatThrownBy(() -> adminCardService.getCardInformationById(cardId))
                    .isInstanceOf(CardNotFoundException.class)
                    .hasMessageContaining("Карта с таким id не найдена в базе данных");
        }
    }

    @Nested
    class GetAllCards {
        @Test
        void getAllCards() {
            when(cardRepository.findAll())
                    .thenReturn(List.of(card, card2));
            when(mapper.toFullCardInfoResponseDto(card))
                    .thenReturn(fullCardInfoResponseDto);
            when(mapper.toFullCardInfoResponseDto(card2))
                    .thenReturn(fullCardInfoResponseDto2);

            List<FullCardInfoResponseDto> actual = adminCardService.getAllCards();

            assertThat(actual)
                    .hasSize(2)
                    .containsExactlyElementsOf(List.of(fullCardInfoResponseDto, fullCardInfoResponseDto2));
        }
    }

    @Nested
    class CreateCard {
        @Test
        void createCardWhenUserPresent() {
            when(userRepository.findById(any()))
                    .thenReturn(Optional.of(user));
            when(mapper.toCard(cardInfoRequestDto))
                    .thenReturn(card);
            when(cardRepository.save(card))
                    .thenReturn(card);
            when(mapper.toFullCardInfoResponseDto(card))
                    .thenReturn(fullCardInfoResponseDto);

            FullCardInfoResponseDto actual = adminCardService.createCard(cardInfoRequestDto);

            assertThat(actual)
                    .isEqualTo(fullCardInfoResponseDto);
        }

        @Test
        void createCardWhenUserNotFound() {
            when(userRepository.findById(any()))
                    .thenReturn(Optional.empty());

            assertThatThrownBy(() -> adminCardService.createCard(cardInfoRequestDto))
                    .isInstanceOf(UserNotFoundException.class)
                    .hasMessageContaining("Пользователь с таким id не найден в базе данных");
        }
    }

    @Nested
    class ChangeStatus {
        @Test
        void changeStatusWhenCardPresent() {
            String expected = String.format("Статус карты с id %s успешно изменен", cardId);

            when(cardRepository.findById(any()))
                    .thenReturn(Optional.of(card));

            String actual = adminCardService.changeStatus(cardId, "ACTIVE");

            assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        void changeStatusWhenCardNotPresent() {
            when(cardRepository.findById(any()))
                    .thenReturn(Optional.empty());

            assertThatThrownBy(() -> adminCardService.changeStatus(cardId, "ACTIVE"))
                    .isInstanceOf(CardNotFoundException.class)
                    .hasMessageContaining("Карта с таким id не найдена в базе данных");
        }

        @Test
        void changeStatusWhenWrongStatus() {
            when(cardRepository.findById(any()))
                    .thenReturn(Optional.of(card));

            assertThatThrownBy(() -> adminCardService.changeStatus(cardId, "BLA BLA BLA"))
                    .isInstanceOf(WrongCardStatusException.class)
                    .hasMessageContaining("Статус может быть только ACTIVE, BLOCKED или EXPIRED");
        }
    }

    @Nested
    class Delete {
        @Test
        void delete() {
            String expected = String.format("Карта с ID: %s успешно удалёна", cardId);

            String actual = adminCardService.delete(cardId);

            assertThat(actual).isEqualTo(expected);
        }
    }
}