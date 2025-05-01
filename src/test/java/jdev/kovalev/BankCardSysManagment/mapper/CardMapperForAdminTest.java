package jdev.kovalev.BankCardSysManagment.mapper;

import jdev.kovalev.BankCardSysManagment.dto.request.CardInfoRequestDto;
import jdev.kovalev.BankCardSysManagment.dto.response.FullCardInfoResponseDto;
import jdev.kovalev.BankCardSysManagment.entity.Card;
import jdev.kovalev.BankCardSysManagment.entity.User;
import jdev.kovalev.BankCardSysManagment.entity.enums.CardStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CardMapperForAdminTest {
    private final CardMapperForAdmin mapper = new CardMapperForAdminImpl();

    private String cardId;
    private String userId;
    private User user;
    private Card card;
    private FullCardInfoResponseDto fullCardInfoResponseDto;
    private CardInfoRequestDto cardInfoRequestDto;

    @BeforeEach
    void setUp() {
        cardId = "e619c532-218e-4f2c-8d30-c75cf29e0276";
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
        fullCardInfoResponseDto = FullCardInfoResponseDto.builder()
                .cardId(UUID.fromString(cardId))
                .cardNumber("1230 4560 7890 0120")
                .firstAndLastName("Siarhei Kavaleu")
                .expirationDate(LocalDate.now().plusYears(1))
                .cardStatus(CardStatus.ACTIVE.toString())
                .balance(BigDecimal.valueOf(22.2))
                .build();
        cardInfoRequestDto = CardInfoRequestDto.builder()
                .cardNumber("1230 4560 7890 0120")
                .userId(userId)
                .expirationDate(LocalDate.now().plusYears(1))
                .cardStatus("ACTIVE")
                .balance(BigDecimal.valueOf(22.2))
                .build();
    }

    @Test
    void toFullCardInfoResponseDto() {
        FullCardInfoResponseDto actual = mapper.toFullCardInfoResponseDto(card);

        assertThat(actual).isEqualTo(fullCardInfoResponseDto);
    }

    @Test
    void toCard() {
        card.setCardId(null);
        card.setUser(null);

        Card actual = mapper.toCard(cardInfoRequestDto);

        assertThat(actual).isEqualTo(card);
    }

}