package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.profile;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.ProfileAlreadyExistsException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.UsernameNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.ProfileNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.UserNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.profile.ProfileService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.CompressedAnnouncementDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.CompressedCommentDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.profile.ProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping
    public List<ProfileDto> getAll(){
        return profileService.getAll();
    }

    @GetMapping(value = "/{id}")
    public ProfileDto getById(@PathVariable int id) throws ProfileNotFoundException {
        return profileService.getById(id);
    }

    @GetMapping(value = "/{id}/comments")
    public List<CompressedCommentDto> getCommentsByProfileId(
            @PathVariable int id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int limit) throws ProfileNotFoundException {
        return profileService.getCommentsByProfileId(id, page, limit);
    }

    @GetMapping(value = "/{id}/announcements")
    public List<CompressedAnnouncementDto> getAnnouncementsByProfileId(
            @PathVariable int id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int limit) throws ProfileNotFoundException {
        return profileService.getAnnouncementsByProfileId(id, page, limit);
    }

    @GetMapping(value = "/{id}/sold-announcements")
    public List<CompressedAnnouncementDto> getAnnouncementsWithStatusSoldByProfileId(
            @PathVariable int id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int limit) throws ProfileNotFoundException {
        return profileService.getAnnouncementsWithStatusSoldByProfileId(id, page, limit);
    }


    @PutMapping
    public void update(@RequestBody ProfileDto profileDto, @AuthenticationPrincipal UserDetails userDetails) throws ProfileNotFoundException {
        profileService.update(profileDto, userDetails);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ProfileDto profileDto, @AuthenticationPrincipal UserDetails userDetails) throws UserNotFoundException, UsernameNotFoundException, ProfileAlreadyExistsException {
        profileService.create(profileDto, userDetails);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable int id, @AuthenticationPrincipal UserDetails userDetails) throws ProfileNotFoundException, AccessDeniedException {
        profileService.delete(id, userDetails);
    }
}
