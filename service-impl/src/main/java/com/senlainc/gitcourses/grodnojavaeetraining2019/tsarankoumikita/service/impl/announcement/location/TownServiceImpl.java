package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.service.impl.announcement.location;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.ObjectNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.TownNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.announcement.location.TownRepository;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.announcement.location.TownService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.announcement.location.Town;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.location.TownDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TownServiceImpl implements TownService {

    @Autowired
    private TownRepository townRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TownDto getById(int id) throws TownNotFoundException {
        try {
            Town town = townRepository.getById(id);
            return modelMapper.map(town, TownDto.class);
        } catch (ObjectNotFoundException e) {
            throw new TownNotFoundException("such town ID not found");
        }
    }

    @Override
    public List<TownDto> getAll() {
        return townRepository.getAll().stream()
                .map(x -> modelMapper.map(x, TownDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void create(TownDto townDto) {
        Town townToRepo = modelMapper.map(townDto, Town.class);
        townRepository.create(townToRepo);
    }

    @Override
    public void update(TownDto townDto) throws TownNotFoundException {
        Town townToRepo = modelMapper.map(townDto, Town.class);
        try {
            townRepository.update(townToRepo, townDto.getId());
        } catch (ObjectNotFoundException e) {
            throw new TownNotFoundException("such town not found");
        }
    }

    @Override
    public void delete(int id) throws TownNotFoundException {
        try {
            townRepository.delete(id);
        } catch (ObjectNotFoundException e) {
            throw new TownNotFoundException("such town not found");
        }
    }
}
