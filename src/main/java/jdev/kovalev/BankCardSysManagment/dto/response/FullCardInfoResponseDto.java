package jdev.kovalev.BankCardSysManagment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FullCardInfoResponseDto {
    private UUID cardId;
    private String cardNumber;
    private String firstAndLastName;
    private LocalDate expirationDate;
    private String cardStatus;
    private BigDecimal balance;
}
