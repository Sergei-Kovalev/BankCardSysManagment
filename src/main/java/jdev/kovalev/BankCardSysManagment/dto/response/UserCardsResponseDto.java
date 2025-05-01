package jdev.kovalev.BankCardSysManagment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCardsResponseDto {
    private Integer totalPages;
    private List<UserCardInfo> cards;
}
