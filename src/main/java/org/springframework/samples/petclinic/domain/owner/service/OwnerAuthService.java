package org.springframework.samples.petclinic.domain.owner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.domain.owner.dto.LoginRequestDto;
import org.springframework.samples.petclinic.domain.owner.dto.RegisterRequestDto;
import org.springframework.samples.petclinic.domain.owner.mapper.OwnerMapper;
import org.springframework.samples.petclinic.domain.owner.model.Owner;
import org.springframework.samples.petclinic.domain.owner.repository.OwnerRepository;
import org.springframework.samples.petclinic.domain.token.dto.TokenResponseDto;
import org.springframework.samples.petclinic.domain.token.service.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Service
@RequiredArgsConstructor
public class OwnerAuthService {

	private final OwnerRepository ownerRepository;
	private final TokenService tokenService;
	private final PasswordEncoder passwordEncoder;
	private final OwnerMapper ownerMapper;
	private final OwnerUtilsService ownerUtilsService;

	// 회원가입
	public Owner register(RegisterRequestDto registerRequestDto) {
		ownerUtilsService.validateOwnerDoesNotExist(registerRequestDto);

		String encryptedPassword = passwordEncoder.encode(registerRequestDto.getPassword());

		Owner owner = ownerMapper.toRegisterEntity(registerRequestDto);
		owner.setPassword(encryptedPassword);
		return ownerRepository.save(owner);
	}

	// 로그인
	public TokenResponseDto login(LoginRequestDto loginRequestDto) {
		Owner owner = ownerUtilsService.findOwnerByOwnerIdOrThrow(loginRequestDto);
		ownerUtilsService.validatePasswordOrThrow(loginRequestDto, owner);
		return tokenService.issueToken(owner.getId());
	}

	// 토큰 재발급
	public TokenResponseDto tokens() {
		var requestContext = RequestContextHolder.getRequestAttributes();
		var ownerId = requestContext.getAttribute("ownerId", RequestAttributes.SCOPE_REQUEST);
		return tokenService.issueToken((Integer) ownerId);
	}
}
