package com.to.wms.service;

import com.to.wms.controller.dto.GenericPutDto;
import com.to.wms.controller.dto.GenericResponseDto;
import com.to.wms.controller.dto.role.UserMapper;
import com.to.wms.controller.dto.role.UserPostDto;
import com.to.wms.model.Authority;
import com.to.wms.model.Role;
import com.to.wms.model.User;
import com.to.wms.repository.AuthorityRepository;
import com.to.wms.repository.RoleRepository;
import com.to.wms.repository.UserRepository;
import com.to.wms.service.exceptions.LoginAlreadyExistException;
import com.to.wms.service.exceptions.RoleNotFoundException;
import com.to.wms.service.exceptions.UserAlreadyExistException;
import com.to.wms.service.exceptions.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class UserService extends BasicGenericService<UserRepository> {

    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            AuthorityRepository authorityRepository,
            PasswordEncoder passwordEncoder,
            UserMapper userMapper
    ) {
        super(userRepository);
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.userMapper = userMapper;
    }

    public User addUser(UserPostDto postDto) throws UserAlreadyExistException {
        checkIfUserExists(postDto.getLogin(), postDto.getEmail());
        User user = userMapper.userPostDtoToUser(postDto);
        user.setRole(getUserRole());
        user.setPassword(passwordEncoder.encode(postDto.getPassword()));
        return repository.save(user);
    }

    private void checkIfUserExists(String login, String email) throws IllegalStateException, UserAlreadyExistException {
        if (repository.findUserByLogin(login).isPresent() || repository.findByEmail(email).isPresent() ) {
            throw new UserAlreadyExistException();
        }
    }

    public Role getUserRole() {
        if (repository.getAllCount() == 0) {
            Authority userManagement = authorityRepository.findByName("OP_USER_MANAGEMENT");
            Authority roleManagement = authorityRepository.findByName("OP_ROLE_MANAGEMENT");
            return roleRepository.findByAuthoritiesIsContaining(List.of(userManagement, roleManagement)).get(0);
        }
        return roleRepository.findByAuthoritiesIsContaining(authorityRepository.findByName("BASIC_USER")).get(0);
    }

    @Transactional(readOnly = true)
    public User getUserByName(String login) throws UserNotFoundException {
        return repository.findUserByLogin(login).orElseThrow(UserNotFoundException::new);
    }

    public User updateUser(String login, String changeType, GenericPutDto genericPutDto) throws RoleNotFoundException, LoginAlreadyExistException, UserNotFoundException {
        User user = repository.findUserByLogin(login).orElseThrow(UserNotFoundException::new);
        switch (changeType) {
            case "email":
                user.setEmail(genericPutDto.getNewValue());
                break;
            case "password":
                user.setPassword(passwordEncoder.encode(genericPutDto.getNewValue()));
                break;
            case "login":
                String newLogin = genericPutDto.getNewValue();
                Optional<User> userWithLogin = repository.findUserByLogin(newLogin);
                if (userWithLogin.isPresent()) {
                    throw new LoginAlreadyExistException();
                }
                user.setLogin(newLogin);
                break;
            case "name":
                user.setName(genericPutDto.getNewValue());
                break;
            case "lastname":
                user.setLastname(genericPutDto.getNewValue());
                break;
            case "phone_number":
                user.setPhoneNumber(genericPutDto.getNewValue());
                break;
            case "role":
                Role newRole = roleRepository.findByName(genericPutDto.getNewValue().toUpperCase(Locale.ROOT)).orElseThrow(RoleNotFoundException::new);
                user.setRole(newRole);
                break;
            default:
                return user;
        }
        return repository.save(user);
    }

    public GenericResponseDto deleteUser(String userId) throws UserNotFoundException {
        User user = repository.findUserByLogin(userId).orElseThrow(UserNotFoundException::new);
        repository.delete(user);
        return new GenericResponseDto("Successfully deleted user!");
    }
}
