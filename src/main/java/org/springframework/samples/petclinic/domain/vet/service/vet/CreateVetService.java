package org.springframework.samples.petclinic.domain.vet.service.vet;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.domain.vet.dto.VetRequestDto;
import org.springframework.samples.petclinic.domain.vet.dto.VetResponseDto;
import org.springframework.samples.petclinic.domain.vet.mapper.VetMapper;
import org.springframework.samples.petclinic.domain.vet.model.Specialty;
import org.springframework.samples.petclinic.domain.vet.model.Vet;
import org.springframework.samples.petclinic.domain.vet.repository.VetRepository;
import org.springframework.samples.petclinic.domain.vet.service.specialty.SpecialtyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateVetService {

	private final VetRepository vetRepository;
	private final VetMapper vetMapper;
	private final SpecialtyService specialtyService;
	private final VetUtilsService vetUtilsService;

	// 수의사 등록
	@Transactional
	public VetResponseDto register(VetRequestDto vetRequestDto) {
		vetUtilsService.validateVetRequestDto(vetRequestDto);

		List<Specialty> validSpecialties = specialtyService.findByIds(vetRequestDto.getSpecialties());

		Vet vet = vetMapper.toEntity(vetRequestDto);
		Vet savedVet = vetRepository.save(vet);

		vetUtilsService.saveSpecialities(savedVet, validSpecialties);
		return vetMapper.toResponse(savedVet);
	}
}
