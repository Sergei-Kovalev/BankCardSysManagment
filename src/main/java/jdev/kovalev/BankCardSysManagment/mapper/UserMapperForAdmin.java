package jdev.kovalev.BankCardSysManagment.mapper;

import jdev.kovalev.BankCardSysManagment.dto.request.UserInfoRequestDto;
import jdev.kovalev.BankCardSysManagment.dto.response.FullUserInfoResponseDto;
import jdev.kovalev.BankCardSysManagment.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapperForAdmin {
    FullUserInfoResponseDto entityToDto(User user);
    User dtoToEntity(UserInfoRequestDto dto);
}
