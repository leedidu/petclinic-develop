package org.springframework.samples.petclinic.domain.history.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.common.error.HistoryErrorCode;
import org.springframework.samples.petclinic.common.error.VisitErrorCode;
import org.springframework.samples.petclinic.common.exception.ApiException;
import org.springframework.samples.petclinic.domain.history.dto.HistoryRequestDto;
import org.springframework.samples.petclinic.domain.history.dto.HistoryResponseDto;
import org.springframework.samples.petclinic.domain.history.mapper.HistoryMapper;
import org.springframework.samples.petclinic.domain.history.model.History;
import org.springframework.samples.petclinic.domain.history.repository.HistoryRepository;
import org.springframework.samples.petclinic.domain.vet.model.Vet;
import org.springframework.samples.petclinic.domain.vet.service.vet.VetUtilsService;
import org.springframework.samples.petclinic.domain.visit.model.Visit;
import org.springframework.samples.petclinic.domain.visit.repository.VisitRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateHistoryService {

	private final HistoryRepository historyRepository;
	private final VisitRepository visitRepository;
	private final HistoryMapper historyMapper;
	private final VetUtilsService vetUtilsService;

	/**
	 * 진료 내역 수정
	 *
	 * @param historyId 수정할 진료내역 ID
	 * @param request 수정할 내역 요청 데이터
	 * @return HistoryResponseDto 수정된 진료 정보 반환
	 */
	public HistoryResponseDto updateHistory(int historyId, HistoryRequestDto request) {
		// 존재 여부 확인
		History history = historyRepository.findById(historyId)
			.orElseThrow(() -> new ApiException(HistoryErrorCode.NO_HISTORY));

		Vet vet = vetUtilsService.getVetOrThrow(request.getVetId());

		Visit visit = visitRepository.findById(request.getVisitId())
			.orElseThrow(() -> new ApiException(VisitErrorCode.NO_VISIT));

		// 업데이트 내용 반영
		history.setSymptoms(request.getSymptoms());
		history.setContent(request.getContent());
		history.setVet(vet);
		history.setVisit(visit);

		//업데이트 내용 저장
		History updateEntity = historyRepository.save(history);

		//응답 DTO 생성
		HistoryResponseDto historyResponseDto = historyMapper.toDto(updateEntity);

		return historyResponseDto;
	}
}
