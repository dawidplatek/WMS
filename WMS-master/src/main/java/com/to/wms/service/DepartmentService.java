package com.to.wms.service;

import com.to.wms.controller.dto.GenericResponseDto;
import com.to.wms.controller.dto.department.DepartmentGetDto;
import com.to.wms.controller.dto.department.DepartmentMapper;
import com.to.wms.controller.dto.department.DepartmentPostDto;
import com.to.wms.controller.dto.department.DepartmentPutDto;
import com.to.wms.model.Address;
import com.to.wms.model.Department;
import com.to.wms.repository.AddressRepository;
import com.to.wms.repository.DepartmentRepository;
import com.to.wms.service.exceptions.AddressNotFoundException;
import com.to.wms.service.exceptions.DepartmentAlreadyExistException;
import com.to.wms.service.exceptions.DepartmentNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final AddressRepository addressRepository;
    private final DepartmentMapper mapper;


    public DepartmentService(
            DepartmentRepository departmentRepository,
            AddressRepository addressRepository,
            DepartmentMapper mapper
    ) {
        this.departmentRepository = departmentRepository;
        this.addressRepository = addressRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public DepartmentGetDto getDepartmentByName(String name) throws DepartmentNotFoundException {
        Department department = departmentRepository.findDepartmentByName(name).orElseThrow(DepartmentNotFoundException::new);
        return mapper.departmentToDepartmentGetDto(department);
    }

    @Transactional(readOnly = true)
    public List<DepartmentGetDto> getDepartmentByCity(String city) throws DepartmentNotFoundException {
        List<Department> department = departmentRepository.findByCityName(city);
        return department.stream()
                .map(mapper::departmentToDepartmentGetDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public DepartmentGetDto addDepartment(DepartmentPostDto departmentPostDto) throws AddressNotFoundException, DepartmentAlreadyExistException {
        Address address = addressRepository.findAddressByPostCode(departmentPostDto.getAddressPostCode()).orElseThrow(AddressNotFoundException::new);
        if (departmentRepository.findDepartmentByName(departmentPostDto.getName()).isPresent()) {
            throw new DepartmentAlreadyExistException();
        }
        Department department = mapper.departmentPostDtoToDepartment(departmentPostDto);
        department.setAddress(address);
        return mapper.departmentToDepartmentGetDto(departmentRepository.save(department));

    }

    public DepartmentGetDto editDepartment(String name, DepartmentPutDto departmentToUpdate) throws DepartmentNotFoundException, AddressNotFoundException {
        Department department = departmentRepository.findDepartmentByName(name).orElseThrow(DepartmentNotFoundException::new);
        if (departmentToUpdate.getName() != null) {
            department.setName(departmentToUpdate.getName());
        }
        if (departmentToUpdate.getAddressPostCode() != null) {
            Address address = addressRepository.findAddressByPostCode(departmentToUpdate.getAddressPostCode()).orElseThrow(AddressNotFoundException::new);
            department.setAddress(address);
        }
        return mapper.departmentToDepartmentGetDto(departmentRepository.save(department));
    }

    public List<DepartmentGetDto> getAll() {
        return departmentRepository.findAll().stream()
                .map(mapper::departmentToDepartmentGetDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public GenericResponseDto deleteByName(String name) throws DepartmentNotFoundException {
        Department department = departmentRepository.findDepartmentByName(name).orElseThrow(DepartmentNotFoundException::new);
        departmentRepository.delete(department);
        return new GenericResponseDto("Successfully deleted department!");
    }

}
