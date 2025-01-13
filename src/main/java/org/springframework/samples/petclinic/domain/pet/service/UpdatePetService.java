package org.springframework.samples.petclinic.domain.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.domain.owner.model.Owner;
import org.springframework.samples.petclinic.domain.owner.service.OwnerUtilsService;
import org.springframework.samples.petclinic.domain.pet.dto.PetRequestDto;
import org.springframework.samples.petclinic.domain.pet.model.Pet;
import org.springframework.samples.petclinic.domain.pet.model.PetType;
import org.springframework.samples.petclinic.domain.pet.repository.PetRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatePetService {

	private final PetRepository petRepository;
	private final PetUtilsService petUtilsService;
	private final OwnerUtilsService ownerUtilsService;

	// Pet 수정
	public Pet updatePet(Integer id, PetRequestDto request) {
		Pet pet = petUtilsService.getPetOrThrow(id);
		PetType petType = petUtilsService.getPetTypeOrThrow(id);
		Owner owner = ownerUtilsService.findOwnerByIdOrThrow(request.getOwnerId());

		Pet updatedPet = pet.updatePet(request.getName(), request.getBirthDate(), petType, owner);

		return petRepository.save(updatedPet);
	}
}
