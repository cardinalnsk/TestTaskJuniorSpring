package ru.cardinalnsk.springjavajuniortest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.RegistrationResponseDto;
import ru.cardinalnsk.springjavajuniortest.domain.UserAccount;

@Mapper(componentModel = "spring")
public abstract class UserAccountToRegistrationDtoMapper {

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "token", ignore = true)
    public abstract RegistrationResponseDto toUserDto(UserAccount userAccount);

}
