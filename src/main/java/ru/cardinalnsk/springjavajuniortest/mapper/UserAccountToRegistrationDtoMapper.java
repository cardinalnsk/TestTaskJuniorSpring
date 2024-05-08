package ru.cardinalnsk.springjavajuniortest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.RegistrationResponseDto;
import ru.cardinalnsk.springjavajuniortest.domain.UserAccount;

@Mapper(componentModel = "spring")
public interface UserAccountToRegistrationDtoMapper {

    @Mapping(target = "userId", source = "id")
    RegistrationResponseDto toUserDto(UserAccount userAccount);

}
