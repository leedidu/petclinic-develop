package org.springframework.samples.petclinic.domain.history.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.common.error.PetErrorCode;
import org.springframework.samples.petclinic.common.exception.ApiException;
import org.springframework.samples.petclinic.domain.history.dto.HistoryResponseDto;
import org.springframework.samples.petclinic.domain.history.mapper.HistoryMapper;
import org.springframework.samples.petclinic.domain.history.repository.HistoryRepository;
import org.springframework.samples.petclinic.domain.pet.enums.PetStatus;
import org.springframework.samples.petclinic.domain.pet.model.Pet;
import org.springframework.samples.petclinic.domain.pet.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReadHistoryService {

	private final PetRepository petRepository;
	private final HistoryRepository historyRepository;
	private final HistoryMapper historyMapper;

	/**
	 * 특정 반려동물의 진료 내역 전체 조회
	 *
	 * @param petId 반려동물 ID
	 * @return 진료 내역 목록 응답 DTO
	 */
	public List<HistoryResponseDto> getHistoriesByPetId(int petId) {

		Pet pet = petRepository.findByIdAndStatus(petId, PetStatus.REGISTERED)
			.orElseThrow(() -> new ApiException(PetErrorCode.NO_PET));

		return historyRepository.findAllByVisitId_PetId(pet.getId())
			.stream()
			.map(historyMapper::toDto)
			.collect(Collectors.toList());
	}

}
