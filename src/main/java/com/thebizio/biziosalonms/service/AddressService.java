package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.dto.address.AddressDto;
import com.thebizio.biziosalonms.entity.Address;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    public void setAddressFields(Address address, AddressDto addressDto) {
        address.setStreetAddress1(addressDto.getStreetAddress1());
        address.setStreetAddress2(addressDto.getStreetAddress2());
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());
        address.setCountry(addressDto.getCountry());
        address.setZipcode(addressDto.getZipcode());
    }
}