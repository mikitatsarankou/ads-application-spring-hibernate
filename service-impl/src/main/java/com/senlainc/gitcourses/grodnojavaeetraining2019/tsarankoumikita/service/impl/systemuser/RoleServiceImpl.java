package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.service.impl.systemuser;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.ObjectNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.RoleNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.systemuser.RoleRepository;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.systemuser.RoleService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.enums.RoleName;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.systemuser.Role;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.systemuser.User;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.systemuser.RoleDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RoleDto getById(int id) throws RoleNotFoundException {
        try {
            Role role = roleRepository.getById(id);
            return modelMapper.map(role, RoleDto.class);
        } catch (ObjectNotFoundException e) {
            throw new RoleNotFoundException("such role ID not found");
        }
    }

    @Override
    public List<RoleDto> getAll() {
        return roleRepository.getAll().stream()
                .map(x -> modelMapper.map(x, RoleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void create(RoleDto roleDto) {
        Role roleToRepo = modelMapper.map(roleDto, Role.class);
        roleRepository.create(roleToRepo);
    }

    @Override
    public void update(RoleDto roleDto) throws RoleNotFoundException {
        Role roleToRepo = modelMapper.map(roleDto, Role.class);
        try {
            roleRepository.update(roleToRepo, roleToRepo.getId());
        } catch (ObjectNotFoundException e) {
            throw new RoleNotFoundException("such role not found");
        }
    }

    @Override
    public void delete(int id) throws RoleNotFoundException {
        try {
            roleRepository.delete(id);
        } catch (ObjectNotFoundException e) {
            throw new RoleNotFoundException("such role not found");
        }
    }


    @Override
    public RoleDto getByName(String name) throws RoleNotFoundException {
        RoleName roleName;
        try {
            roleName = RoleName.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RoleNotFoundException("such role does not exists");
        }
        Role role = roleRepository.getByName(roleName);
        return modelMapper.map(role, RoleDto.class);
    }

    @Override
    public List<User> getUsersByRole(String name) throws RoleNotFoundException {
        RoleName roleName;
        try {
            roleName = RoleName.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RoleNotFoundException("such role does not exists");
        }
        return roleRepository.getByName(roleName).getUsers();
    }
}
