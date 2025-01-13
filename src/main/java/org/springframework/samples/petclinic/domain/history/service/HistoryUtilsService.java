package org.springframework.samples.petclinic.domain.history.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.common.error.HistoryErrorCode;
import org.springframework.samples.petclinic.common.exception.ApiException;
import org.springframework.samples.petclinic.domain.history.model.History;
import org.springframework.samples.petclinic.domain.history.repository.HistoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryUtilsService {

	private final HistoryRepository historyRepository;

	public History getHistoryOrThrow(Integer historyId){
		return historyRepository.findById(historyId)
			.orElseThrow(() -> new ApiException(HistoryErrorCode.NO_HISTORY));
	}
}
