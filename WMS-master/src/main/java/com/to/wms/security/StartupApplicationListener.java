package com.to.wms.security;

import com.to.wms.model.Authority;
import com.to.wms.model.Role;
import com.to.wms.repository.AuthorityRepository;
import com.to.wms.repository.RoleRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;

    public StartupApplicationListener(AuthorityRepository authorityRepository, RoleRepository roleRepository) {
        this.authorityRepository = authorityRepository;
        this.roleRepository = roleRepository;
    }

    @Override public void onApplicationEvent(ContextRefreshedEvent event) {
        // Init authorities
        List<String> adminAuthoritiesNames = List.of(
                "OP_ROLE_MANAGEMENT",
                "OP_USER_MANAGEMENT"
        );
        List<String> managerAuthoritiesNames = List.of(
                "OP_ADDRESS_MANAGEMENT",
                "OP_DEPARTMENTS_MANAGEMENT",
                "OP_LOCATIONS_MANAGEMENT",
                "OP_CATEGORY_MANAGEMENT",
                "OP_PRODUCT_MANAGEMENT",
                "OP_MANAGER_USER"
        );
        List<String> userAuthoritiesNames = List.of(
                "BASIC_USER",
                "WAREHOUSEMAN_ADDRESS",
                "WAREHOUSEMAN_DEPARTMENTS",
                "WAREHOUSEMAN_LOCATIONS",
                "WAREHOUSE_CATEGORY",
                "WAREHOUSEMAN_PRODUCT"
        );

        List<String> allAuthorities = new ArrayList<>();
        allAuthorities.addAll(adminAuthoritiesNames);
        allAuthorities.addAll(managerAuthoritiesNames);
        allAuthorities.addAll(userAuthoritiesNames);
        initAuthorities(allAuthorities);

        // Basic admin and user roles
        List<Authority> adminAuthorities = authorityRepository.findAllByNameIn(adminAuthoritiesNames);
        List<Authority> managerAuthorities = authorityRepository.findAllByNameIn(managerAuthoritiesNames);
        List<Authority> userAuthorities = authorityRepository.findAllByNameIn(userAuthoritiesNames);
        createRoleWithAuthoritiesIfNotExist("admin", 1, adminAuthorities);
        createRoleWithAuthoritiesIfNotExist("manager", 2, managerAuthorities);
        createRoleWithAuthoritiesIfNotExist("user", 3, userAuthorities);
    }

    private void initAuthorities(List<String> names) {
        findAuthoritiesToCreate(names);
        List<Authority> authorities = createAuthorities(names);
        authorityRepository.saveAll(authorities);
    }

    private void findAuthoritiesToCreate(List<String> authorityNames) {
        List<Authority> authorities = authorityRepository.findAllByNameIn(authorityNames);
        authorities.forEach(a -> authorityNames.remove(a.getName()));
    }

    private List<Authority> createAuthorities(List<String> authorityNames) {
        List<Authority> authorities = new ArrayList<>();
        for (String authorityName : authorityNames) {
            Authority authority = new Authority();
            authority.setName(authorityName);
            authorities.add(authority);
        }
        return authorities;
    }

    private void createRoleWithAuthoritiesIfNotExist(String name, int grade, List<Authority> authorities) {
        if (roleRepository.findByAuthoritiesIsContaining(authorities).isEmpty()) {
            Role role = new Role();
            role.setGrade(grade);
            role.setAuthorities(authorities);
            role.setName(name.toUpperCase(Locale.ROOT));
            roleRepository.save(role);
        }
    }

}