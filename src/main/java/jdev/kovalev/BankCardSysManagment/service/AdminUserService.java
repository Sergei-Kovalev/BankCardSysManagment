package jdev.kovalev.BankCardSysManagment.service;

import jdev.kovalev.BankCardSysManagment.dto.request.UserInfoRequestDto;
import jdev.kovalev.BankCardSysManagment.dto.response.FullUserInfoResponseDto;

import java.util.List;
import java.util.UUID;

public interface AdminUserService {
    FullUserInfoResponseDto getUserInformationById(UUID userId);
    List<FullUserInfoResponseDto> getAllUsers();
    FullUserInfoResponseDto createUser(UserInfoRequestDto userInfoRequestDto);
    FullUserInfoResponseDto updateUser(UUID userId, UserInfoRequestDto userInfoRequestDto);
    String deleteUser(UUID userId);
}
