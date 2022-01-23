package com.to.wms.service;

import com.to.wms.controller.dto.GenericResponseDto;
import com.to.wms.controller.dto.location.LocationGetDto;
import com.to.wms.controller.dto.location.LocationMapper;
import com.to.wms.controller.dto.location.LocationPostDto;
import com.to.wms.controller.dto.location.LocationWithProductsGetDto;
import com.to.wms.model.Department;
import com.to.wms.model.Location;
import com.to.wms.repository.DepartmentRepository;
import com.to.wms.repository.LocationRepository;
import com.to.wms.service.exceptions.DepartmentNotFoundException;
import com.to.wms.service.exceptions.LocationAlreadyExistException;
import com.to.wms.service.exceptions.LocationNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final DepartmentRepository departmentRepository;
    private final LocationMapper mapper;

    public LocationService(
            LocationRepository locationRepository,
            DepartmentRepository departmentRepository,
            LocationMapper locationMapper
    ) {
        this.locationRepository = locationRepository;
        this.departmentRepository = departmentRepository;
        this.mapper = locationMapper;
    }

    @Transactional(readOnly = true)
    public LocationWithProductsGetDto getLocationByShelfAndDepartment(String departmentName, String shelf) throws DepartmentNotFoundException, LocationNotFoundException {
        Department department = departmentRepository.findDepartmentByName(departmentName).orElseThrow(DepartmentNotFoundException::new);
        Location location = locationRepository.findByDepartmentAndShelf(department, shelf).orElseThrow(LocationNotFoundException::new);
        return mapper.locationToLocationWithProductsGetDto(location);
    }

    @Transactional(readOnly = true)
    public List<LocationGetDto> getAllLocationsByDepartment(String departmentName) throws LocationNotFoundException {
        List<Location> locations = locationRepository.findAllLocationsByDepartmentName(departmentName);
        if (locations.size() == 0) {
            throw new LocationNotFoundException();
        }
        return locations.stream()
                .map(mapper::locationToLocationGetDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public LocationGetDto addLocation(String departmentName, LocationPostDto locationPostDto) throws DepartmentNotFoundException, LocationAlreadyExistException {
        Department department = departmentRepository.findDepartmentByName(departmentName).orElseThrow(DepartmentNotFoundException::new);
        if (locationRepository.findByDepartmentAndShelf(department, locationPostDto.getShelf()).isPresent()) {
            throw new LocationAlreadyExistException();
        }
        Location location = mapper.locationPostDtoToLocation(locationPostDto);
        location.setDepartment(department);
        location.setProducts(new HashSet<>());
        return mapper.locationToLocationGetDto(locationRepository.save(location));
    }

    public LocationGetDto editLocation(String departmentName, String shelf, LocationPostDto locationPostDto) throws DepartmentNotFoundException, LocationNotFoundException {
        Department department = departmentRepository.findDepartmentByName(departmentName).orElseThrow(DepartmentNotFoundException::new);
        Location location = locationRepository.findByDepartmentAndShelf(department, shelf).orElseThrow(LocationNotFoundException::new);
        location.setShelf(locationPostDto.getShelf());
        return mapper.locationToLocationGetDto(locationRepository.save(location));
    }

    public List<LocationGetDto> getAll() {
        return locationRepository.findAll().stream()
                .map(mapper::locationToLocationGetDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public GenericResponseDto delete(String departmentName, String shelf) throws DepartmentNotFoundException, LocationNotFoundException {
        Department department = departmentRepository.findDepartmentByName(departmentName).orElseThrow(DepartmentNotFoundException::new);
        Location location = locationRepository.findByDepartmentAndShelf(department, shelf).orElseThrow(LocationNotFoundException::new);
        locationRepository.delete(location);
        return new GenericResponseDto("Successfully deleted location!");
    }

}
