package org.springframework.samples.petclinic.domain.vet.service.vet;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.common.error.VetErrorCode;
import org.springframework.samples.petclinic.common.exception.ApiException;
import org.springframework.samples.petclinic.domain.vet.dto.VetRequestDto;
import org.springframework.samples.petclinic.domain.vet.mapper.VetSpecialtyMapper;
import org.springframework.samples.petclinic.domain.vet.model.Specialty;
import org.springframework.samples.petclinic.domain.vet.model.Vet;
import org.springframework.samples.petclinic.domain.vet.model.VetSpeciality;
import org.springframework.samples.petclinic.domain.vet.model.enums.VetStatus;
import org.springframework.samples.petclinic.domain.vet.repository.VetRepository;
import org.springframework.samples.petclinic.domain.vet.repository.VetSpecialtyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VetUtilsService {

	private final VetRepository vetRepository;
	private final VetSpecialtyRepository vetSpecialtyRepository;
	private final VetSpecialtyMapper vetSpecialtyMapper;

	// 수의사 등록 상태 확인
	public Vet getVetOrThrow(int id) {
		return vetRepository.findByIdAndStatus(id, VetStatus.REGISTERED)
			.orElseThrow(() -> new ApiException(VetErrorCode.NO_VET));
	}

	// 전문분야-수의사 연결 테이블 저장
	public void saveSpecialities(Vet vet, List<Specialty> specialties) {
		List<VetSpeciality> vetSpecialties = vetSpecialtyMapper.toEntityList(vet, specialties);
		vetSpecialtyRepository.saveAll(vetSpecialties);
	}

	// 요청값 검증
	public void validateVetRequestDto(VetRequestDto vetRequestDto) {
		if (vetRequestDto.getName() == null || vetRequestDto.getName().isBlank()) {
			throw new ApiException(VetErrorCode.NULL_NAME);
		}
		if (vetRequestDto.getSpecialties() == null || vetRequestDto.getSpecialties().isEmpty()) {
			throw new ApiException(VetErrorCode.NULL_SPECIALITY);
		}
	}
}
