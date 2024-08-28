package ru.mudan.facafe;

import org.springframework.stereotype.Component;
import ru.mudan.dto.UserDTO;
import ru.mudan.models.User;

@Component
public class UserFacade {
    public UserDTO userToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setUsername(user.getUsername());
        userDTO.setLastname(user.getLastname());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}