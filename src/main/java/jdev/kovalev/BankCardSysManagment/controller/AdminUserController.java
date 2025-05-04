package jdev.kovalev.BankCardSysManagment.controller;

import jdev.kovalev.BankCardSysManagment.dto.request.UserInfoRequestDto;
import jdev.kovalev.BankCardSysManagment.dto.response.AdminUserInfoResponseDto;
import jdev.kovalev.BankCardSysManagment.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping("/{userId}")
    public ResponseEntity<AdminUserInfoResponseDto> getUserInformationByUserId(@PathVariable @UUID String userId) {
        return new ResponseEntity<>(adminUserService.getUserInformationById(userId), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AdminUserInfoResponseDto>> getAllUserInformation() {
        return new ResponseEntity<>(adminUserService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AdminUserInfoResponseDto> createUser(@Validated @RequestBody UserInfoRequestDto userInfoRequestDto) {
        return new ResponseEntity<>(adminUserService.createUser(userInfoRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<AdminUserInfoResponseDto> updateUser(@PathVariable @UUID String userId,
                                                               @Validated @RequestBody UserInfoRequestDto userInfoRequestDto) {
        return new ResponseEntity<>(adminUserService.updateUser(userId, userInfoRequestDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{userId}", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> deleteUser(@PathVariable @UUID String userId) {
        return new ResponseEntity<>(adminUserService.deleteUser(userId), HttpStatus.OK);
    }
}

