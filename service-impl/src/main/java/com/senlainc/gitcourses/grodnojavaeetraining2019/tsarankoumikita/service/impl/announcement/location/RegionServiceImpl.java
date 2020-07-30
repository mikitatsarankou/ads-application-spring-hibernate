package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.service.impl.announcement.location;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.ObjectNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.RegionNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.announcement.location.RegionRepository;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.announcement.location.RegionService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.announcement.location.Region;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.location.RegionDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RegionDto getById(int id) throws RegionNotFoundException {
        try {
            Region region = regionRepository.getById(id);
            return modelMapper.map(region, RegionDto.class);
        } catch (ObjectNotFoundException e) {
            throw new RegionNotFoundException("such region ID not found");
        }
    }

    @Override
    public List<RegionDto> getAll() {
        return regionRepository.getAll().stream()
                .map(x -> modelMapper.map(x, RegionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void create(RegionDto regionDto) {
        Region regionToRepo = modelMapper.map(regionDto, Region.class);
        regionRepository.create(regionToRepo);
    }

    @Override
    public void update(RegionDto regionDto) throws RegionNotFoundException {
        Region regionToRepo = modelMapper.map(regionDto, Region.class);
        try {
            regionRepository.update(regionToRepo, regionToRepo.getId());
        } catch (ObjectNotFoundException e) {
            throw new RegionNotFoundException("such region not found");
        }

    }

    @Override
    public void delete(int id) throws RegionNotFoundException {
        try {
            regionRepository.delete(id);
        } catch (ObjectNotFoundException e) {
            throw new RegionNotFoundException("such region not found");
        }
    }
}
