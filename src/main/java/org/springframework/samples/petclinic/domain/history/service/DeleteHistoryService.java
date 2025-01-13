package org.springframework.samples.petclinic.domain.history.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.common.error.HistoryErrorCode;
import org.springframework.samples.petclinic.common.exception.ApiException;
import org.springframework.samples.petclinic.domain.history.repository.HistoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteHistoryService {

	private final HistoryRepository historyRepository;

	/**
	 * 진료 내역 삭제
	 *
	 * @param historyId 삭제할 진료 내역 ID
	 */
	public void deleteHistory(int historyId) {

		//진료내역 존재 여부 확인
		if (!historyRepository.existsById(historyId)) {
			throw new ApiException(HistoryErrorCode.NO_HISTORY);
		}

		//진료내역 삭제
		historyRepository.deleteById(historyId);
	}
}
