package com.to.wms.controller;

import com.to.wms.controller.dto.GenericArrayPutDto;
import com.to.wms.controller.dto.GenericPutDto;
import com.to.wms.controller.dto.GenericResponseDto;
import com.to.wms.model.Role;
import com.to.wms.service.RoleService;
import com.to.wms.service.exceptions.RoleAlreadyExistException;
import com.to.wms.service.exceptions.RoleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<?>> getAll() {
        List<?> roles = roleService.getAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping(params = {"authority"})
    public ResponseEntity<Role> addRole(
            @RequestBody @Valid Role role,
            @RequestParam("authority") List<String> authorities
    ) throws RoleAlreadyExistException {
        return new ResponseEntity<>(roleService.addRole(role, authorities), HttpStatus.CREATED);
    }

    @GetMapping("/{role_name}")
    public ResponseEntity<Role> getRole(
            @PathVariable("role_name") String roleName
    ) throws RoleNotFoundException {
        Role role = roleService.getRoleByName(roleName);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PutMapping("/{role_name}/data")
    public ResponseEntity<Role> editRole(
            @PathVariable("role_name") String roleName,
            @RequestParam("update") String updateType,
            @RequestBody @Valid GenericPutDto genericPutDto
    ) throws RoleNotFoundException {
        return ResponseEntity.ok(roleService.editRole(roleName, updateType, genericPutDto));
    }

    @PutMapping("/{role_name}/authorities")
    public ResponseEntity<Role> editRole(
            @PathVariable("role_name") String roleName,
            @RequestBody @Valid GenericArrayPutDto genericArrayPutDto
    ) throws RoleNotFoundException {
        return ResponseEntity.ok(roleService.editAuthorities(roleName, genericArrayPutDto));
    }

    @DeleteMapping("/{role_name}")
    public ResponseEntity<GenericResponseDto> deleteRole(
            @PathVariable("role_name") String roleName
    ) throws RoleNotFoundException {
        return ResponseEntity.ok(roleService.deleteRoleByName(roleName));
    }

}
