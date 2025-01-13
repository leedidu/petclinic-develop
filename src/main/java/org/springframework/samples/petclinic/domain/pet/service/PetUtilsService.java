package org.springframework.samples.petclinic.domain.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.common.error.PetErrorCode;
import org.springframework.samples.petclinic.common.exception.ApiException;
import org.springframework.samples.petclinic.domain.pet.enums.PetStatus;
import org.springframework.samples.petclinic.domain.pet.model.Pet;
import org.springframework.samples.petclinic.domain.pet.model.PetType;
import org.springframework.samples.petclinic.domain.pet.repository.PetRepository;
import org.springframework.samples.petclinic.domain.pet.repository.PetTypeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetUtilsService {

	private final PetRepository petRepository;
	private final PetTypeRepository petTypeRepository;

	public Pet getPetOrThrow(int id) {
		return petRepository.findByIdAndStatus(id, PetStatus.REGISTERED)
			.orElseThrow(() -> new ApiException(PetErrorCode.NO_PET));
	}

	public PetType getPetTypeOrThrow(int id) {
		return petTypeRepository.findById(id)
			.orElseThrow(() -> new ApiException(PetErrorCode.INVALID_PET_TYPE));
	}
}
