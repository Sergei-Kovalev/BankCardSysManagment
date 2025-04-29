package jdev.kovalev.BankCardSysManagment.controller;

import jdev.kovalev.BankCardSysManagment.dto.request.UserInfoRequestDto;
import jdev.kovalev.BankCardSysManagment.dto.response.FullUserInfoResponseDto;
import jdev.kovalev.BankCardSysManagment.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping("/{userId}")
    public ResponseEntity<FullUserInfoResponseDto> getUserInformationByUserId(@PathVariable UUID userId) {
        return new ResponseEntity<>(adminUserService.getUserInformationById(userId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FullUserInfoResponseDto>> getAllUserInformation() {
        return new ResponseEntity<>(adminUserService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FullUserInfoResponseDto> createUser(@Validated @RequestBody UserInfoRequestDto userInfoRequestDto) {
        return new ResponseEntity<>(adminUserService.createUser(userInfoRequestDto), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<FullUserInfoResponseDto> updateUser(@PathVariable UUID userId,
                                                              @RequestBody UserInfoRequestDto userInfoRequestDto) {
        return new ResponseEntity<>(adminUserService.updateUser(userId, userInfoRequestDto), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID userId) {
        return new ResponseEntity<>(adminUserService.deleteUser(userId), HttpStatus.OK);
    }

}
