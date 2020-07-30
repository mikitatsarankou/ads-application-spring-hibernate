package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.announcement;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.*;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.CompressedAnnouncementDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.AnnouncementDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.PriorityAnnouncementDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.announcement.Announcement;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.profile.Profile;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface AnnouncementService {

    CompressedAnnouncementDto getById(int id) throws AnnouncementNotFoundException;

    AnnouncementDto getAnnouncementByIdWithComments(int id) throws AnnouncementNotFoundException;

    List<CompressedAnnouncementDto> getAll(int page, int limit, int categoryId, int locationId, String sortBy, String sortType);

    List<CompressedAnnouncementDto> getAnnouncementsByProfileId(int id, int page, int limit);

    List<CompressedAnnouncementDto> getAnnouncementsWithStatusSoldByProfileId(int id, int page, int limit);

    List<AnnouncementDto> getAnnouncementsWithComments(int page, int limit, int categoryId, int locationId, String sortBy, String sortType);

    void create(CompressedAnnouncementDto compressedAnnouncementDto, UserDetails userDetails) throws RatingNotFoundException, ProfileNotFoundException, UserNotFoundException, UsernameNotFoundException;

    void update(CompressedAnnouncementDto compressedAnnouncementDto, UserDetails userDetails) throws AnnouncementNotFoundException, ProfileNotFoundException, UserNotFoundException, UsernameNotFoundException;

    void delete(int id, UserDetails userDetails) throws AnnouncementNotFoundException, ProfileNotFoundException, AccessDeniedException;

    void setStatus(PriorityAnnouncementDto dto) throws AnnouncementNotFoundException, ProfileNotFoundException, RatingNotFoundException;

    void checkExpireDate();

    void setPriority(PriorityAnnouncementDto announcementDto) throws AnnouncementNotFoundException, PriorityNotFoundException;

    Integer getLastCreatedIdByProfileId(int id);

    Profile getProfileFromAnnouncement(int id);

    List<Announcement> getAllWherePriorityNotDefault();
}
