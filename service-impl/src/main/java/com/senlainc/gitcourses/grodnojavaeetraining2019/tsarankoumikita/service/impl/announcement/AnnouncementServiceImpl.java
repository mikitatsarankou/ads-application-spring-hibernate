package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.service.impl.announcement;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.*;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.announcement.AnnouncementRepository;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.announcement.AnnouncementService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.profile.ProfileService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.profile.RatingService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.systemuser.UserService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.profile.ProfileDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.announcement.Announcement;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.profile.Profile;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.profile.Rating;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.CompressedAnnouncementDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.AnnouncementDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.PriorityAnnouncementDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.enums.AdStatusDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
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
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${is.schedule.enable.flag}")
    private boolean isEnableScheduleJob;

    private int calculateOffset(int page, int limit) {
        if (page != 0) {
            return page * limit;
        } return 0;
    }

    @Override
    public CompressedAnnouncementDto getById(int id) throws AnnouncementNotFoundException {
        try {
            Announcement announcement = announcementRepository.getById(id);
            return modelMapper.map(announcement, CompressedAnnouncementDto.class);
        } catch (ObjectNotFoundException e) {
            throw new AnnouncementNotFoundException("such announcement ID not found");
        }
    }

    @Override
    public AnnouncementDto getAnnouncementByIdWithComments(int id) throws AnnouncementNotFoundException {
        try {
            Announcement announcement = announcementRepository.getById(id);
            return modelMapper.map(announcement, AnnouncementDto.class);
        } catch (ObjectNotFoundException e) {
            throw new AnnouncementNotFoundException("such announcement ID not found");
        }
    }

    @Override
    public List<CompressedAnnouncementDto> getAll(int page, int limit, int categoryId, int locationId, String sortBy, String sortType) {
        int offset = calculateOffset(page, limit);
        return announcementRepository.getAllAnnouncements(offset, limit, categoryId, locationId, sortBy, sortType).stream()
                .map(x -> modelMapper.map(x, CompressedAnnouncementDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CompressedAnnouncementDto> getAnnouncementsByProfileId(int id, int page, int limit) {
        int offset = calculateOffset(page, limit);
        return announcementRepository.getAnnouncementsByProfileId(id, offset, limit).stream()
                .map(x -> modelMapper.map(x, CompressedAnnouncementDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CompressedAnnouncementDto> getAnnouncementsWithStatusSoldByProfileId(int id, int page, int limit) {
        int offset = calculateOffset(page, limit);
        return announcementRepository.getAnnouncementsWithStatusSoldByProfileId(id, offset, limit).stream()
                .map(x -> modelMapper.map(x, CompressedAnnouncementDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AnnouncementDto> getAnnouncementsWithComments(int page, int limit, int categoryId, int locationId, String sortBy, String sortType) {
        int offset = calculateOffset(page, limit);
        return announcementRepository.getAllAnnouncements(offset, limit, categoryId, locationId, sortBy, sortType).stream()
                .map(x -> modelMapper.map(x, AnnouncementDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void create(CompressedAnnouncementDto compressedAnnouncementDto,@AuthenticationPrincipal UserDetails userDetails) throws RatingNotFoundException, UserNotFoundException, UsernameNotFoundException, ProfileNotFoundException {
        ProfileDto profileDto = profileService.getProfileByUsername(userDetails.getUsername());
        compressedAnnouncementDto.setProfile(profileDto);
        compressedAnnouncementDto.setPriority(Announcement.DEFAULT_ANNOUNCEMENT_PRIORITY);
        compressedAnnouncementDto.setCreateDate(LocalDateTime.now());
        compressedAnnouncementDto.setStatus(AdStatusDto.OPEN);
        Announcement announcementToRepo = modelMapper.map(compressedAnnouncementDto, Announcement.class);
        announcementRepository.create(announcementToRepo);
        int profileId = profileDto.getId();
        ratingService.addPoints(profileId, Rating.ANNOUNCEMENT_POINTS);
    }

    @Override
    public void update(CompressedAnnouncementDto compressedAnnouncementDto,@AuthenticationPrincipal UserDetails userDetails) throws AnnouncementNotFoundException, UserNotFoundException, UsernameNotFoundException {
        userService.isActualUserARecordHolder(compressedAnnouncementDto, userDetails);
        ProfileDto profileDto = profileService.getProfileByUsername(userDetails.getUsername());
        compressedAnnouncementDto.setProfile(profileDto);
        Announcement announcementToRepo = modelMapper.map(compressedAnnouncementDto, Announcement.class);
        try {
            announcementRepository.update(announcementToRepo, announcementToRepo.getId());
        } catch (ObjectNotFoundException e) {
            throw new AnnouncementNotFoundException("such announcement not found");
        }
    }

    @Override
    public void delete(int id, @AuthenticationPrincipal UserDetails userDetails) throws AnnouncementNotFoundException, AccessDeniedException {
        CompressedAnnouncementDto compressedAnnouncementDto = getById(id);
        try {
            userService.isActualUserARecordHolder(compressedAnnouncementDto, userDetails);
        } catch (AccessDeniedException e) {
            if (!userService.isUserPrincipalHaveAdminRole(userDetails)) {
                throw new AccessDeniedException("access denied to perform this action");
            }
        }
        Announcement announcementToRepo = modelMapper.map(compressedAnnouncementDto, Announcement.class);
        announcementRepository.lazyDelete(announcementToRepo);
    }


    @Override
    public void setStatus(PriorityAnnouncementDto dto) throws AnnouncementNotFoundException, ProfileNotFoundException, RatingNotFoundException {
        CompressedAnnouncementDto announcementDto = getById(dto.getId());

        announcementDto.setStatus(dto.getStatus());

        if (dto.getStatus().equals(AdStatusDto.SOLD)) {
            ratingService.addPoints(announcementDto.getProfile().getId(), Rating.SALE_POINTS);
        }

        Announcement announcementToRepo = modelMapper.map(announcementDto, Announcement.class);
        announcementRepository.lazyUpdate(announcementToRepo);
    }

    @Override
    public void setPriority(PriorityAnnouncementDto dto) throws AnnouncementNotFoundException {
        CompressedAnnouncementDto announcementDto = getById(dto.getId());

        LocalDateTime payDate = LocalDateTime.now();
        LocalDateTime expireDate = payDate.plusDays(dto.getDays());

        announcementDto.setPriority(dto.getPriority());

        if (dto.getPriority() == Announcement.DEFAULT_ANNOUNCEMENT_PRIORITY) {
            announcementDto.setHighPriorityPayDate(null);
        } else {
            announcementDto.setHighPriorityPayDate(payDate);
            announcementDto.setPaidPriorityExpireDate(expireDate);
        }

        Announcement announcementToRepo = modelMapper.map(announcementDto, Announcement.class);
        announcementRepository.lazyUpdate(announcementToRepo);
    }

    @Override
    public Integer getLastCreatedIdByProfileId(int id) {
        return announcementRepository.getLastCreatedIdByProfileId(id);
    }

    @Override
    public List<Announcement> getAllWherePriorityNotDefault() {
        return announcementRepository.getAllWherePriorityNotDefault();
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?", zone = "GMT+3:00")
    public void checkExpireDate() {
        if (isEnableScheduleJob) {

            List<Announcement> announcements = getAllWherePriorityNotDefault();

            for (Announcement announcement : announcements) {
                if (announcement.getPaidPriorityExpireDate().isBefore(LocalDateTime.now())) {
                    announcement.setPriority(Announcement.DEFAULT_ANNOUNCEMENT_PRIORITY);
                    announcement.setHighPriorityPayDate(null);
                    announcementRepository.lazyUpdate(announcement);
                }
            }
        }
    }

    @Override
    public Profile getProfileFromAnnouncement(int id) {
        return announcementRepository.getProfileFromAnnouncement(id);
    }
}
