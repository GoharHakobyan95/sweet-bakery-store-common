package am.itspace.sweetbakerystorecommon.service;

import am.itspace.sweetbakerystorecommon.entity.City;
import am.itspace.sweetbakerystorecommon.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    public Page<City> findPaginated(Pageable pageable) {
        Page<City> categoryPages = cityRepository.findAll(pageable);
        return new PageImpl<>(categoryPages.getContent(), pageable, categoryPages.getSize());
    }

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public Optional<City> findByName(String name) {
        return cityRepository.findByName(name);
    }

    public Optional<City> findById(int cityId) {
        return cityRepository.findById(cityId);
    }

    public void deleteById(int id) {
        cityRepository.deleteById(id);
    }

    public City save(City city) {
        return cityRepository.save(city);
    }

    public CityResponseDto updateCity(City city, UpdateCityDto updateCityDto) {
        city.setId(updateCityDto.getId());
        city.setName(updateCityDto.getName());
        City updatedCity = cityRepository.save(city);
        return modelMapper.map(updatedCity, CityResponseDto.class);
    }
}
