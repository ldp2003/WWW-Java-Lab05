package iuh.edu.vn.backend.services;

import iuh.edu.vn.backend.models.Address;
import iuh.edu.vn.backend.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public void add(Address address) {
        addressRepository.save(address);
    }

    public void update(Address address) {
        addressRepository.save(address);
    }
}
