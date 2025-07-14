package com.example.test.Service;
import com.example.test.DTO.DTOUtility;
import com.example.test.DTO.UserDTO;
import com.example.test.customException.UserNotFoundException;
import com.example.test.customException.userAlreadyExistsException;
import org.springframework.stereotype.Service;
import com.example.test.Repository.UserRepository;
import com.example.test.Entity.User;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public UserDTO saveUser(UserDTO userDto) {
        userRepository.findByEmail(userDto.getEmail()).ifPresent(u -> {
            throw new userAlreadyExistsException("Email already in use: " + userDto.getEmail());
        });
        User user = DTOUtility.dtoToEntity(userDto);
        User saved = userRepository.save(user);

        return DTOUtility.entityToDto(saved);
    }
    public List<UserDTO> saveUsers(List<UserDTO> users) {
        List<String> emails = users.stream().map(UserDTO::getEmail).toList();
        List<User> userList = userRepository.findAllByEmailIn(emails);
        if(!userList.isEmpty()) {
            String error = userList.stream().map(User::getEmail).collect(Collectors.joining(", "));
            throw new userAlreadyExistsException("Emails Already Exists: " + error);
        }
        return userRepository.saveAll(users.stream().map(DTOUtility::dtoToEntity).toList()).stream().map(DTOUtility::entityToDto).toList();
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(DTOUtility::entityToDto).toList();
    }
    public UserDTO getUserById(int id) {
        return userRepository.findById(id).map(DTOUtility::entityToDto).orElseThrow(()->new UserNotFoundException("User Not Found with id: " + id));
    }

    public UserDTO updateUser(Integer id, UserDTO userdto) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found with id: " + id));
        userRepository.findByEmailAndIdNot(userdto.getEmail(), id).ifPresent(e -> {
            throw new userAlreadyExistsException("Email already in use: " + e.getEmail());
        });
        DTOUtility.updateUserFromDto(user, userdto);
        User updated = userRepository.save(user);
        return DTOUtility.entityToDto(updated);
    }

    public List<UserDTO> updateUsers(List<UserDTO> userDTOS) {
        List<Integer> ids = userDTOS.stream().map(UserDTO::getId).toList();
        List<String> emails = userDTOS.stream().map(UserDTO::getEmail).toList();

        List<User> users = userRepository.findAllById(ids);
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(User::getId, u -> u));

        List<User> emailErrors = userRepository.findAllByEmailIn(emails);
        Map<String, Integer> emailToId = emailErrors.stream().filter(e -> !ids.contains(e.getId())).collect(Collectors.toMap(User::getEmail, User::getId));

        if(userMap.size() != userDTOS.size()) {
            List<Integer> missing = ids.stream().filter(id -> !userMap.containsKey(id)).toList();
            throw new UserNotFoundException("User Not Found with Ids: " + missing);
        }

        for(UserDTO u : userDTOS) {
            Integer id = u.getId();
            String email = u.getEmail();

            if(emailToId.containsKey(email) && !Objects.equals(emailToId.get(email), id)) {
                throw new userAlreadyExistsException("User Already Exists with email: " + email);
            }
        }
        List<User> updated = userDTOS.stream().map(dto -> {
            User user = userMap.get(dto.getId());
            DTOUtility.updateUserFromDto(user, dto);
            return user;
        }).toList();

        return userRepository.saveAll(updated).stream().map(DTOUtility::entityToDto).toList();
    }

    public void deleteUser(int id) {
        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException("User Not Found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    public void deleteUsers(List<Integer> ids) {
        List<User> users = userRepository.findAllById(ids);
        Set<Integer> found = users.stream().map(User::getId).collect(Collectors.toSet());
        List<Integer> notFound = ids.stream().filter(id -> !found.contains(id)).toList();
        if(!notFound.isEmpty()) {
            throw new UserNotFoundException("Couldn't find these ids: " + notFound);
        }
        userRepository.deleteAllById(ids);
    }
}
