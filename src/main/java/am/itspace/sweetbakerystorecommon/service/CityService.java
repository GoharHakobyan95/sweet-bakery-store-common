package am.itspace.sweetbakerystorecommon.service;

import am.itspace.sweetbakerystorecommon.entity.City;
import am.itspace.sweetbakerystorecommon.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityService {

    private  final CityRepository cityRepository;

    public List<City> findAll() {
        return cityRepository.findAll();
    }

        public Optional<City> findByName (String name){
            return cityRepository.findByName(name);
        }

        public Optional<City> findById ( int cityId){
            return cityRepository.findById(cityId);
        }

        public void deleteById ( int id){
            cityRepository.deleteById(id);
        }

        public void save (City city){
            cityRepository.save(city);
        }
    }
