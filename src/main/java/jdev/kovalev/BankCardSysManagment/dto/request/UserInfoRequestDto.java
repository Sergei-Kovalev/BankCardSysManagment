package jdev.kovalev.BankCardSysManagment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoRequestDto {
    @NotBlank(message = "Фамилия и имя пользователя не могут быть пустыми")
    @Length(max = 50)
    private String firstAndLastName;
}
