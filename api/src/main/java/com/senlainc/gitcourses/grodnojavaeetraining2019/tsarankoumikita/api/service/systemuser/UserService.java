package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.systemuser;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.UsernameAlreadyExistsException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.ProfileNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.UsernameNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.RoleNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.UserNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.CommentDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.CompressedAnnouncementDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.CompressedCommentDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.profile.ProfileDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.systemuser.UserDto;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    UserDto getById(int id) throws UserNotFoundException;

    UserDto getByUsername(String username) throws UsernameNotFoundException;

    List<UserDto> getAll();

    List<UserDto> getByRole(String role) throws UsernameNotFoundException, RoleNotFoundException;

    UserDto create(UserDto userDto) throws UsernameAlreadyExistsException, RoleNotFoundException;

    boolean isUserHaveAdminRole(UserDto userDto);

    boolean isUserPrincipalHaveAdminRole(@AuthenticationPrincipal UserDetails userDetails);

    boolean isActualUserARecordHolder(CompressedAnnouncementDto compressedDto, @AuthenticationPrincipal UserDetails userDetails) throws AccessDeniedException;

    boolean isActualUserARecordHolder(CommentDto commentDto, @AuthenticationPrincipal UserDetails userDetails) throws AccessDeniedException;

    boolean isActualUserARecordHolder(CompressedCommentDto commentDto, @AuthenticationPrincipal UserDetails userDetails) throws AccessDeniedException;

    boolean isActualUserARecordHolder(UserDto userDto, @AuthenticationPrincipal UserDetails userDetails) throws AccessDeniedException;

    boolean isActualUserARecordHolder(ProfileDto profileDto, @AuthenticationPrincipal UserDetails userDetails) throws AccessDeniedException;

    void update(UserDto userDto, UserDetails userDetails) throws UserNotFoundException;

    void delete(int id, UserDetails userDetails) throws UserNotFoundException, AccessDeniedException;
}
