package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.service.impl.profile;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.ObjectNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.ProfileNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.RatingNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.profile.RatingRepository;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.profile.ProfileService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.profile.RatingService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.profile.ProfileDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.profile.Rating;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.profile.RatingDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RatingDto getById(int id) throws RatingNotFoundException {
        try {
            Rating rating = ratingRepository.getById(id);
            return modelMapper.map(rating, RatingDto.class);
        } catch (ObjectNotFoundException e) {
            throw new RatingNotFoundException("such rating ID not found");
        }
    }

    public RatingDto getByProfileId(int profileId) throws RatingNotFoundException, ProfileNotFoundException {
        ProfileDto profileDto = profileService.getById(profileId);
        Rating rating;
        try {
            rating = ratingRepository.getByProfileId(profileId);
        } catch (RatingNotFoundException e) {
            RatingDto ratingDto = new RatingDto(profileDto, 0, 0);
            create(ratingDto);
            rating = ratingRepository.getByProfileId(profileId);
        }
        return modelMapper.map(rating, RatingDto.class);
    }

    @Override
    public List<RatingDto> getAll() {
        return ratingRepository.getAll().stream()
                .map(x -> modelMapper.map(x, RatingDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void create(RatingDto ratingDto) {
        ratingDto.setOverallRating(0);
        ratingDto.setSales(0);
        Rating ratingToRepo = modelMapper.map(ratingDto, Rating.class);
        ratingRepository.create(ratingToRepo);
    }

    @Override
    public void update(RatingDto ratingDto) throws RatingNotFoundException {
        Rating ratingToRepo = modelMapper.map(ratingDto, Rating.class);
        try {
            ratingRepository.update(ratingToRepo, ratingToRepo.getId());
        } catch (ObjectNotFoundException e) {
            throw new RatingNotFoundException("such rating not found");
        }
    }

    @Override
    public void delete(int id) throws RatingNotFoundException {
        try {
            ratingRepository.delete(id);
        } catch (ObjectNotFoundException e) {
            throw new RatingNotFoundException("such rating not found");
        }
    }

    @Override
    public void addPoints(int profileId, int points) throws ProfileNotFoundException, RatingNotFoundException {
        RatingDto ratingDto = getByProfileId(profileId);
        int currentOverallRating = ratingDto.getOverallRating();
        int recountedOverallRating = (currentOverallRating + points);
        if (points == Rating.SALE_POINTS) {
            ratingDto.setSales(ratingDto.getSales() + 1);
        }
        ratingDto.setOverallRating(recountedOverallRating);
        Rating rating = modelMapper.map(ratingDto, Rating.class);
        ratingRepository.lazyUpdate(rating);
    }
}
