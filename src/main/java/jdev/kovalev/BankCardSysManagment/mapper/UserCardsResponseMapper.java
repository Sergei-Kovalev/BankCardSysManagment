package jdev.kovalev.BankCardSysManagment.mapper;

import jdev.kovalev.BankCardSysManagment.dto.response.UserCardInfo;
import jdev.kovalev.BankCardSysManagment.dto.response.UserCardsResponseDto;
import jdev.kovalev.BankCardSysManagment.entity.Card;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CardMapperForUser.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserCardsResponseMapper {
    @Mapping(target = "totalPages", source = "totalPages")
    @Mapping(target = "cards", source = "cards")
    UserCardsResponseDto toResponseDto(Integer totalPages, List<Card> cards);

    List<UserCardInfo> fromCardsToUserCardInfos(List<Card> cards);
}
