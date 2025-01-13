package org.springframework.samples.petclinic.domain.pet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.domain.pet.dto.PetRequestDto;
import org.springframework.samples.petclinic.domain.pet.dto.PetResponseDto;
import org.springframework.samples.petclinic.domain.pet.mapper.PetMapper;
import org.springframework.samples.petclinic.domain.pet.model.Pet;
import org.springframework.samples.petclinic.domain.pet.service.CreatePetService;
import org.springframework.samples.petclinic.domain.pet.service.DeletePetService;
import org.springframework.samples.petclinic.domain.pet.service.ReadPetService;
import org.springframework.samples.petclinic.domain.pet.service.UpdatePetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pet")
@RequiredArgsConstructor
public class PetController {

	private final CreatePetService createPetService;
	private final ReadPetService readPetService;
	private final UpdatePetService updatePetService;
	private final DeletePetService deletePetService;
	private final PetMapper petMapper;

	// 모든 Pet 조회
	@GetMapping
	public ResponseEntity<List<PetResponseDto>> getAllPets() {
		List<Pet> pets = readPetService.getAllPets();
		List<PetResponseDto> response = petMapper.toListDto(pets);
		return ResponseEntity.ok(response);
	}

	// 단일 Pet 조회
	@GetMapping("/{petId}")
	public ResponseEntity<PetResponseDto> getPetById(@PathVariable("petId") Integer petId) {
		Pet pet = readPetService.getPetById(petId);
		PetResponseDto response = petMapper.toDto(pet);
		return ResponseEntity.ok(response);
	}

	// 주인의 펫 조회
	@GetMapping("/owner/{ownerId}")
	public ResponseEntity<List<PetResponseDto>> getPetsByOwnerId(@PathVariable("ownerId") Integer ownerId) {
		List<Pet> pets = readPetService.getPetsByOwnerId(ownerId);
		List<PetResponseDto> response = petMapper.toListDto(pets);
		return ResponseEntity.ok(response);
	}

	// Pet 생성
	@PostMapping
	public ResponseEntity<PetResponseDto> createPet(@RequestBody PetRequestDto request) {
		Pet pet = createPetService.createPet(request);
		PetResponseDto response = petMapper.toDto(pet);
		return ResponseEntity.ok(response);
	}

	// Pet 수정
	@PutMapping("/{petId}")
	public ResponseEntity<PetResponseDto> updatePet(@PathVariable("petId") Integer petId, @RequestBody PetRequestDto request) {
		Pet pet = updatePetService.updatePet(petId, request);
		PetResponseDto response = petMapper.toDto(pet);
		return ResponseEntity.ok(response);
	}

	// Pet 삭제
	@DeleteMapping("/{petId}")
	public ResponseEntity<Void> deletePet(@PathVariable("petId") Integer petId) {
		deletePetService.deletePet(petId);
		return ResponseEntity.noContent().build();
	}
}
