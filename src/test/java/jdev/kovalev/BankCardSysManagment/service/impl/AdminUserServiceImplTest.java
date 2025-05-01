package jdev.kovalev.BankCardSysManagment.service.impl;

import jdev.kovalev.BankCardSysManagment.dto.request.UserInfoRequestDto;
import jdev.kovalev.BankCardSysManagment.dto.response.FullUserInfoResponseDto;
import jdev.kovalev.BankCardSysManagment.entity.User;
import jdev.kovalev.BankCardSysManagment.exception.UserNotFoundException;
import jdev.kovalev.BankCardSysManagment.mapper.UserMapperForAdmin;
import jdev.kovalev.BankCardSysManagment.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminUserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapperForAdmin mapper;
    @InjectMocks
    private AdminUserServiceImpl adminUserService;

    private User user;
    private User user2;
    private String userId;
    private FullUserInfoResponseDto fullUserInfoResponseDto;
    private FullUserInfoResponseDto fullUserInfoResponseDto2;
    private UserInfoRequestDto userInfoRequestDto;

    @BeforeEach
    void setUp() {
        userId = "e487f331-5b7d-40b9-b49d-b41f94b2c960";
        user = User.builder()
                .userId(UUID.fromString(userId))
                .firstAndLastName("Siarhei Kavaleu")
                .build();
        user2 = User.builder()
                .userId(UUID.fromString(userId))
                .firstAndLastName("Mihail Nekrasov")
                .build();
        fullUserInfoResponseDto = FullUserInfoResponseDto.builder()
                .userId(UUID.fromString(userId))
                .firstAndLastName("Siarhei Kavaleu")
                .build();
        fullUserInfoResponseDto2 = FullUserInfoResponseDto.builder()
                .userId(UUID.fromString(userId))
                .firstAndLastName("Mihail Nekrasov")
                .build();
        userInfoRequestDto = UserInfoRequestDto.builder()
                .firstAndLastName("Siarhei Kavaleu")
                .build();
    }

    @Nested
    class GetUserById {
        @Test
        void getUserInformationByIdWhenPresent() {
            when(userRepository.findById(any()))
                    .thenReturn(Optional.of(user));
            when(mapper.entityToDto(user))
                    .thenReturn(fullUserInfoResponseDto);

            FullUserInfoResponseDto actual = adminUserService.getUserInformationById(userId);

            assertThat(actual)
                    .isEqualTo(fullUserInfoResponseDto);
        }

        @Test
        void getUserInformationByIdWhenNotPresent() {
            when(userRepository.findById(any()))
                    .thenReturn(Optional.empty());

            assertThatThrownBy(() -> adminUserService.getUserInformationById(userId))
                    .isInstanceOf(UserNotFoundException.class)
                    .hasMessage("Пользователь с таким id не найден в базе данных");
        }
    }

    @Nested
    class GetAllUsers {
        @Test
        void getAllUsers() {
            when(userRepository.findAll())
                    .thenReturn(List.of(user, user2));
            when(mapper.entityToDto(user))
                    .thenReturn(fullUserInfoResponseDto);
            when(mapper.entityToDto(user2))
                    .thenReturn(fullUserInfoResponseDto2);

            List<FullUserInfoResponseDto> actual = adminUserService.getAllUsers();

            assertThat(actual)
                    .hasSize(2)
                    .containsExactlyElementsOf(List.of(fullUserInfoResponseDto, fullUserInfoResponseDto2));
        }
    }

    @Nested
    class CreateUser {
        @Test
        void createUser() {
            when(userRepository.save(any()))
                    .thenReturn(user);
            when(mapper.dtoToEntity(userInfoRequestDto))
                    .thenReturn(user);
            when(mapper.entityToDto(user))
                    .thenReturn(fullUserInfoResponseDto);

            FullUserInfoResponseDto actual = adminUserService.createUser(userInfoRequestDto);

            assertThat(actual)
                    .isNotNull()
                    .isInstanceOf(FullUserInfoResponseDto.class)
                    .isEqualTo(fullUserInfoResponseDto);
        }
    }

    @Nested
    class UpdateUser {
        @Test
        void updateUserWhenPresentItDb() {
            UserInfoRequestDto userInfoRequestDto2 = UserInfoRequestDto.builder()
                    .firstAndLastName("Mihail Nekrasov")
                    .build();
            FullUserInfoResponseDto expected = fullUserInfoResponseDto2;

            when(userRepository.findById(any()))
                    .thenReturn(Optional.of(user));
            when(userRepository.save(any()))
                    .thenReturn(user2);
            when(mapper.entityToDto(user2))
                    .thenReturn(fullUserInfoResponseDto2);

            FullUserInfoResponseDto actual = adminUserService.updateUser(userId, userInfoRequestDto2);

            assertThat(actual)
                    .isEqualTo(expected);
            assertThat(actual.getFirstAndLastName())
                    .isEqualTo(expected.getFirstAndLastName());
        }

        @Test
        void updateUserWhenNotPresentItDb() {
            when(userRepository.findById(any()))
                    .thenReturn(Optional.empty());

            assertThatThrownBy(() -> adminUserService.updateUser(userId, userInfoRequestDto))
                    .isInstanceOf(UserNotFoundException.class)
                    .hasMessageContaining("Пользователь с таким id не найден в базе данных");
        }
    }

    @Nested
    class DeleteUser {
        @Test
        void deleteUser() {
            String expected = String.format("Пользователь с ID: %s успешно удалён", userId);

            String actual = adminUserService.deleteUser(userId);

            assertThat(actual).isEqualTo(expected);

            verify(userRepository).deleteById(UUID.fromString(userId));
        }
    }
}