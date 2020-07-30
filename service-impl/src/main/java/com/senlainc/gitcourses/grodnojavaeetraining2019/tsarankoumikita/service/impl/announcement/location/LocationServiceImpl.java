package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.service.impl.announcement.location;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.LocationNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.ObjectNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.announcement.location.LocationRepository;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.announcement.location.LocationService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.announcement.location.Location;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.location.LocationDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public LocationDto getById(int id) throws LocationNotFoundException {
        try {
            Location location = locationRepository.getById(id);
            return modelMapper.map(location, LocationDto.class);
        } catch (ObjectNotFoundException e) {
            throw new LocationNotFoundException("such location ID not found");
        }
    }

    @Override
    public List<LocationDto> getAll() {
        return locationRepository.getAll().stream()
                .map(x -> modelMapper.map(x, LocationDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void create(LocationDto locationDto) {
        Location locationToRepo = modelMapper.map(locationDto, Location.class);
        locationRepository.create(locationToRepo);
    }

    @Override
    public void update(LocationDto locationDto) throws LocationNotFoundException {
        Location locationToRepo = modelMapper.map(locationDto, Location.class);
        try {
            locationRepository.update(locationToRepo, locationToRepo.getId());
        } catch (ObjectNotFoundException e) {
            throw new LocationNotFoundException("such location not found");
        }
    }

    @Override
    public void delete(int id) throws LocationNotFoundException {
        try {
            locationRepository.delete(id);
        } catch (ObjectNotFoundException e) {
            throw new LocationNotFoundException("such location not found");
        }
    }
}
