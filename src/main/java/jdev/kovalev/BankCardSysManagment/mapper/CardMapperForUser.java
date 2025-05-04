package jdev.kovalev.BankCardSysManagment.mapper;

import jdev.kovalev.BankCardSysManagment.dto.response.UserCardInfo;
import jdev.kovalev.BankCardSysManagment.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CardMapperForUser {
    @Mapping(source = "cardNumber", target = "cardNumber", qualifiedByName = "maskIt")
    @Mapping(source = "cardStatus", target = "cardStatus")
    UserCardInfo fromCardToUserCardInfo(Card card);

    @Named("maskIt")
    default String maskIt(String cardNumber) {
        String[] parts = cardNumber.split(" ");
        for (int i = 0; i < parts.length - 1; i++) {
            parts[i] = "****";
        }
        return String.join(" ", parts);
    }
}
