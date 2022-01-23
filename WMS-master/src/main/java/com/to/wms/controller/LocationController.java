package com.to.wms.controller;

import com.to.wms.controller.dto.GenericResponseDto;
import com.to.wms.controller.dto.location.LocationGetDto;
import com.to.wms.controller.dto.location.LocationPostDto;
import com.to.wms.controller.dto.location.LocationWithProductsGetDto;
import com.to.wms.model.Location;
import com.to.wms.service.LocationService;
import com.to.wms.service.exceptions.DepartmentNotFoundException;
import com.to.wms.service.exceptions.LocationAlreadyExistException;
import com.to.wms.service.exceptions.LocationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<List<LocationGetDto>> getAllLocations() {
        List<LocationGetDto> locations = locationService.getAll();
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/{department}/{shelf}")
    public ResponseEntity<LocationWithProductsGetDto> getLocationByShelf(
            @PathVariable String department,
            @PathVariable String shelf
    ) throws LocationNotFoundException, DepartmentNotFoundException {
        LocationWithProductsGetDto location = locationService.getLocationByShelfAndDepartment(department, shelf);
        return ResponseEntity.ok(location);
    }


    @GetMapping("/{department}")
    public ResponseEntity<List<LocationGetDto>> getAllLocationsByDepartment(
            @PathVariable String department
    ) throws LocationNotFoundException {
        List<LocationGetDto> locations = locationService.getAllLocationsByDepartment(department);
        return ResponseEntity.ok(locations);
    }


    @PostMapping("/{department}")
    public ResponseEntity<LocationGetDto> addLocation(
            @PathVariable String department,
            @RequestBody @Valid LocationPostDto locationPostDto
    ) throws LocationAlreadyExistException, DepartmentNotFoundException {
        return new ResponseEntity<>(locationService.addLocation(department, locationPostDto), HttpStatus.CREATED);
    }

    @PutMapping("/{department}/{shelf}")
    public ResponseEntity<LocationGetDto> editLocationById(
            @PathVariable String department,
            @PathVariable String shelf,
            @RequestBody @Valid LocationPostDto locationPostDto
    ) throws LocationNotFoundException, DepartmentNotFoundException {
        return new ResponseEntity<>(locationService.editLocation(department, shelf, locationPostDto), HttpStatus.OK);

    }

    @DeleteMapping("/{department}/{shelf}")
    public  ResponseEntity<GenericResponseDto> deleteById(
            @PathVariable String department,
            @PathVariable String shelf
    ) throws LocationNotFoundException, DepartmentNotFoundException {
        return new ResponseEntity<>(locationService.delete(department, shelf), HttpStatus.OK);
    }
}
