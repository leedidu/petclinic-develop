package org.springframework.samples.petclinic.domain.history.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.domain.history.mapper.HistoryMapper;
import org.springframework.samples.petclinic.domain.history.model.History;
import org.springframework.samples.petclinic.domain.history.repository.HistoryRepository;
import org.springframework.samples.petclinic.domain.pet.model.Pet;
import org.springframework.samples.petclinic.domain.pet.service.PetUtilsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReadHistoryService {

	private final HistoryRepository historyRepository;
	private final HistoryMapper historyMapper;
	private final PetUtilsService petUtilsService;

	/**
	 * 특정 반려동물의 진료 내역 전체 조회
	 *
	 * @param petId 반려동물 ID
	 * @return 진료 내역 목록 응답 DTO
	 */
	public List<History> getHistoriesByPetId(int petId) {
		Pet pet = petUtilsService.getPetOrThrow(petId);

		return historyRepository.findAllByVisitId_PetId(pet.getId());
	}
}
