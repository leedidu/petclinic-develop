package org.springframework.samples.petclinic.domain.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.domain.owner.service.OwnerUtilsService;
import org.springframework.samples.petclinic.domain.pet.enums.PetStatus;
import org.springframework.samples.petclinic.domain.pet.model.Pet;
import org.springframework.samples.petclinic.domain.pet.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadPetService {

	private final PetRepository petRepository;
	private final PetUtilsService petUtilsService;
	private final OwnerUtilsService ownerUtilsService;

	// 모든 Pet 조회
	public List<Pet> getAllPets() {
		return petRepository.findAllByStatusOrderById(PetStatus.REGISTERED);
	}

	// 단일 Pet 조회
	public Pet getPetById(Integer id) {
		return petUtilsService.getPetOrThrow(id);
	}

	// 주인의 펫 조회
	public List<Pet> getPetsByOwnerId(Integer ownerId) {
		ownerUtilsService.findOwnerByIdOrThrow(ownerId);
		return petRepository.findAllByStatusOrderById(PetStatus.REGISTERED);
	}
}
