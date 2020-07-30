package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.service.impl.systemuser;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.UsernameAlreadyExistsException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.*;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.systemuser.UserRepository;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.announcement.AnnouncementService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.profile.ProfileService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.systemuser.RoleService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.systemuser.UserService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.CommentDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.CompressedAnnouncementDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.CompressedCommentDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.profile.ProfileDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.enums.RoleName;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.systemuser.Role;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.systemuser.User;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.enums.RoleNameDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.systemuser.RoleDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.systemuser.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws org.springframework.security.core.userdetails.UsernameNotFoundException {
        try {
            User user = userRepository.getByUsername(username);
            return new UserPrincipal(user);
        } catch (UsernameNotFoundException e) {
            throw new org.springframework.security.core.userdetails.UsernameNotFoundException("such username not found!");
        }
    }

    @Override
    public UserDto getById(int id) throws UserNotFoundException {
        try {
            User user = userRepository.getById(id);
            return modelMapper.map(user, UserDto.class);
        } catch (ObjectNotFoundException e) {
            throw new UserNotFoundException("such user not found");
        }
    }

    @Override
    public UserDto getByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("such username not found");
        }
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getByRole(String roleName) throws RoleNotFoundException {
        List<User> users = roleService.getUsersByRole(roleName);
        return users.stream().map(x -> modelMapper.map(x, UserDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public List<UserDto> getAll() {
        return userRepository.getAll().stream()
                .map(x -> modelMapper.map(x, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto create(UserDto userDto) throws UsernameAlreadyExistsException, RoleNotFoundException {
        try {
            getByUsername(userDto.getUsername());
            throw new UsernameAlreadyExistsException("such username already exists");
        } catch (UsernameNotFoundException e) {
            String encodedPassword = passwordEncoder.encode(userDto.getPassword());
            userDto.setPassword(encodedPassword);
            if (userDto.getRoles() == null) {
                RoleDto userRole = roleService.getByName(RoleNameDto.USER.name());
                List<RoleDto> roles = new ArrayList<>();
                roles.add(userRole);
                userDto.setRoles(roles);
            }
            User userToRepo = modelMapper.map(userDto, User.class);
            userRepository.create(userToRepo);
            return userDto;
        }
    }

    @Override
    public void update(UserDto userDto, UserDetails userDetails) throws UserNotFoundException {
        isActualUserARecordHolder(userDto, userDetails);
        User userToRepo = modelMapper.map(userDto, User.class);
        try {
            userRepository.update(userToRepo, userToRepo.getId());
        } catch (ObjectNotFoundException e) {
            throw new UserNotFoundException("such user not found");
        }
    }

    @Override
    public void delete(int id, UserDetails userDetails) throws UserNotFoundException, AccessDeniedException {
        UserDto userDto = getById(id);
        try {
            isActualUserARecordHolder(userDto, userDetails);
        } catch (AccessDeniedException e) {
            if(!isUserPrincipalHaveAdminRole(userDetails)) {
                throw new AccessDeniedException("access denied to perform this action");
            };
        }
        User userToRepo = modelMapper.map(userDto, User.class);
        userRepository.lazyDelete(userToRepo);
    }

    @Override
    public boolean isUserHaveAdminRole(UserDto userDto) {
        List<RoleDto> roles = userDto.getRoles();
        for (RoleDto roleDto : roles) {
            if (roleDto.getName().equals(RoleNameDto.ADMIN)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isUserPrincipalHaveAdminRole(@AuthenticationPrincipal UserDetails userDetails) {
        List<Role> roles = (List<Role>) userDetails.getAuthorities();
        for (Role role : roles) {
            if (role.getName().equals(RoleName.ADMIN.name())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isActualUserARecordHolder(CommentDto compressedDto, UserDetails userDetails) throws AccessDeniedException {
        if (isUserPrincipalHaveAdminRole(userDetails)) {
            return true;
        } else {
            String recordHolder = compressedDto.getProfile().getUser().getUsername();
            String actualUsername = userDetails.getUsername();
            if (recordHolder.equals(actualUsername)) {
                return true;
            } else {
                throw new AccessDeniedException("access denied to perform this action");
            }
        }
    }

    @Override
    public boolean isActualUserARecordHolder(CompressedAnnouncementDto compressedDto, @AuthenticationPrincipal UserDetails userDetails) throws AccessDeniedException {
        if (isUserPrincipalHaveAdminRole(userDetails)) {
            return true;
        } else {
            String recordHolder = announcementService.getProfileFromAnnouncement(compressedDto.getId())
                    .getUser().getUsername();
            String actualUsername = userDetails.getUsername();
            if (recordHolder.equals(actualUsername)) {
                return true;
            } else {
                throw new AccessDeniedException("access denied to perform this action");
            }
        }
    }

    @Override
    public boolean isActualUserARecordHolder(CompressedCommentDto compressedDto, @AuthenticationPrincipal UserDetails userDetails) throws AccessDeniedException {
        if (isUserPrincipalHaveAdminRole(userDetails)) {
            return true;
        } else {
            String recordHolder = compressedDto.getProfile().getUser().getUsername();
            String actualUsername = userDetails.getUsername();
            if (recordHolder.equals(actualUsername)) {
                return true;
            } else {
                throw new AccessDeniedException("access denied to perform this action");
            }
        }
    }

    @Override
    public boolean isActualUserARecordHolder(UserDto userDto, @AuthenticationPrincipal UserDetails userDetails) throws AccessDeniedException{
        String recordHolder = userDto.getUsername();
        String actualUsername = userDetails.getUsername();
        if(recordHolder.equals(actualUsername)) {
            return true;
        } else {
            throw new AccessDeniedException("access denied to perform this action");
        }
    }

    @Override
    public boolean isActualUserARecordHolder(ProfileDto profileDto, @AuthenticationPrincipal UserDetails userDetails) throws AccessDeniedException {
        String recordHolder = profileDto.getUser().getUsername();
        String actualUsername = userDetails.getUsername();
        if (recordHolder.equals(actualUsername)) {
            return true;
        } else {
            throw new AccessDeniedException("access denied to perform this action");
        }
    }
}
