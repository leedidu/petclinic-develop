package org.springframework.samples.petclinic.domain.vet.service.vet;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.domain.vet.model.Vet;
import org.springframework.samples.petclinic.domain.vet.model.enums.VetStatus;
import org.springframework.samples.petclinic.domain.vet.repository.VetSpecialtyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteVetService {

	private final VetUtilsService vetUtilsService;
	private final VetSpecialtyRepository vetSpecialtyRepository;

	// 수의사 삭제
	@Transactional
	public void delete(int vetId) {
		Vet vet = vetUtilsService.getVetOrThrow(vetId);
		vetSpecialtyRepository.deleteAllByVetId_Id(vetId);
		vet.setStatus(VetStatus.DELETED);
	}
}
