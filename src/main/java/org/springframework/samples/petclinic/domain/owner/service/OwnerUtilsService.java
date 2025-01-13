package org.springframework.samples.petclinic.domain.owner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.common.error.OwnerErrorCode;
import org.springframework.samples.petclinic.common.exception.ApiException;
import org.springframework.samples.petclinic.domain.owner.dto.LoginRequestDto;
import org.springframework.samples.petclinic.domain.owner.dto.RegisterRequestDto;
import org.springframework.samples.petclinic.domain.owner.model.Owner;
import org.springframework.samples.petclinic.domain.owner.repository.OwnerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerUtilsService {

	private final OwnerRepository ownerRepository;
	private final PasswordEncoder passwordEncoder;

	public Owner findOwnerByIdOrThrow(Integer id) {
		return ownerRepository.findById(id)
			.orElseThrow(() -> new ApiException(OwnerErrorCode.NO_OWNER));
	}

	public void validateOwnerDoesNotExist(RegisterRequestDto registerRequestDto) {
		if (ownerRepository.existsByUserId(registerRequestDto.getUserId()))
			throw new ApiException(OwnerErrorCode.NO_OWNER);

	}

	public Owner findOwnerByOwnerIdOrThrow(LoginRequestDto loginRequestDto) {
		return ownerRepository.findByUserId(loginRequestDto.getUserId())
			.orElseThrow(() -> new ApiException(OwnerErrorCode.NO_OWNER));
	}

	public void validatePasswordOrThrow(LoginRequestDto loginRequestDto, Owner owner) {
		if (!passwordEncoder.matches(loginRequestDto.getPassword(), owner.getPassword()))
			throw new ApiException(OwnerErrorCode.INVALID_PASSWORD);
	}
}
