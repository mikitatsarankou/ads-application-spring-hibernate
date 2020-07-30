package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.announcement;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.*;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.CompressedCommentDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.CommentDto;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CommentService {

    List<CompressedCommentDto> getAll(int page, int limit);

    List<CommentDto> getCommentsWithAnnouncements(int page, int limit);

    List<CompressedCommentDto> getCommentsByAnnouncementId(int announcementId, int page, int limit) throws AnnouncementNotFoundException;

    List<CompressedCommentDto> getCommentsByProfileId(int profileId, int page, int limit) throws ProfileNotFoundException;

    CompressedCommentDto getById(int id) throws CommentNotFoundException;

    CommentDto getByIdWithAnnouncement(int id) throws CommentNotFoundException;

    void create(CommentDto commentDto, UserDetails userDetails) throws RatingNotFoundException, ProfileNotFoundException, UserNotFoundException, UsernameNotFoundException;

    void update(CommentDto commentDto, @AuthenticationPrincipal UserDetails userDetails) throws CommentNotFoundException, UserNotFoundException, UsernameNotFoundException;

    void delete(int id, @AuthenticationPrincipal UserDetails userDetails) throws CommentNotFoundException, AccessDeniedException;
}
