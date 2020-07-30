import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.*;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.announcement.AnnouncementService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.profile.ProfileService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.profile.RatingService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.config.web.WebConfig;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.CategoryDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.CompressedAnnouncementDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.PriorityAnnouncementDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.location.LocationDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.enums.AdStatusDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.profile.ProfileDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.profile.RatingDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.announcement.Announcement;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.profile.Rating;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
public class AnnouncementServiceTest {

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private RatingService ratingService;

    private UserDetails testUserDetails;

    private CompressedAnnouncementDto dto;

    private PriorityAnnouncementDto priorityDto;

    @Before
    public void testUserDetails() {
        testUserDetails = userDetailsService.loadUserByUsername("test");
    }

    @Before
    public void initAnnouncementDto() {
        dto = new CompressedAnnouncementDto();
        dto.setAnnouncementText("тестовое объявление для проверки");
        dto.setCategory(new CategoryDto(1));
        dto.setLocation(new LocationDto(4));
        dto.setProfile(new ProfileDto(2));
        dto.setPrice(1000);
    }

    @Before
    public void initPriorityAnnouncementDto() {
        priorityDto = new PriorityAnnouncementDto();
        priorityDto.setDays(7);
        priorityDto.setPriority(Announcement.HIGH_ANNOUNCEMENT_PRIORITY);
    }

    @Test
    public void create_announcement_test() throws ProfileNotFoundException, RatingNotFoundException, AnnouncementNotFoundException,
            UserNotFoundException, UsernameNotFoundException {

        Integer profileId = dto.getProfile().getId();
        RatingDto profileRating = ratingService.getByProfileId(profileId);

        int profileOverallRatingBefore = profileRating.getOverallRating();
        int expectedOverallRatingAfter1 = profileOverallRatingBefore + Rating.ANNOUNCEMENT_POINTS;

        dto.setAnnouncementText(dto.getAnnouncementText() + " создания");
        announcementService.create(dto, testUserDetails);
        int actualRating1 = ratingService.getByProfileId(profileId).getOverallRating();

        Assert.assertEquals(expectedOverallRatingAfter1, actualRating1);

        Integer announcementId = profileService.getLastCreatedAnnouncementId(profileId);

        Assert.assertNotNull(announcementService.getById(announcementId));
    }

    @Test
    public void update_announcement_test() throws ProfileNotFoundException, RatingNotFoundException, UserNotFoundException, UsernameNotFoundException, AnnouncementNotFoundException, PriorityNotFoundException {
        Integer profileId = dto.getProfile().getId();
        RatingDto profileRating = ratingService.getByProfileId(profileId);

        int profileOverallRatingBefore = profileRating.getOverallRating();
        int expectedOverallRatingAfter1 = profileOverallRatingBefore + Rating.ANNOUNCEMENT_POINTS;

        dto.setAnnouncementText(dto.getAnnouncementText() + " обновления");
        announcementService.create(dto, testUserDetails);
        int actualRating1 = ratingService.getByProfileId(profileId).getOverallRating();

        Assert.assertEquals(expectedOverallRatingAfter1, actualRating1);

        Integer announcementId = profileService.getLastCreatedAnnouncementId(profileId);

        Assert.assertNotNull(announcementService.getById(announcementId));

        priorityDto.setId(announcementId);
        announcementService.setPriority(priorityDto);
        int actualPriority = announcementService.getById(announcementId).getPriority();

        Assert.assertEquals(Announcement.HIGH_ANNOUNCEMENT_PRIORITY, actualPriority);

        CompressedAnnouncementDto compressedAnnouncementDto = announcementService.getById(announcementId);
        compressedAnnouncementDto.setPrice(1500);

        announcementService.update(compressedAnnouncementDto, testUserDetails);
        int actualPrice = announcementService.getById(announcementId).getPrice();

        Assert.assertEquals(1500, actualPrice);

        priorityDto.setStatus(AdStatusDto.SOLD);
        announcementService.setStatus(priorityDto);
        AdStatusDto actualStatus = announcementService.getById(announcementId).getStatus();

        Assert.assertEquals(AdStatusDto.SOLD, actualStatus);

        int expectedOverallRatingAfter2 = actualRating1 + Rating.SALE_POINTS;
        int actualRating2 = ratingService.getByProfileId(profileId).getOverallRating();

        Assert.assertEquals(expectedOverallRatingAfter2, actualRating2);
    }

    @Test(expected = AnnouncementNotFoundException.class)
    public void delete_announcement_test() throws ProfileNotFoundException, RatingNotFoundException, UserNotFoundException, UsernameNotFoundException, AnnouncementNotFoundException {
        Integer profileId = dto.getProfile().getId();
        RatingDto profileRating = ratingService.getByProfileId(profileId);

        int profileOverallRatingBefore = profileRating.getOverallRating();
        int expectedOverallRatingAfter1 = profileOverallRatingBefore + Rating.ANNOUNCEMENT_POINTS;

        dto.setAnnouncementText(dto.getAnnouncementText() + " удаления");
        announcementService.create(dto, testUserDetails);
        Integer announcementId = profileService.getLastCreatedAnnouncementId(profileId);
        Assert.assertNotNull(announcementService.getById(announcementId));

        int actualRating1 = ratingService.getByProfileId(profileId).getOverallRating();

        Assert.assertEquals(expectedOverallRatingAfter1, actualRating1);

        announcementService.delete(announcementId, testUserDetails);

        Assert.assertNull(announcementService.getById(announcementId));
    }

    @Test
    public void schedule_method_test() throws ProfileNotFoundException, RatingNotFoundException, UserNotFoundException, UsernameNotFoundException, AnnouncementNotFoundException {

        Integer profileId = dto.getProfile().getId();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime payDate = now.minusDays(7);
        LocalDateTime expireDate = payDate.plusDays(3);

        dto.setHighPriorityPayDate(payDate);
        dto.setPaidPriorityExpireDate(expireDate);
        dto.setPriority(Announcement.ABOVE_AVERAGE_ANNOUNCEMENT_PRIORITY);
        dto.setAnnouncementText(dto.getAnnouncementText() + " шеделинга");

        announcementService.create(dto, testUserDetails);

        Integer announcementId = profileService.getLastCreatedAnnouncementId(profileId);

        Assert.assertNotNull(announcementService.getById(announcementId));

        Assert.assertEquals(Announcement.ABOVE_AVERAGE_ANNOUNCEMENT_PRIORITY, announcementService.getById(announcementId).getPriority());

        List<Announcement> announcements = announcementService.getAllWherePriorityNotDefault();
        announcementService.checkExpireDate();
        List<Announcement> announcementsAfter = announcementService.getAllWherePriorityNotDefault();

        Assert.assertEquals(Announcement.DEFAULT_ANNOUNCEMENT_PRIORITY, announcementService.getById(announcementId).getPriority());

        Assert.assertNotEquals(announcements, announcementsAfter);
    }
}