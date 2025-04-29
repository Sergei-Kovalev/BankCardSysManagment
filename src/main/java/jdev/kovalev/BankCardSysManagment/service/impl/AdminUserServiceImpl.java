package jdev.kovalev.BankCardSysManagment.service.impl;

import jdev.kovalev.BankCardSysManagment.dto.request.UserInfoRequestDto;
import jdev.kovalev.BankCardSysManagment.dto.response.FullUserInfoResponseDto;
import jdev.kovalev.BankCardSysManagment.entity.User;
import jdev.kovalev.BankCardSysManagment.exception.UserNotFoundException;
import jdev.kovalev.BankCardSysManagment.mapper.UserMapperForAdmin;
import jdev.kovalev.BankCardSysManagment.repository.UserRepository;
import jdev.kovalev.BankCardSysManagment.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;
    private final UserMapperForAdmin mapper;

    private final static String DELETED_SUCCESSFULLY = "Пользователь с ID: %s успешно удалён";

    @Override
    public FullUserInfoResponseDto getUserInformationById(UUID userId) {
        return userRepository.findById(userId)
                .map(mapper::entityToDto)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<FullUserInfoResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(mapper::entityToDto)
                .toList();
    }

    @Override
    public FullUserInfoResponseDto createUser(UserInfoRequestDto userInfoRequestDto) {
        User savedUser = userRepository.save(mapper.dtoToEntity(userInfoRequestDto));
        return mapper.entityToDto(savedUser);
    }

    @Transactional
    @Override
    public FullUserInfoResponseDto updateUser(UUID userId, UserInfoRequestDto userInfoRequestDto) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setFirstAndLastName(userInfoRequestDto.getFirstAndLastName());
                    return mapper.entityToDto(userRepository.save(user));
                })
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public String deleteUser(UUID userId) {
        userRepository.deleteById(userId);
        return String.format(DELETED_SUCCESSFULLY, userId);
    }
}
