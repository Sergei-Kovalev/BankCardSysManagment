package jdev.kovalev.BankCardSysManagment.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jdev.kovalev.BankCardSysManagment.entity.enums.CardStatus;
import jdev.kovalev.BankCardSysManagment.utils.annotations.EnumValue;
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
public class CardInfoRequestDto {
    @NotBlank(message = "Поле cardNumber не должно быть пустым")
    @Pattern(regexp = "^\\d{4} \\d{4} \\d{4} \\d{4}$",
            message = "Номер карты должен выглядеть как 16 цифр разделенных пробелом")
    private String cardNumber;

    @NotNull(message = "Введите корректный UUID")
    private String userId;

    @NotNull(message = "Дата окончания действия карты обязательна")
    @FutureOrPresent(message = "Дата окончания действия карты должна быть в будущем или сегодня")
    private LocalDate expirationDate;

    @NotNull(message = "Статус карты обязателен")
    @EnumValue(enumClass = CardStatus.class, message = "Статус может быть только ACTIVE, BLOCKED или EXPIRED")
    private String cardStatus;

    @DecimalMin(value = "0.0", message = "Баланс не может быть отрицательным") // предполагаем, что при создании минуса быть не может
    private BigDecimal balance;
}
