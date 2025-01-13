package org.springframework.samples.petclinic.domain.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.common.error.PetErrorCode;
import org.springframework.samples.petclinic.common.exception.ApiException;
import org.springframework.samples.petclinic.domain.pet.enums.PetStatus;
import org.springframework.samples.petclinic.domain.pet.model.Pet;
import org.springframework.samples.petclinic.domain.pet.repository.PetRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletePetService {

	private final PetRepository petRepository;

	// Pet 삭제
	public void deletePet(Integer id) {
		Pet pet = petRepository.findByIdAndStatus(id, PetStatus.REGISTERED)
			.filter(Pet::isRegistered)
			.orElseThrow(() -> new ApiException(PetErrorCode.NO_PET));
		pet.setStatus(PetStatus.DELETED);
	}
}
