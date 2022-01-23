package com.to.wms.controller;

import com.to.wms.controller.dto.GenericResponseDto;
import com.to.wms.controller.dto.address.AddressGetDto;
import com.to.wms.controller.dto.address.AddressPostDto;
import com.to.wms.service.AddressService;
import com.to.wms.service.exceptions.AddressAlreadyExistException;
import com.to.wms.service.exceptions.AddressNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<List<AddressGetDto>> getAll() {
        List<AddressGetDto> address = addressService.getAll();
        return ResponseEntity.ok(address);
    }

    @GetMapping(params = {"city"})
    public ResponseEntity<List<AddressGetDto>> getAddressByCity(@RequestParam String city) {
        return ResponseEntity.ok(addressService.getAddressByCity(city));
    }

    @PostMapping
    public ResponseEntity<AddressGetDto> addAddress(
            @RequestBody @Valid AddressPostDto address
    ) throws AddressAlreadyExistException {
        return new ResponseEntity<>(addressService.addAddress(address), HttpStatus.CREATED);
    }

    @PutMapping("/{post_code}")
    public ResponseEntity<AddressGetDto> updateAddress(
            @PathVariable("post_code") String postCode,
            @RequestBody @Valid AddressPostDto address
    ) throws AddressAlreadyExistException, AddressNotFoundException {
        return new ResponseEntity<>(addressService.editAddress(address, postCode), HttpStatus.OK);
    }

    @DeleteMapping("/{post_code}")
    public  ResponseEntity<GenericResponseDto> deleteAddress(@PathVariable("post_code") String postCode) throws AddressNotFoundException {
        return ResponseEntity.ok(addressService.deleteByPostCode(postCode));
    }
}
