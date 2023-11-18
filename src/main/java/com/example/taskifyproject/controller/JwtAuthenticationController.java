package com.example.taskifyproject.controller;


import com.example.taskifyproject.config.security.JwtTokenUtil;
import com.example.taskifyproject.dto.JwtRequest;
import com.example.taskifyproject.dto.request.SignUpRequestDto;
import com.example.taskifyproject.dto.request.UserRequestDto;
import com.example.taskifyproject.dto.response.AfterSignInResponseDto;
import com.example.taskifyproject.entity.OrganizationEntity;
import com.example.taskifyproject.entity.UserEntity;
import com.example.taskifyproject.enumuration.Role;
import com.example.taskifyproject.mapper.UserMapper;
import com.example.taskifyproject.repository.OrganizationRepository;
import com.example.taskifyproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationController {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtTokenUtil;
	private final UserDetailsService jwtInMemoryUserDetailsService;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final OrganizationRepository organizationRepository;








	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ResponseEntity<?>  signIn(@Valid @RequestBody JwtRequest request, BindingResult bindingResult)
			throws Exception {

		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			StringBuilder errorMsg = new StringBuilder("Validation error(s): ");
			for (FieldError error : errors) {
				errorMsg.append(error.getDefaultMessage()).append("; ");
			}
			return ResponseEntity.badRequest().body(errorMsg);
		}

		authenticate(request.getEmail(), request.getPassword());
		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(request.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails, Role.ADMIN.name());
		AfterSignInResponseDto signInResponseDto = AfterSignInResponseDto.builder()
				.token(token)
				.email(request.getEmail())
				.role(request.getRole())
				.build();

		return  ResponseEntity.ok(signInResponseDto);
	}


	@RequestMapping(value = "/signup",method = RequestMethod.POST)
	public ResponseEntity signUp (@Valid @RequestBody SignUpRequestDto dto){
		OrganizationEntity entity = organizationRepository.findOrganizationEntityByEmail(dto.getEmail());
		if (entity == null) {
			OrganizationEntity organizationEntity = OrganizationEntity.builder()
					.name(dto.getOrgName())
					.phoneNumber(dto.getPhoneNumber())
					.address(dto.getAddress())
					.username(dto.getUsername())
					.email(dto.getEmail())
					.password(passwordEncoder.encode(dto.getPassword()))
					.role(dto.getRole())
					.build();
			organizationRepository.save(organizationEntity);
			return ResponseEntity.ok("You signed!");
		}else
			return ResponseEntity.ok("This account already exist in our DB!");

	}


	@RequestMapping(value = "/register-user",method = RequestMethod.POST)
	public ResponseEntity registerUser (@Valid @RequestBody UserRequestDto dto){

		OrganizationEntity organizationEntity = organizationRepository.findById(dto.getOrganizationId()).orElseThrow();
		UserEntity entity = userRepository.findUsersEntityByEmail(dto.getEmail());
		if (entity == null) {
//			String randomPassword = generateDefaultPassword();
			UserEntity userEntity = UserEntity.builder()
					.name(dto.getName())
					.surname(dto.getSurname())
					.email(dto.getEmail())
					.password(passwordEncoder.encode(dto.getPassword()))
					.role(dto.getRole())
					.organization(organizationEntity)
					.build();
			userRepository.save(userEntity);
			return ResponseEntity.ok("You signed!");
		}else
			return ResponseEntity.ok("This account already exist in our DB!");

	}



	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}


	public static String generateDefaultPassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String rawPassword = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		return passwordEncoder.encode(rawPassword);
	}
}