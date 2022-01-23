package com.to.wms.controller;

import com.to.wms.controller.dto.GenericPutDto;
import com.to.wms.controller.dto.GenericResponseDto;
import com.to.wms.controller.dto.role.UserPostDto;
import com.to.wms.model.User;
import com.to.wms.service.UserService;
import com.to.wms.service.exceptions.LoginAlreadyExistException;
import com.to.wms.service.exceptions.RoleNotFoundException;
import com.to.wms.service.exceptions.UserAlreadyExistException;
import com.to.wms.service.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<?>> getAll() {
        List<?> roles = userService.getAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(
            @Valid @RequestBody UserPostDto user
    ) throws UserAlreadyExistException {
        User response = userService.addUser(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{login}")
    public ResponseEntity<User> getUser(
        @PathVariable String login
    ) throws UserNotFoundException {
        User user = userService.getUserByName(login);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{login}")
    public ResponseEntity<User> updateUser(
            @PathVariable String login,
            @RequestParam("update") String changeType,
            @RequestBody @Valid GenericPutDto genericPutDto
    ) throws UserNotFoundException, LoginAlreadyExistException, RoleNotFoundException {
        User user = userService.updateUser(login, changeType, genericPutDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{login}")
    public ResponseEntity<GenericResponseDto> deleteUser(
            @PathVariable String login
    ) throws UserNotFoundException {
        GenericResponseDto responseDto = userService.deleteUser(login);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
