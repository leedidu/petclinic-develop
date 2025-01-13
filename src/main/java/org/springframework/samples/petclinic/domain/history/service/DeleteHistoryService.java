package org.springframework.samples.petclinic.domain.history.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.domain.history.repository.HistoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteHistoryService {

	private final HistoryRepository historyRepository;
	private final HistoryUtilsService historyUtilsService;

	/**
	 * 진료 내역 삭제
	 *
	 * @param historyId 삭제할 진료 내역 ID
	 */
	public void deleteHistory(int historyId) {
		historyUtilsService.getHistoryOrThrow(historyId);
		historyRepository.deleteById(historyId);
	}
}
