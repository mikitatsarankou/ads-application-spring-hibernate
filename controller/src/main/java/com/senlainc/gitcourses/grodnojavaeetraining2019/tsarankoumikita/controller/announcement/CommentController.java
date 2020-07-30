package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.announcement;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.*;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.announcement.CommentService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.systemuser.UserService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.CompressedCommentDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<CompressedCommentDto> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        return commentService.getAll(page, limit);
    }

    @GetMapping(value = "/with-announcement")
    public List<CommentDto> getCommentsWithAnnouncements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        return commentService.getCommentsWithAnnouncements(page, limit);
    }

    @GetMapping(value = "/from-announcement/{id}")
    public List<CompressedCommentDto> getCommentsByAnnouncementId(
            @PathVariable int id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) throws AnnouncementNotFoundException {
        return commentService.getCommentsByAnnouncementId(id, page, limit);
    }

    @GetMapping(value = "/{id}/with-announcement")
    public CommentDto getByIdWithAnnouncement(@PathVariable int id) throws CommentNotFoundException {
        return commentService.getByIdWithAnnouncement(id);
    }

    @GetMapping(value = "/{id}")
    public CompressedCommentDto getById(@PathVariable int id) throws CommentNotFoundException {
        return commentService.getById(id);
    }

    @PutMapping
    public void update(@RequestBody CommentDto commentDto, @AuthenticationPrincipal UserDetails userDetails) throws CommentNotFoundException, UserNotFoundException, UsernameNotFoundException {
        commentService.update(commentDto, userDetails);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CommentDto commentDto, @AuthenticationPrincipal UserDetails userDetails) throws RatingNotFoundException, ProfileNotFoundException, UserNotFoundException, UsernameNotFoundException {
        commentService.create(commentDto, userDetails);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable int id, @AuthenticationPrincipal UserDetails userDetails) throws CommentNotFoundException, AccessDeniedException {
        commentService.delete(id, userDetails);
    }
}
