package jdev.kovalev.BankCardSysManagment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jdev.kovalev.BankCardSysManagment.entity.enums.UserRole;
import jdev.kovalev.BankCardSysManagment.utils.annotations.EnumValue;
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

    @NotBlank(message = "Username не может быть пустым")
    @Length(max = 50)
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")
    @Length(max = 100)
    private String password;

    @NotNull(message = "Роль пользователя не может быть пустым")
    @EnumValue(enumClass = UserRole.class, message = "Роль пользователя может быть только ROLE_USER или ROLE_ADMIN")
    private String role;
}
