package com.to.wms.service;

import com.to.wms.controller.dto.GenericArrayPutDto;
import com.to.wms.controller.dto.GenericPutDto;
import com.to.wms.controller.dto.GenericResponseDto;
import com.to.wms.model.Authority;
import com.to.wms.model.Role;
import com.to.wms.repository.AuthorityRepository;
import com.to.wms.repository.RoleRepository;
import com.to.wms.service.exceptions.RoleAlreadyExistException;
import com.to.wms.service.exceptions.RoleNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class RoleService extends BasicGenericService<RoleRepository> {

    private final AuthorityRepository authorityRepository;

    public RoleService(RoleRepository genericRoleRepository, AuthorityRepository authorityRepository) {
        super(genericRoleRepository);
        this.authorityRepository = authorityRepository;
    }

    @Transactional
    public Role addRole(Role role, List<String> authorities) throws RoleAlreadyExistException {
        if (repository.findByName(role.getName().toUpperCase(Locale.ROOT)).isPresent()) {
            throw new RoleAlreadyExistException();
        }
        authorities = authorities.stream()
                .map(a -> a.toUpperCase(Locale.ROOT))
                .collect(Collectors.toUnmodifiableList());
        List<Authority> authorityList = authorityRepository.findAllByNameIn(authorities);
        role.setName(role.getName().toUpperCase(Locale.ROOT));
        role.setAuthorities(authorityList);
        return repository.save(role);
    }

    public Role getRoleByName(String roleName) throws RoleNotFoundException {
        roleName = roleName.toUpperCase(Locale.ROOT);
        return repository.findByName(roleName).orElseThrow(RoleNotFoundException::new);
    }

    public Role editAuthorities(String roleName, GenericArrayPutDto genericArrayPutDto) throws RoleNotFoundException {
        roleName = roleName.toUpperCase(Locale.ROOT);
        List<String> authoritiesNames = genericArrayPutDto.getNewValues().stream()
                .map(a -> a.toUpperCase(Locale.ROOT))
                .collect(Collectors.toUnmodifiableList());
        Role role = repository.findByName(roleName).orElseThrow(RoleNotFoundException::new);
        List<Authority> authorities = authorityRepository.findAllByNameIn(authoritiesNames);
        role.setAuthorities(authorities);
        return repository.save(role);
    }

    public Role editRole(String roleName, String updateType, GenericPutDto genericPutDto) throws RoleNotFoundException {
        roleName = roleName.toUpperCase(Locale.ROOT);
        Role role = repository.findByName(roleName).orElseThrow(RoleNotFoundException::new);
        switch (updateType) {
            case "name":
                role.setName(genericPutDto.getNewValue().toUpperCase(Locale.ROOT));
                break;
            case "grade":
                role.setGrade(Integer.parseInt(genericPutDto.getNewValue()));
                break;
            default:
               return role;
        }
        return repository.save(role);
    }

    public GenericResponseDto deleteRoleByName(String roleName) throws RoleNotFoundException {
        roleName = roleName.toUpperCase(Locale.ROOT);
        Role role = repository.findByName(roleName).orElseThrow(RoleNotFoundException::new);
        repository.delete(role);
        return new GenericResponseDto("Successfully deleted role!");
    }

}
