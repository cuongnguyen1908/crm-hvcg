package com.hvcg.api.crm.controller;

import com.hvcg.api.crm.constant.ERole;
import com.hvcg.api.crm.dto.JwtResponseDTO;
import com.hvcg.api.crm.dto.LoginDTO;
import com.hvcg.api.crm.dto.ResponseDTO;
import com.hvcg.api.crm.dto.SignupDTO;
import com.hvcg.api.crm.entity.Role;
import com.hvcg.api.crm.entity.User;
import com.hvcg.api.crm.exception.NotFoundException;
import com.hvcg.api.crm.repository.RoleRepository;
import com.hvcg.api.crm.repository.UserRepository;
import com.hvcg.api.crm.security.jwt.JwtUtils;
import com.hvcg.api.crm.security.service.UserDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {


    AuthenticationManager authenticationManager;

    @Autowired
    private ResponseDTO responseDTO;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;


    @PostMapping("/signin")
    public ResponseEntity<ResponseDTO> authenticateUser(@RequestBody LoginDTO dto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailImpl userDetails = (UserDetailImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        responseDTO.setContent(new JwtResponseDTO(userDetails.getUser().getId(),
                userDetails.getUser().getUsername(), roles, jwt));
        responseDTO.setMessage(null);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody SignupDTO dto) {
        if (userRepository.existsByUsername(dto.getEmail())) {
            throw new NotFoundException("Email has exist");
        }

        // Create new user's account
        User user = new User();
        user.setUsername(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setFullName(dto.getLastName() + " " + dto.getFirstName());
//      encoder.encode(dto.getPassword()));


        Set<String> strRoles = dto.getRole();
        Set<Role> roles = new HashSet<>();


        if (strRoles == null) {

            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":

                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        responseDTO.setContent(dto);
        responseDTO.setMessage("Register success");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

}
