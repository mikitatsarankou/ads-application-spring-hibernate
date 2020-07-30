package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.service.impl.announcement;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.*;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.announcement.CommentRepository;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.announcement.AnnouncementService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.announcement.CommentService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.profile.ProfileService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.profile.RatingService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.systemuser.UserService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.profile.ProfileDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.announcement.Comment;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.profile.Rating;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.CompressedCommentDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.CommentDto;
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
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    private int calculateOffset(int page, int limit) {
        if (page != 0) {
            return page * limit;
        } return 0;
    }

    @Override
    public CompressedCommentDto getById(int id) throws CommentNotFoundException {
        try {
            Comment comment = commentRepository.getById(id);
            return modelMapper.map(comment, CompressedCommentDto.class);
        } catch (ObjectNotFoundException e) {
            throw new CommentNotFoundException("such comment ID not found");
        }
    }

    @Override
    public CommentDto getByIdWithAnnouncement(int id) throws CommentNotFoundException {
        try {
            Comment comment = commentRepository.getById(id);
            return modelMapper.map(comment, CommentDto.class);
        } catch (ObjectNotFoundException e) {
            throw new CommentNotFoundException("such comment ID not found");
        }
    }

    @Override
    public List<CompressedCommentDto> getAll(int page, int limit) {
        int offset = calculateOffset(page, limit);
        return commentRepository.getAllComments(offset, limit).stream()
                .map(x -> modelMapper.map(x, CompressedCommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getCommentsWithAnnouncements(int page, int limit) {
        int offset = calculateOffset(page, limit);
        return commentRepository.getAllComments(offset, limit).stream()
                .map(x -> modelMapper.map(x, CommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CompressedCommentDto> getCommentsByProfileId(int id, int page, int limit) throws ProfileNotFoundException {
        profileService.getById(id);
        int offset = calculateOffset(page, limit);
        return commentRepository.getCommentsByProfileId(id, offset, limit).stream()
                .map(x -> modelMapper.map(x, CompressedCommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void create(CommentDto commentDto,@AuthenticationPrincipal UserDetails userDetails) throws RatingNotFoundException, ProfileNotFoundException, UserNotFoundException, UsernameNotFoundException {
        ProfileDto profileDto = profileService.getProfileByUsername(userDetails.getUsername());
        commentDto.setProfile(profileDto);
        commentDto.setCreateDate(LocalDateTime.now());
        int profileId = commentDto.getProfile().getId();
        Comment commentToRepo = modelMapper.map(commentDto, Comment.class);
        commentRepository.create(commentToRepo);
        ratingService.addPoints(profileId, Rating.COMMENT_POINTS);
    }

    @Override
    public void update(CommentDto commentDto, @AuthenticationPrincipal UserDetails userDetails) throws CommentNotFoundException, UserNotFoundException, UsernameNotFoundException {
        userService.isActualUserARecordHolder(commentDto, userDetails);
        ProfileDto profileDto = profileService.getProfileByUsername(userDetails.getUsername());
        commentDto.setProfile(profileDto);
        Comment commentToRepo = modelMapper.map(commentDto, Comment.class);
        try {
            commentRepository.update(commentToRepo, commentToRepo.getId());
        } catch (ObjectNotFoundException e) {
            throw new CommentNotFoundException("such comment not found");
        }
    }


    @Override
    public void delete(int id, @AuthenticationPrincipal UserDetails userDetails) throws CommentNotFoundException, AccessDeniedException {
        CompressedCommentDto compressedCommentDto = getById(id);
        try {
            userService.isActualUserARecordHolder(compressedCommentDto, userDetails);
        } catch (AccessDeniedException e) {
            if(!userService.isUserPrincipalHaveAdminRole(userDetails)) {
                throw new AccessDeniedException("access denied to perform this action");
            }
        }
        Comment commentToRepo = modelMapper.map(compressedCommentDto, Comment.class);
        commentRepository.lazyDelete(commentToRepo);
    }

    @Override
    public List<CompressedCommentDto> getCommentsByAnnouncementId(int announcementId, int page, int limit) throws AnnouncementNotFoundException {
        announcementService.getById(announcementId);
        return commentRepository.getCommentsByAnnouncementId(announcementId).stream()
                .map(x -> modelMapper.map(x, CompressedCommentDto.class))
                .collect(Collectors.toList());
    }
}
