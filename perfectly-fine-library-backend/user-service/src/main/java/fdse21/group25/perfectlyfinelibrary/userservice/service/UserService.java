package fdse21.group25.perfectlyfinelibrary.userservice.service;

import java.util.List;

import fdse21.group25.perfectlyfinelibrary.common.dto.UserDto;
import fdse21.group25.perfectlyfinelibrary.common.exception.NotFoundException;
import fdse21.group25.perfectlyfinelibrary.userservice.entity.User;

public interface UserService {
    User addNewUser(UserDto user);

    User findUserByUsername(String username) throws NotFoundException;

    User payFine(String username) throws NotFoundException;

    List<User> findUsers(boolean isFined);

    User resetCredit(String username) throws NotFoundException;

}