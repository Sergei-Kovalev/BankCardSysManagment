package jdev.kovalev.BankCardSysManagment.mapper;

import jdev.kovalev.BankCardSysManagment.dto.request.CardInfoRequestDto;
import jdev.kovalev.BankCardSysManagment.dto.response.AdminCardInfoResponseDto;
import jdev.kovalev.BankCardSysManagment.entity.Card;
import jdev.kovalev.BankCardSysManagment.entity.enums.CardStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {CardStatus.class})
public interface CardMapperForAdmin {
    @Mapping(target = "firstAndLastName", expression = "java(card.getUser().getFirstAndLastName())")
    AdminCardInfoResponseDto toFullCardInfoResponseDto(Card card);

    @Mapping(target = "cardId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "cardStatus", expression = "java(CardStatus.valueOf(cardInfoRequestDto.getCardStatus()))")
    Card toCard(CardInfoRequestDto cardInfoRequestDto);
}
