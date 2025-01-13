package org.springframework.samples.petclinic.domain.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.common.error.PetErrorCode;
import org.springframework.samples.petclinic.common.exception.ApiException;
import org.springframework.samples.petclinic.domain.owner.model.Owner;
import org.springframework.samples.petclinic.domain.owner.repository.OwnerRepository;
import org.springframework.samples.petclinic.domain.pet.dto.PetRequestDto;
import org.springframework.samples.petclinic.domain.pet.dto.PetResponseDto;
import org.springframework.samples.petclinic.domain.pet.enums.PetStatus;
import org.springframework.samples.petclinic.domain.pet.mapper.PetMapper;
import org.springframework.samples.petclinic.domain.pet.model.Pet;
import org.springframework.samples.petclinic.domain.pet.model.PetType;
import org.springframework.samples.petclinic.domain.pet.repository.PetRepository;
import org.springframework.samples.petclinic.domain.pet.repository.PetTypeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatePetService {

	private final PetRepository petRepository;
	private final OwnerRepository ownerRepository;
	private final PetTypeRepository petTypeRepository;
	private final PetMapper petMapper;

	// Pet 수정
	public PetResponseDto updatePet(Integer id, PetRequestDto request) {
		Pet pet = petRepository.findByIdAndStatus(id, PetStatus.REGISTERED)
			.orElseThrow(() -> new ApiException(PetErrorCode.NO_PET));

		PetType petType = petTypeRepository.findById(request.getTypeId())
			.orElseThrow(() -> new ApiException(PetErrorCode.INVALID_PET_TYPE));

		Owner owner = ownerRepository.findById(request.getOwnerId())
			.orElseThrow(() -> new ApiException(PetErrorCode.INVALID_OWNER));

		// 업데이트 반영
		pet.setName(request.getName());
		pet.setBirthDate(request.getBirthDate());
		pet.setType(petType);
		pet.setOwner(owner);

		Pet updatedPet = petRepository.save(pet);
		return petMapper.toDto(updatedPet);
	}
}
