package jdev.kovalev.BankCardSysManagment.mapper;

import jdev.kovalev.BankCardSysManagment.dto.request.CardInfoRequestDto;
import jdev.kovalev.BankCardSysManagment.dto.response.FullCardInfoResponseDto;
import jdev.kovalev.BankCardSysManagment.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CardMapperForAdmin {
    FullCardInfoResponseDto toFullCardInfoResponseDto(Card card);

    @Mapping(target = "cardId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "cardStatus", expression = "java(CardStatus.valueOf(cardInfoRequestDto.getCardStatus()))")
    Card toCard(CardInfoRequestDto cardInfoRequestDto);
}
