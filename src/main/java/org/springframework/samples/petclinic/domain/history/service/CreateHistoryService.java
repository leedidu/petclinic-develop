package org.springframework.samples.petclinic.domain.history.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.domain.history.dto.HistoryRequestDto;
import org.springframework.samples.petclinic.domain.history.mapper.HistoryMapper;
import org.springframework.samples.petclinic.domain.history.model.History;
import org.springframework.samples.petclinic.domain.history.repository.HistoryRepository;
import org.springframework.samples.petclinic.domain.vet.model.Vet;
import org.springframework.samples.petclinic.domain.vet.service.vet.VetUtilsService;
import org.springframework.samples.petclinic.domain.visit.model.Visit;
import org.springframework.samples.petclinic.domain.visit.service.VisitUtilsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateHistoryService {

	private final HistoryMapper historyMapper;
	private final VetUtilsService vetUtilsService;
	private final VisitUtilsService visitUtilsService;
	private final HistoryRepository historyRepository;

	/**
	 * 진료 내역 생성
	 *
	 * @param requestDto 진료 요청 데이터
	 * @return HistoryResponseDto 저장된 진료 정보 반환
	 */
	public History addHistory(HistoryRequestDto requestDto) {
		Vet vet = vetUtilsService.getVetOrThrow(requestDto.getVetId());
		Visit visit = visitUtilsService.getVisitOrThrow(requestDto.getVisitId());

		History history = historyMapper.toEntity(requestDto, vet, visit);
		return historyRepository.save(history);
	}
}
