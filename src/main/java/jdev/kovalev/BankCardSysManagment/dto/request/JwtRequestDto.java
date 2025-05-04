package jdev.kovalev.BankCardSysManagment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "DTO запроса на получение JWT.")
public class JwtRequestDto {
    @Schema(description = "Username(login) пользователя",
            example = "Nomad999")
    @NotBlank(message = "Поле username не должно быть пустым")
    private String username;

    @Schema(description = "Пароль пользователя",
            example = "Nomad_999!!!")
    @NotBlank(message = "Поле password не должно быть пустым")
    private String password;
}
