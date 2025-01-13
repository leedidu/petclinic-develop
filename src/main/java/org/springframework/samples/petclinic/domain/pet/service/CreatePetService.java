package org.springframework.samples.petclinic.domain.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.common.error.PetErrorCode;
import org.springframework.samples.petclinic.common.exception.ApiException;
import org.springframework.samples.petclinic.domain.owner.model.Owner;
import org.springframework.samples.petclinic.domain.owner.service.OwnerUtilsService;
import org.springframework.samples.petclinic.domain.pet.dto.PetRequestDto;
import org.springframework.samples.petclinic.domain.pet.mapper.PetMapper;
import org.springframework.samples.petclinic.domain.pet.model.Pet;
import org.springframework.samples.petclinic.domain.pet.model.PetType;
import org.springframework.samples.petclinic.domain.pet.repository.PetRepository;
import org.springframework.samples.petclinic.domain.pet.repository.PetTypeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePetService {

	private final PetRepository petRepository;
	private final OwnerUtilsService ownerUtilsService;
	private final PetTypeRepository petTypeRepository;
	private final PetMapper petMapper;

	// Pet 생성
	public Pet createPet(PetRequestDto request) {
		PetType petType = petTypeRepository.findById(request.getTypeId())
			.orElseThrow(() -> new ApiException(PetErrorCode.INVALID_PET_TYPE));
		Owner owner = ownerUtilsService.findOwnerByIdOrThrow(request.getOwnerId());
		Pet pet = petMapper.toEntity(request, petType, owner);
		return petRepository.save(pet);
	}
}
