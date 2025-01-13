package org.springframework.samples.petclinic.domain.appointment.mapper;

import org.springframework.samples.petclinic.domain.appointment.dto.AppointmentRequestDto;
import org.springframework.samples.petclinic.domain.appointment.dto.AppointmentResponseDto;
import org.springframework.samples.petclinic.domain.appointment.model.Appointment;
import org.springframework.samples.petclinic.domain.pet.model.Pet;
import org.springframework.samples.petclinic.domain.vet.model.Vet;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppointmentMapper {

	public AppointmentResponseDto toDto(Appointment appointment) {
		return AppointmentResponseDto.builder()
			.id(appointment.getId())
			.apptDateTime(appointment.getApptDateTime())
			.status(appointment.getStatus())
			.symptoms(appointment.getSymptoms())
			.vetId(appointment.getVet().getId())
			.petId(appointment.getPet().getId())
			.build();
	}

	public List<AppointmentResponseDto> toListDto(List<Appointment> appointments) {
		return appointments.stream()
			.map(this::toDto)
			.toList();
	}

	public Appointment toEntity(AppointmentRequestDto request, Pet pet, Vet vet) {
		return Appointment.builder()
			.pet(pet)
			.vet(vet)
			.apptDateTime(request.getApptDateTime())
			.status(request.getStatus())
			.symptoms(request.getSymptoms())
			.build();
	}
}
