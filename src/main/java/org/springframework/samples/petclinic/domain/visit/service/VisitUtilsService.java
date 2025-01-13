package org.springframework.samples.petclinic.domain.visit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.common.error.VisitErrorCode;
import org.springframework.samples.petclinic.common.exception.ApiException;
import org.springframework.samples.petclinic.domain.visit.model.Visit;
import org.springframework.samples.petclinic.domain.visit.repository.VisitRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisitUtilsService {

	private final VisitRepository visitRepository;

	public Visit getVisitOrThrow(Integer visitId){
		return visitRepository.findById(visitId)
			.orElseThrow(() -> new ApiException(VisitErrorCode.NO_VISIT));
	}
}
