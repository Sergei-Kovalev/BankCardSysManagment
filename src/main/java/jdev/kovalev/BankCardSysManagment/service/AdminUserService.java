package jdev.kovalev.BankCardSysManagment.service;

import jdev.kovalev.BankCardSysManagment.dto.request.UserInfoRequestDto;
import jdev.kovalev.BankCardSysManagment.dto.response.FullUserInfoResponseDto;

import java.util.List;

public interface AdminUserService {
    FullUserInfoResponseDto getUserInformationById(String userId);
    List<FullUserInfoResponseDto> getAllUsers();
    FullUserInfoResponseDto createUser(UserInfoRequestDto userInfoRequestDto);
    FullUserInfoResponseDto updateUser(String userId, UserInfoRequestDto userInfoRequestDto);
    String deleteUser(String userId);
}
