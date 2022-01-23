package com.to.wms.service;

import com.to.wms.controller.dto.authority.AuthorityGetDto;
import com.to.wms.controller.dto.authority.AuthorityMapper;
import com.to.wms.repository.AuthorityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final AuthorityMapper mapper;

    public AuthorityService(AuthorityRepository authorityRepository, AuthorityMapper authorityMapper) {
        this.authorityRepository = authorityRepository;
        this.mapper = authorityMapper;
    }

    public List<AuthorityGetDto> getAll() {
        return authorityRepository.findAll().stream()
                .map(mapper::authorityToAuthorityGetDto)
                .collect(Collectors.toUnmodifiableList());
    }
}
