package org.springframework.samples.petclinic.domain.owner.mapper;

import org.springframework.samples.petclinic.domain.owner.dto.OwnerResponseDto;
import org.springframework.samples.petclinic.domain.owner.dto.RegisterRequestDto;
import org.springframework.samples.petclinic.domain.owner.model.Owner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OwnerMapper {

	public OwnerResponseDto toDto(Owner owner) {
		return OwnerResponseDto.builder()
			.id(owner.getId())
			.name(owner.getName())
			.address(owner.getAddress())
			.city(owner.getCity())
			.telephone(owner.getTelephone())
			.build();
	}

	public Owner toEntity(OwnerResponseDto ownerDto) {
		return Owner.builder()
			.name(ownerDto.getName())
			.address(ownerDto.getAddress())
			.city(ownerDto.getCity())
			.telephone(ownerDto.getTelephone())
			.build();
	}

	public Owner toRegisterEntity(RegisterRequestDto registerRequestDto) {
		return Owner.builder()
			.userId(registerRequestDto.getUserId())
			.password(registerRequestDto.getPassword())
			.name(registerRequestDto.getName())
			.address(registerRequestDto.getAddress())
			.city(registerRequestDto.getCity())
			.telephone(registerRequestDto.getTelephone())
			.build();
	}

	public List<OwnerResponseDto> toListDto(List<Owner> owners) {
		return owners.stream()
			.map(this::toDto)
			.toList();
	}
}
