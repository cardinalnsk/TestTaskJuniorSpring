package ru.cardinalnsk.springjavajuniortest.mapper;

import org.mapstruct.Mapper;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.UserDto;
import ru.cardinalnsk.springjavajuniortest.domain.UserAccount;

@Mapper(componentModel = "spring")
public interface UserAccountToDtoMapper {

    UserDto toUserDto (UserAccount userAccount);

}
