package jdev.kovalev.BankCardSysManagment.service.impl;

import jdev.kovalev.BankCardSysManagment.dto.response.UserCardsResponseDto;
import jdev.kovalev.BankCardSysManagment.entity.Card;
import jdev.kovalev.BankCardSysManagment.entity.enums.CardStatus;
import jdev.kovalev.BankCardSysManagment.exception.CardNotBelongsToUserException;
import jdev.kovalev.BankCardSysManagment.exception.CardNotFoundException;
import jdev.kovalev.BankCardSysManagment.exception.NotEnoughMoneyException;
import jdev.kovalev.BankCardSysManagment.exception.WrongCardStatusException;
import jdev.kovalev.BankCardSysManagment.mapper.UserCardsResponseMapper;
import jdev.kovalev.BankCardSysManagment.repository.CardRepository;
import jdev.kovalev.BankCardSysManagment.service.UserService;
import jdev.kovalev.BankCardSysManagment.service.specification.CardFilterSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final static String SUCCESSFULLY_BLOCKED = "Карта успешно заблокирована";
    private final static String SUCCESSFULLY_TRANSFERRED = "Сумма успешно переведена между картами";

    private final CardRepository cardRepository;
    private final UserCardsResponseMapper userCardsResponseMapper;

    @Override
    public UserCardsResponseDto getAllCards(String userId, LocalDate expireFrom, LocalDate expireTo,
                                            String cardStatus, int page, int pageSize) {
        Sort sort = Sort.by(Sort.Direction.ASC, "expirationDate");
        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);

        Specification<Card> specification = configureSpecifications(userId, expireFrom, expireTo, cardStatus);

        Page<Card> cardsPage = cardRepository.findAll(specification, pageable);

        return userCardsResponseMapper.toResponseDto(cardsPage.getTotalPages(), cardsPage.getContent());
    }

    @Transactional
    @Override
    public String block(String userId, String cardId) {
        Card card = getCardOrThrow(cardId);
        validateCardBelongsToUser(userId, card);
        return SUCCESSFULLY_BLOCKED;
    }


    @Transactional
    @Override
    public String transfer(String userId, String idCardFrom, String idCardTo, BigDecimal amount) {
        Card cardFrom = getCardOrThrow(idCardFrom);
        Card cardTo = getCardOrThrow(idCardTo);

        validateCardBelongsToUser(userId, cardFrom);
        validateCardBelongsToUser(userId, cardTo);

        validateSufficientBalance(cardFrom, amount);

        cardFrom.setBalance(cardFrom.getBalance().subtract(amount));
        cardTo.setBalance(cardTo.getBalance().add(amount));

        return SUCCESSFULLY_TRANSFERRED;
    }

    @Override
    public String getBalance(String userId, String cardId) {
        Card card = getCardOrThrow(cardId);

        validateCardBelongsToUser(userId, card);

        return card.getBalance().toString();
    }

    private void validateSufficientBalance(Card cardFrom, BigDecimal amount) {
        if (cardFrom.getBalance().compareTo(amount) < 0) {
            throw new NotEnoughMoneyException();
        }
    }

    private static void validateCardBelongsToUser(String userId, Card card) {
        if (card.getUser().getUserId().toString().equals(userId)) {
            card.setCardStatus(CardStatus.BLOCKED);
        } else {
            throw new CardNotBelongsToUserException();
        }
    }

    private Card getCardOrThrow(String cardId) {
        return cardRepository.findById(UUID.fromString(cardId)).orElseThrow(CardNotFoundException::new);
    }

    private Specification<Card> configureSpecifications(String userId, LocalDate expireFrom, LocalDate expireTo, String cardStatus) {
        return CardFilterSpecification.hasUserId(UUID.fromString(userId))
                .and(CardFilterSpecification.hasCardStatus(fromStringToEnumStatus(cardStatus)))
                .and(CardFilterSpecification.hasExpirationDateAfterOrEqual(expireFrom))
                .and(CardFilterSpecification.hasExpirationDateBeforeOrEqual(expireTo));
    }

    private static CardStatus fromStringToEnumStatus(String cardStatus) {
        if (cardStatus == null) {
            return null;
        }
        CardStatus status;
        try {
            status = CardStatus.valueOf(cardStatus.toUpperCase());
        } catch (Exception e) {
            throw new WrongCardStatusException();
        }
        return status;
    }
}
