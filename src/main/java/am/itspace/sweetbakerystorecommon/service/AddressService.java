package am.itspace.sweetbakerystorecommon.service;

import am.itspace.sweetbakerystorecommon.entity.Address;
import am.itspace.sweetbakerystorecommon.entity.City;
import am.itspace.sweetbakerystorecommon.repository.AddressRepository;
import am.itspace.sweetbakerystorecommon.repository.CityRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;

    public Page<Address> findPaginated(Pageable pageable) {
        Page<Address> addressPages = addressRepository.findAll(pageable);
        return new PageImpl<>(addressPages.getContent(), pageable, addressPages.getSize());
    }

    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    public void save(Address address, City city) {
        cityRepository.save(city);
        addressRepository.save(address);
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public Optional<Address> findById(Integer addressId) {
        return addressRepository.findById(addressId);
    }

    public void deleteAddressById(int id) {
        addressRepository.deleteById(id);
    }
}
