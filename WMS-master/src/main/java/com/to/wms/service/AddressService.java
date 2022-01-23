package com.to.wms.service;

import com.to.wms.controller.dto.GenericResponseDto;
import com.to.wms.controller.dto.address.AddressGetDto;
import com.to.wms.controller.dto.address.AddressMapper;
import com.to.wms.controller.dto.address.AddressPostDto;
import com.to.wms.model.Address;
import com.to.wms.repository.AddressRepository;
import com.to.wms.service.exceptions.AddressAlreadyExistException;
import com.to.wms.service.exceptions.AddressNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressService(
            AddressRepository addressRepository,
            AddressMapper addressMapper
    ) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public AddressGetDto addAddress(AddressPostDto addressPostDto) throws AddressAlreadyExistException {
        Optional<Address> address = addressRepository.findAddressByPostCode(addressPostDto.getPostCode());
        if (address.isPresent()) {
            throw new AddressAlreadyExistException();
        }
        Address addressToSave = addressMapper.addressPostDtoToAddress(addressPostDto);
        return addressMapper.addressToAddressGetDto(addressRepository.save(addressToSave));
    }

    public AddressGetDto editAddress(AddressPostDto addressToUpdate, String postCode) throws AddressNotFoundException, AddressAlreadyExistException {
        Address address = addressRepository.findAddressByPostCode(postCode).orElseThrow(AddressNotFoundException::new);
        if (addressRepository.findAddressByPostCode(addressToUpdate.getPostCode()).isPresent()) {
            throw new AddressAlreadyExistException();
        }
        address.setCity(addressToUpdate.getCity());
        address.setCountry(addressToUpdate.getCountry());
        address.setNumber(addressToUpdate.getNumber());
        address.setPostCode(addressToUpdate.getPostCode());
        address.setStreet(addressToUpdate.getStreet());
        addressRepository.save(address);
        return addressMapper.addressToAddressGetDto(address);
    }

    @Transactional(readOnly = true)
    public List<AddressGetDto> getAddressByCity(String city) {
        List<Address> addressList = addressRepository.findAddressByCity(city);
        return addressList.stream()
                .map(addressMapper::addressToAddressGetDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<AddressGetDto> getAll() {
        return addressRepository.findAll().stream()
                .map(addressMapper::addressToAddressGetDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public GenericResponseDto deleteByPostCode(String postCode) throws AddressNotFoundException {
        Address address = addressRepository.findAddressByPostCode(postCode).orElseThrow(AddressNotFoundException::new);
        addressRepository.delete(address);
        return new GenericResponseDto("Address was successfully deleted!");
    }


}
