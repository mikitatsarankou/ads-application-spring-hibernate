package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.profile;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.ProfileAlreadyExistsException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.UsernameNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.ProfileNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.UserNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.CompressedAnnouncementDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.CompressedCommentDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.profile.ProfileDto;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ProfileService {

    List<ProfileDto> getAll();

    List<CompressedCommentDto> getCommentsByProfileId(int id, int page, int limit) throws ProfileNotFoundException;

    List<CompressedAnnouncementDto> getAnnouncementsByProfileId(int id, int page, int limit) throws ProfileNotFoundException;

    List<CompressedAnnouncementDto> getAnnouncementsWithStatusSoldByProfileId(int id, int page, int limit) throws ProfileNotFoundException;

    ProfileDto getById(int id) throws ProfileNotFoundException;

    void create(ProfileDto profileDto, UserDetails userDetails) throws UserNotFoundException, UsernameNotFoundException, ProfileAlreadyExistsException;

    void update(ProfileDto profileDto, UserDetails userDetails) throws ProfileNotFoundException;

    void delete(int id, UserDetails userDetails) throws ProfileNotFoundException, AccessDeniedException;

    Integer getLastCreatedAnnouncementId(int id) throws ProfileNotFoundException;

    ProfileDto getProfileByUsername(String username) throws UserNotFoundException, UsernameNotFoundException;
}
