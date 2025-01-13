package org.springframework.samples.petclinic.domain.history.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.domain.history.dto.HistoryRequestDto;
import org.springframework.samples.petclinic.domain.history.dto.HistoryResponseDto;
import org.springframework.samples.petclinic.domain.history.mapper.HistoryMapper;
import org.springframework.samples.petclinic.domain.history.model.History;
import org.springframework.samples.petclinic.domain.history.service.CreateHistoryService;
import org.springframework.samples.petclinic.domain.history.service.DeleteHistoryService;
import org.springframework.samples.petclinic.domain.history.service.ReadHistoryService;
import org.springframework.samples.petclinic.domain.history.service.UpdateHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class HistoryController {

	private final CreateHistoryService createHistoryService;
	private final ReadHistoryService readHistoryService;
	private final UpdateHistoryService updateHistoryService;
	private final DeleteHistoryService deleteHistoryService;
	private final HistoryMapper historyMapper;

	/**
	 * 새로운 진료 내역 추가
	 *
	 * @param request 요청 본문으로 전달된 진료 내역
	 * @return 추가된 진료 내역
	 */
	@PostMapping
	public ResponseEntity<HistoryResponseDto> addHistory(@Valid @RequestBody HistoryRequestDto request) {
		History history = createHistoryService.addHistory(request);
		HistoryResponseDto response = historyMapper.toDto(history);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	/**
	 * 특정 반려동물의 진료 내역 전체 조회
	 *
	 * @param petId 반려동물 ID
	 * @return 방문 내역 목록
	 */
	@GetMapping("/{petId}")
	public ResponseEntity<List<HistoryResponseDto>> getHistoriesByPetId(@PathVariable("petId") int petId) {
		List<History> histories = readHistoryService.getHistoriesByPetId(petId);
		List<HistoryResponseDto> response = historyMapper.toListDto(histories);
		return ResponseEntity.ok(response);
	}

	/**
	 * 진료 내역 수정
	 *
	 * @param request 수정할 진료내역 요청 DTO
	 * @return 수정된 진료 내역
	 */
	@PutMapping("/{historyId}")
	public ResponseEntity<HistoryResponseDto> updateHistory(@PathVariable("historyId") int historyId,@Valid @RequestBody HistoryRequestDto request) {
		History history = updateHistoryService.updateHistory(historyId,request);;
		HistoryResponseDto response = historyMapper.toDto(history);
		return ResponseEntity.ok(response);
	}

	/**
	 * 진료 내역 삭제
	 *
	 * @param historyId 삭제할 진료 내역 ID
	 * @return 삭제 결과 메시지
	 */
	@DeleteMapping("/{historyId}")
	public ResponseEntity<String> deleteHistory(@PathVariable("historyId") int historyId) {
		deleteHistoryService.deleteHistory(historyId);
		return ResponseEntity.ok("History deleted successfully.");
	}

}
