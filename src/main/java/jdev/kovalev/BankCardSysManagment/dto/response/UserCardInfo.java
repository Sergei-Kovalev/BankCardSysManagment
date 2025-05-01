package jdev.kovalev.BankCardSysManagment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCardInfo {
    private String cardNumber;
    private LocalDate expirationDate;
    private String cardStatus;
    private BigDecimal balance;
}
