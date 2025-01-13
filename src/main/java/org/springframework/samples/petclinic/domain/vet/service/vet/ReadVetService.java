package org.springframework.samples.petclinic.domain.vet.service.vet;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.common.error.SpecialityErrorCode;
import org.springframework.samples.petclinic.common.exception.ApiException;
import org.springframework.samples.petclinic.domain.vet.dto.VetResponseDto;
import org.springframework.samples.petclinic.domain.vet.mapper.VetMapper;
import org.springframework.samples.petclinic.domain.vet.model.enums.VetStatus;
import org.springframework.samples.petclinic.domain.vet.repository.VetRepository;
import org.springframework.samples.petclinic.domain.vet.repository.VetSpecialtyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReadVetService {

	private final VetRepository vetRepository;
	private final VetMapper vetMapper;
	private final VetSpecialtyRepository vetSpecialtyRepository;
	private final VetUtilsService vetUtilsService;

	// 수의사 전체 조회
	public List<VetResponseDto> findAll() {
		return vetRepository.findAllByStatusOrderById(VetStatus.REGISTERED).stream()
			.map(vetMapper::toResponse)
			.collect(Collectors.toList());
	}

	// 특정 수의사 조회
	public VetResponseDto findById(int vetId) {
		return vetMapper.toResponse(vetUtilsService.getVetOrThrow(vetId));
	}

	// 전문 분야별 수의사 조회
	public List<VetResponseDto> findBySpecialtyId(int specialtyId) {
		var vetIds = vetSpecialtyRepository.findVetIdsBySpecialtyId_Id(specialtyId)
			.stream()
			.map(vs -> vs.getVet().getId())
			.toList();

		if (vetIds.isEmpty()) {
			throw new ApiException(SpecialityErrorCode.NO_SPECIALITY);
		}

		return vetIds.stream()
			.map(vetUtilsService::getVetOrThrow)
			.map(vetMapper::toResponse)
			.collect(Collectors.toList());
	}
}
