package com.to.wms.controller;

import com.to.wms.controller.dto.GenericResponseDto;
import com.to.wms.controller.dto.department.DepartmentGetDto;
import com.to.wms.controller.dto.department.DepartmentPostDto;
import com.to.wms.controller.dto.department.DepartmentPutDto;
import com.to.wms.service.DepartmentService;
import com.to.wms.service.exceptions.AddressNotFoundException;
import com.to.wms.service.exceptions.DepartmentAlreadyExistException;
import com.to.wms.service.exceptions.DepartmentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentGetDto>> getAllDepartments() {
        List<DepartmentGetDto> departments = departmentService.getAll();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("{name}")
    public ResponseEntity<DepartmentGetDto> getDepartmentByName(
            @PathVariable String name
    ) throws DepartmentNotFoundException {
        DepartmentGetDto department = departmentService.getDepartmentByName(name);
        return ResponseEntity.ok(department);
    }

    @GetMapping(params = "city")
    public ResponseEntity<List<DepartmentGetDto>> getDepartmentByCity(
            @RequestParam String city
    ) throws DepartmentNotFoundException {
        List<DepartmentGetDto> department = departmentService.getDepartmentByCity(city);
        return ResponseEntity.ok(department);
    }

    @PostMapping
    public ResponseEntity<DepartmentGetDto> addDepartment(
            @Valid @RequestBody DepartmentPostDto departmentPostDto
    ) throws AddressNotFoundException, DepartmentAlreadyExistException {
        DepartmentGetDto department = departmentService.addDepartment(departmentPostDto);
        return new ResponseEntity<>(department, HttpStatus.CREATED);
    }

    @PutMapping("/{name}")
    public ResponseEntity<DepartmentGetDto> editDepartment(
            @Valid @RequestBody DepartmentPutDto departmentToUpdate,
            @PathVariable String name
    ) throws AddressNotFoundException, DepartmentNotFoundException {
        return new ResponseEntity<>(departmentService.editDepartment(name, departmentToUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<GenericResponseDto> deleteDepartment(@PathVariable String name) throws DepartmentNotFoundException {
        return ResponseEntity.ok(departmentService.deleteByName(name));
    }
}
