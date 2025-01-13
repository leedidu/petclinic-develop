package org.springframework.samples.petclinic.domain.history.service;

import lombok.RequiredArgsConstructor;
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
public class CreateHistoryService {

	private final HistoryMapper historyMapper;
	private final VetUtilsService vetUtilsService;
	private final VisitRepository visitRepository;
	private final HistoryRepository historyRepository;

	/**
	 * 진료 내역 생성
	 *
	 * @param requestDto 진료 요청 데이터
	 * @return HistoryResponseDto 저장된 진료 정보 반환
	 */
	public HistoryResponseDto addHistory(HistoryRequestDto requestDto) {
		Vet vet = vetUtilsService.getVetOrThrow(requestDto.getVetId());
		Visit visit = visitRepository.findById(requestDto.getVisitId())
			.orElseThrow(() -> new ApiException(VisitErrorCode.NO_VISIT));

		History history = historyMapper.toEntity(requestDto, vet, visit);
		History savedHistory = historyRepository.save(history);

		return historyMapper.toDto(savedHistory);
	}


}
