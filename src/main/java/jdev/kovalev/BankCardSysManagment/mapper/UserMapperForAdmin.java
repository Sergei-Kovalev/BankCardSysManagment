package jdev.kovalev.BankCardSysManagment.mapper;

import jdev.kovalev.BankCardSysManagment.dto.request.UserInfoRequestDto;
import jdev.kovalev.BankCardSysManagment.dto.response.AdminUserInfoResponseDto;
import jdev.kovalev.BankCardSysManagment.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapperForAdmin {
    AdminUserInfoResponseDto entityToDto(User user);
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "cards", ignore = true)
    User dtoToEntity(UserInfoRequestDto dto);
}
