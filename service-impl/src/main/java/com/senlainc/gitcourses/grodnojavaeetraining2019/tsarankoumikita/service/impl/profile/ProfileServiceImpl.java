package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.service.impl.profile;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.ProfileAlreadyExistsException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.ObjectNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.ProfileNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.UserNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.UsernameNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.profile.ProfileRepository;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.announcement.AnnouncementService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.announcement.CommentService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.profile.ProfileService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.profile.RatingService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.systemuser.UserService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.systemuser.UserDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.profile.Profile;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.CompressedAnnouncementDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.CompressedCommentDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.profile.ProfileDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProfileDto getById(int id) throws ProfileNotFoundException {
        Profile profile;
        try {
            profile = profileRepository.getById(id);
            return modelMapper.map(profile, ProfileDto.class);
        } catch (ObjectNotFoundException e) {
            throw new ProfileNotFoundException("such profile not found");
        }
    }

    @Override
    public List<ProfileDto> getAll() {
        return profileRepository.getAll().stream()
                .map(x -> modelMapper.map(x, ProfileDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void create(ProfileDto profileDto,@AuthenticationPrincipal UserDetails userDetails) throws UsernameNotFoundException, UserNotFoundException, ProfileAlreadyExistsException {
        if (getProfileByUsername(userDetails.getUsername()) != null) {
            throw new ProfileAlreadyExistsException("profile for this user already exists");
        }
        profileDto.setUser(userService.getByUsername(userDetails.getUsername()));
        profileDto.setCreateDate(LocalDateTime.now());
        Profile profileToRepo = modelMapper.map(profileDto, Profile.class);
        profileRepository.create(profileToRepo);
    }

    @Override
    public void update(ProfileDto profileDto, @AuthenticationPrincipal UserDetails userDetails) throws ProfileNotFoundException {
        userService.isActualUserARecordHolder(profileDto , userDetails);
        Profile profileToRepo = modelMapper.map(profileDto, Profile.class);
        try {
            profileRepository.update(profileToRepo, profileDto.getId());
        } catch (ObjectNotFoundException e) {
            throw new ProfileNotFoundException("such profile not found");
        }
    }

    @Override
    public void delete(int id, @AuthenticationPrincipal UserDetails userDetails) throws ProfileNotFoundException, AccessDeniedException {
        ProfileDto profileDto = getById(id);
        try {
            userService.isActualUserARecordHolder(profileDto, userDetails);
        } catch (AccessDeniedException e) {
            if(!userService.isUserPrincipalHaveAdminRole(userDetails)) {
                throw new AccessDeniedException("access denied to perform this action");
            }
        }
        Profile profile = modelMapper.map(profileDto, Profile.class);
        profileRepository.lazyDelete(profile);
    }

    @Override
    public List<CompressedCommentDto> getCommentsByProfileId(int id, int page, int limit) throws ProfileNotFoundException {
        getById(id);
        return commentService.getCommentsByProfileId(id, page, limit);
    }

    @Override
    public List<CompressedAnnouncementDto> getAnnouncementsByProfileId(int id, int page, int limit) throws ProfileNotFoundException {
        getById(id);
        return announcementService.getAnnouncementsByProfileId(id, page, limit);
    }

    @Override
    public List<CompressedAnnouncementDto> getAnnouncementsWithStatusSoldByProfileId(int id, int page, int limit) throws ProfileNotFoundException {
        getById(id);
        return announcementService.getAnnouncementsWithStatusSoldByProfileId(id, page, limit);
    }

    @Override
    public Integer getLastCreatedAnnouncementId(int id) throws ProfileNotFoundException {
        getById(id);
        return announcementService.getLastCreatedIdByProfileId(id);
    }

    @Override
    public ProfileDto getProfileByUsername(String username) throws UserNotFoundException, UsernameNotFoundException {
        UserDto userDto = userService.getByUsername(username);
        int userId = userDto.getId();
        Profile profile = profileRepository.getProfileByUserId(userId);
        return modelMapper.map(profile, ProfileDto.class);
    }
}
