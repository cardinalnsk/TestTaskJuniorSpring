package ru.cardinalnsk.springjavajuniortest.mapper;

import org.mapstruct.Mapper;
import org.springframework.security.core.userdetails.User;
import ru.cardinalnsk.springjavajuniortest.domain.UserAccount;

@Mapper(componentModel = "spring")
public interface UserAccountToUserMapper {

    default User toUser(UserAccount userAccount) {
        return new User(
            userAccount.getPhoneNumber(),
            userAccount.getPassword(),
            userAccount.getRole()
        );
    }
}
