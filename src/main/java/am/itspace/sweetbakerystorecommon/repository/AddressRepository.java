package am.itspace.sweetbakerystorecommon.repository;

import am.itspace.sweetbakerystorecommon.entity.Address;
import am.itspace.sweetbakerystorecommon.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    Collection<Object> findByCity(City city);

}
