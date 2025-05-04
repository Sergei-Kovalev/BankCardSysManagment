package jdev.kovalev.BankCardSysManagment.dto.request;

import lombok.Data;

@Data
public class JwtRequestDto {
    private String username;
    private String password;
}
