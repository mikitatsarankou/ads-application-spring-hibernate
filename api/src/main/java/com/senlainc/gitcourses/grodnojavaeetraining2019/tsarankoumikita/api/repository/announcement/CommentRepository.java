package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.announcement;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.GenericDAO;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.announcement.Comment;

import java.util.List;

public interface CommentRepository extends GenericDAO<Comment, Integer> {

    List<Comment> getAllComments(int offset, int limit);

    List<Comment> getCommentsByProfileId(int profileId, int offset, int limit);

    List<Comment> getCommentsByAnnouncementId(int announcementId);

}
