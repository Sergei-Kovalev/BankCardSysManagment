package jdev.kovalev.BankCardSysManagment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserInfoResponseDto {
    private UUID userId;
    private String firstAndLastName;
    private String role;
}
