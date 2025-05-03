package jdev.kovalev.BankCardSysManagment.service;

import jdev.kovalev.BankCardSysManagment.dto.request.UserInfoRequestDto;
import jdev.kovalev.BankCardSysManagment.dto.response.AdminUserInfoResponseDto;

import java.util.List;

public interface AdminUserService {
    AdminUserInfoResponseDto getUserInformationById(String userId);
    List<AdminUserInfoResponseDto> getAllUsers();
    AdminUserInfoResponseDto createUser(UserInfoRequestDto userInfoRequestDto);
    AdminUserInfoResponseDto updateUser(String userId, UserInfoRequestDto userInfoRequestDto);
    String deleteUser(String userId);
}
