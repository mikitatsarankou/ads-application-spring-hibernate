package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.announcement;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.*;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.announcement.AnnouncementService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.CompressedAnnouncementDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.AnnouncementDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.PriorityAnnouncementDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping
    public List<CompressedAnnouncementDto> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "0") int categoryId,
            @RequestParam(defaultValue = "0") int locationId,
            @RequestParam(defaultValue = "null") String sortBy,
            @RequestParam(defaultValue = "desc") String sortType) {
        return announcementService.getAll(page, limit, categoryId, locationId, sortBy, sortType);
    }

    @GetMapping(value = "/with-comments")
    public List<AnnouncementDto> getAnnouncementsWithComments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "0") int categoryId,
            @RequestParam(defaultValue = "0") int locationId,
            @RequestParam(defaultValue = "null") String sortBy,
            @RequestParam(defaultValue = "desc") String sortType) {
        return announcementService.getAnnouncementsWithComments(page, limit, categoryId, locationId, sortBy, sortType);
    }

    @GetMapping(value = "/{id}/with-comments")
    public AnnouncementDto getAnnouncementWithComments(@PathVariable int id) throws AnnouncementNotFoundException {
        return announcementService.getAnnouncementByIdWithComments(id);
    }

    @GetMapping(value = "/{id}")
    public CompressedAnnouncementDto getById(@PathVariable int id) throws AnnouncementNotFoundException {
        return announcementService.getById(id);
    }

    @PutMapping
    public void update(@RequestBody CompressedAnnouncementDto compressedAnnouncementDto, @AuthenticationPrincipal UserDetails userDetails) throws AnnouncementNotFoundException, ProfileNotFoundException, UserNotFoundException, UsernameNotFoundException {
        announcementService.update(compressedAnnouncementDto, userDetails);
    }

    @PutMapping(value = "/set-status")
    public void setStatus(@RequestBody PriorityAnnouncementDto announcementDto) throws AnnouncementNotFoundException, ProfileNotFoundException, RatingNotFoundException {
        announcementService.setStatus(announcementDto);
    }

    @PutMapping(value = "/set-priority")
    public void setPriority(@RequestBody PriorityAnnouncementDto announcementDto) throws AnnouncementNotFoundException, PriorityNotFoundException {
        announcementService.setPriority(announcementDto);
    }

    @PutMapping(value = "/check-expire-date")
    public void checkExpireDate() {
        announcementService.checkExpireDate();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CompressedAnnouncementDto compressedAnnouncementDto, @AuthenticationPrincipal UserDetails userDetails)
            throws RatingNotFoundException, ProfileNotFoundException, UserNotFoundException, UsernameNotFoundException {
        announcementService.create(compressedAnnouncementDto, userDetails);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable int id, @AuthenticationPrincipal UserDetails userDetails) throws AnnouncementNotFoundException, ProfileNotFoundException, AccessDeniedException {
        announcementService.delete(id, userDetails);
    }
}
