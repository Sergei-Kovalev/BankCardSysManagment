package jdev.kovalev.BankCardSysManagment.service.impl;

import jdev.kovalev.BankCardSysManagment.dto.request.UserInfoRequestDto;
import jdev.kovalev.BankCardSysManagment.dto.response.AdminUserInfoResponseDto;
import jdev.kovalev.BankCardSysManagment.entity.User;
import jdev.kovalev.BankCardSysManagment.entity.enums.UserRole;
import jdev.kovalev.BankCardSysManagment.exception.UserNotFoundException;
import jdev.kovalev.BankCardSysManagment.mapper.UserMapperForAdmin;
import jdev.kovalev.BankCardSysManagment.repository.UserRepository;
import jdev.kovalev.BankCardSysManagment.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {
    private final static String DELETED_SUCCESSFULLY = "Пользователь с ID: %s успешно удалён";

    private final UserRepository userRepository;
    private final UserMapperForAdmin mapper;


    private Optional<User> findById(String userId) {
        return userRepository.findById(UUID.fromString(userId));
    }

    @Override
    public AdminUserInfoResponseDto getUserInformationById(String userId) {
        return findById(userId)
                .map(mapper::entityToDto)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<AdminUserInfoResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(mapper::entityToDto)
                .toList();
    }

    @Override
    public AdminUserInfoResponseDto createUser(UserInfoRequestDto userInfoRequestDto) {
        User savedUser = userRepository.save(mapper.dtoToEntity(userInfoRequestDto));
        return mapper.entityToDto(savedUser);
    }

    @Transactional
    @Override
    public AdminUserInfoResponseDto updateUser(String userId, UserInfoRequestDto userInfoRequestDto) {
        return findById(userId)
                .map(user -> {
                    user.setFirstAndLastName(userInfoRequestDto.getFirstAndLastName());
                    user.setUsername(userInfoRequestDto.getUsername());
                    user.setPassword(userInfoRequestDto.getPassword());
                    user.setRole(UserRole.valueOf(userInfoRequestDto.getRole()));
                    return mapper.entityToDto(userRepository.save(user));
                })
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public String deleteUser(String userId) {
        userRepository.deleteById(UUID.fromString(userId));
        return String.format(DELETED_SUCCESSFULLY, userId);
    }
}