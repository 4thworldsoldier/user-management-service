package com.babbangona.usermanagement.services;

import com.babbangona.commons.library.dto.UserDto;
import com.babbangona.commons.library.dto.response.BaseResponse;
import com.babbangona.commons.library.entities.Role;
import com.babbangona.commons.library.entities.Tenant;
import com.babbangona.commons.library.entities.User;
import com.babbangona.commons.library.repo.RoleRepository;
import com.babbangona.commons.library.repo.TenantRepository;
import com.babbangona.commons.library.repo.UserRepository;
import com.babbangona.commons.library.utils.ResponseConstants;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public BaseResponse<UserDto> saveUser(UserDto dto) {
        BaseResponse response = new BaseResponse<>();
        User entity = new User();
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setUsername(dto.getUsername());

        //BeanUtils.copyProperties(dto, entity);

        Tenant tenant = tenantRepository.findById(dto.getTenantId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Tenant ID"));
        entity.setTenant(tenant);

        Set<Role> roles = roleRepository.findByIdIn(dto.getRoleIds());
        if (roles.isEmpty()) {
            throw new IllegalArgumentException("Invalid Role IDs provided");
        }
        entity.setUserRoles(roles);

        User savedUser = userRepository.save(entity);

        dto.setPassword("******");
        dto.setUserId(savedUser.getId());

        response = new BaseResponse<>(ResponseConstants.SUCCESS_CODE, ResponseConstants.SUCCESS_MESSAGE,dto);

        return response;
    }

    public boolean findByUsername(String username) {
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            return true;
        }
        return false;
    }

/*    public User mapToEntity(UserDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());

        if (dto.getTenantId() != null) {
            Tenant tenant = tenantRepository.findById(dto.getTenantId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Tenant ID"));
            user.setTenant(tenant);
        }

        if (dto.getRoleIds() != null && !dto.getRoleIds().isEmpty()) {
            Collection<Role> roles = roleRepository.findAllById(dto.getRoleIds());
            user.setUserRoles(roles);
        }

        return user;
    }*/

}
