package org.springframework.samples.petclinic.domain.history.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.domain.history.dto.HistoryRequestDto;
import org.springframework.samples.petclinic.domain.history.model.History;
import org.springframework.samples.petclinic.domain.history.repository.HistoryRepository;
import org.springframework.samples.petclinic.domain.vet.model.Vet;
import org.springframework.samples.petclinic.domain.vet.service.vet.VetUtilsService;
import org.springframework.samples.petclinic.domain.visit.model.Visit;
import org.springframework.samples.petclinic.domain.visit.service.VisitUtilsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateHistoryService {

	private final HistoryRepository historyRepository;
	private final VetUtilsService vetUtilsService;
	private final HistoryUtilsService historyUtilsService;
	private final VisitUtilsService visitUtilsService;

	/**
	 * 진료 내역 수정
	 *
	 * @param historyId 수정할 진료내역 ID
	 * @param request 수정할 내역 요청 데이터
	 * @return HistoryResponseDto 수정된 진료 정보 반환
	 */
	public History updateHistory(int historyId, HistoryRequestDto request) {
		History history = historyUtilsService.getHistoryOrThrow(historyId);
		Vet vet = vetUtilsService.getVetOrThrow(request.getVetId());
		Visit visit = visitUtilsService.getVisitOrThrow(request.getVisitId());

		History updatedHistory = history.updateHistory(request.getSymptoms(), request.getContent(), vet, visit);

		return historyRepository.save(updatedHistory);
	}
}
