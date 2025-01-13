package org.springframework.samples.petclinic.domain.vet.service.vet;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.domain.vet.dto.VetRequestDto;
import org.springframework.samples.petclinic.domain.vet.dto.VetResponseDto;
import org.springframework.samples.petclinic.domain.vet.mapper.VetMapper;
import org.springframework.samples.petclinic.domain.vet.model.Specialty;
import org.springframework.samples.petclinic.domain.vet.model.Vet;
import org.springframework.samples.petclinic.domain.vet.repository.VetRepository;
import org.springframework.samples.petclinic.domain.vet.repository.VetSpecialtyRepository;
import org.springframework.samples.petclinic.domain.vet.service.specialty.SpecialtyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateVetService {

	private final VetRepository vetRepository;
	private final VetUtilsService vetUtilsService;
	private final SpecialtyService specialtyService;
	private final VetSpecialtyRepository vetSpecialtyRepository;
	private final VetMapper vetMapper;

	// 수의사 수정
	@Transactional
	public VetResponseDto update(int id, VetRequestDto vetRequestDto) {
		Vet vet = vetUtilsService.getVetOrThrow(id);

		// 이름 수정
		Optional.ofNullable(vetRequestDto.getName()).ifPresent(vet::setName);

		// 분야 수정
		if (vetRequestDto.getSpecialties() != null && !vetRequestDto.getSpecialties().isEmpty()) {
			List<Specialty> validSpecialties = specialtyService.findByIds(vetRequestDto.getSpecialties());

			vetSpecialtyRepository.deleteAllByVetId_Id(vet.getId());
			vetUtilsService.saveSpecialities(vet, validSpecialties);
		}

		vetRepository.save(vet);
		return vetMapper.toResponse(vet);
	}
}
