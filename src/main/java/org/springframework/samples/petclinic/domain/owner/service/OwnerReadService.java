package org.springframework.samples.petclinic.domain.owner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.domain.owner.model.Owner;
import org.springframework.samples.petclinic.domain.owner.repository.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerReadService {

	private final OwnerRepository ownerRepository;
	private final OwnerUtilsService ownerUtilsService;

	// 모든 회원 데이터를 조회
	public List<Owner> findAll() {
		return ownerRepository.findAll();
	}

	// 특정 회원 조회
	public Owner findById(Integer id) {
		return ownerUtilsService.findOwnerByIdOrThrow(id);
	}
}
