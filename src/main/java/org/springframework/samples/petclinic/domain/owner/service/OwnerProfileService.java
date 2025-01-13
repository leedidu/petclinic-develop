package org.springframework.samples.petclinic.domain.owner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.common.error.OwnerErrorCode;
import org.springframework.samples.petclinic.common.exception.ApiException;
import org.springframework.samples.petclinic.domain.owner.dto.UpdatePasswordRequestDto;
import org.springframework.samples.petclinic.domain.owner.dto.UpdateProfileRequestDto;
import org.springframework.samples.petclinic.domain.owner.model.Owner;
import org.springframework.samples.petclinic.domain.owner.repository.OwnerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerProfileService {

	private final OwnerRepository ownerRepository;
	private final PasswordEncoder passwordEncoder;
	private final OwnerUtilsService ownerUtilsService;

	// 회원 프로필 수정
	public Owner updateProfile(Integer id, UpdateProfileRequestDto request) throws ApiException {
		Owner owner = ownerUtilsService.findOwnerByIdOrThrow(id);
		Owner updatedOwner = owner.updateOwner(request.getName(), request.getAddress(), request.getCity(), request.getTelephone());
		return ownerRepository.save(updatedOwner);
	}

	// 회원 비밀번호 변경
	public void updatePassword(Integer id, UpdatePasswordRequestDto updatePasswordRequestDto) {
		Owner owner = ownerUtilsService.findOwnerByIdOrThrow(id);
		validateCurrentPassword(updatePasswordRequestDto, owner);
		updateOwnerPassword(updatePasswordRequestDto, owner);
		ownerRepository.save(owner);
	}

	private void validateCurrentPassword(UpdatePasswordRequestDto updatePasswordRequestDto, Owner owner) {
		if (!passwordEncoder.matches(updatePasswordRequestDto.getCurrentPassword(), owner.getPassword()))
			throw new ApiException(OwnerErrorCode.NO_OWNER);
	}

	private void updateOwnerPassword(UpdatePasswordRequestDto updatePasswordRequestDto, Owner owner) {
		String encryptedPassword = passwordEncoder.encode(updatePasswordRequestDto.getNewPassword());
		owner.updatePassword(encryptedPassword);
	}
}
