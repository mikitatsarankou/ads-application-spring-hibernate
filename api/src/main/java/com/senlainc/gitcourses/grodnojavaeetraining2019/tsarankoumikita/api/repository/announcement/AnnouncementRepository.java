package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.announcement;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.GenericDAO;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.announcement.Announcement;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.profile.Profile;

import java.util.Arrays;
import java.util.List;

public interface AnnouncementRepository extends GenericDAO<Announcement, Integer> {

    List<Announcement> getAllWherePriorityNotDefault();

    List<Announcement> getAllAnnouncements(int offset, int limit, int categoryId, int locationId, String sortBy, String sortType);

    List<Announcement> getAnnouncementsByProfileId(int id, int offset, int limit);

    List<Announcement> getAnnouncementsWithStatusSoldByProfileId(int id, int offset, int limit);

    Integer getLastCreatedIdByProfileId(int id);

    Profile getProfileFromAnnouncement(int id);

}
