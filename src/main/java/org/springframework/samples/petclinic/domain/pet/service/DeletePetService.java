package org.springframework.samples.petclinic.domain.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.domain.pet.enums.PetStatus;
import org.springframework.samples.petclinic.domain.pet.model.Pet;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletePetService {

	private final PetUtilsService petUtilsService;

	// Pet 삭제
	public void deletePet(Integer id) {
		Pet pet = petUtilsService.getPetOrThrow(id);
		pet.setStatus(PetStatus.DELETED);
	}
}
