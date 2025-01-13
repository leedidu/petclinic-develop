package org.springframework.samples.petclinic.domain.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.common.error.PetErrorCode;
import org.springframework.samples.petclinic.common.exception.ApiException;
import org.springframework.samples.petclinic.domain.owner.repository.OwnerRepository;
import org.springframework.samples.petclinic.domain.pet.dto.PetResponseDto;
import org.springframework.samples.petclinic.domain.pet.enums.PetStatus;
import org.springframework.samples.petclinic.domain.pet.mapper.PetMapper;
import org.springframework.samples.petclinic.domain.pet.model.Pet;
import org.springframework.samples.petclinic.domain.pet.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReadPetService {

	private final PetRepository petRepository;
	private final PetMapper petMapper;
	private final OwnerRepository ownerRepository;

	// 모든 Pet 조회
	public List<PetResponseDto> getAllPets() {
		return petRepository.findAllByStatusOrderById(PetStatus.REGISTERED).stream()
			.map(petMapper::toDto)
			.collect(Collectors.toList());
	}

	// 단일 Pet 조회
	public PetResponseDto getPetById(Integer id) {
		Pet pet = petRepository.findByIdAndStatus(id,PetStatus.REGISTERED)
			.orElseThrow(() -> new ApiException(PetErrorCode.NO_PET));
		return petMapper.toDto(pet);
	}

	// 주인의 펫 조회
	public List<PetResponseDto> getPetsByOwnerId(Integer ownerId) {
		ownerRepository.findById(ownerId)
			.orElseThrow(() -> new ApiException(PetErrorCode.INVALID_OWNER));

		return petRepository.findAllByStatusOrderById(PetStatus.REGISTERED).stream()
			.filter(pet -> pet.getOwner() != null && pet.getOwner().getId().equals(ownerId))
			.map(petMapper::toDto)
			.collect(Collectors.toList());
	}
}
