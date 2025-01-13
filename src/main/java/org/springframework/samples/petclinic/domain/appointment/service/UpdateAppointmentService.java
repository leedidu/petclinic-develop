package org.springframework.samples.petclinic.domain.appointment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.common.error.AppointmentErrorCode;
import org.springframework.samples.petclinic.common.error.PetErrorCode;
import org.springframework.samples.petclinic.domain.appointment.dto.AppointmentRequestDto;
import org.springframework.samples.petclinic.domain.appointment.dto.AppointmentResponseDto;
import org.springframework.samples.petclinic.domain.appointment.mapper.AppointmentMapper;
import org.springframework.samples.petclinic.domain.appointment.model.Appointment;
import org.springframework.samples.petclinic.domain.appointment.repository.AppointmentRepository;
import org.springframework.samples.petclinic.domain.pet.model.Pet;
import org.springframework.samples.petclinic.domain.pet.service.PetUtilsService;
import org.springframework.samples.petclinic.domain.vet.model.Vet;
import org.springframework.samples.petclinic.domain.vet.service.vet.VetUtilsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateAppointmentService {

	private final AppointmentRepository appointmentRepository;
	private final PetUtilsService petUtilsService;
	private final VetUtilsService vetUtilsService;
	private final AppointmentUtilsService appointmentUtilService;
	private final AppointmentMapper appointmentMapper;
	private final AppointmentUtilsService appointmentUtilsService;

	public AppointmentResponseDto updateAppointment(Integer appointmentId, AppointmentRequestDto request) {
		Appointment appointment = appointmentUtilsService.getAppointmentOrThrow(appointmentId);
		Pet pet = petUtilsService.getPetOrThrow(request.getPetId());
		Vet vet = vetUtilsService.getVetOrThrow(request.getVetId());
		appointmentUtilService.validateRequestData(request, pet, vet);

		updateAppointmentDetails(request, appointment, pet, vet);

		Appointment updatedAppointment = appointmentRepository.save(appointment);

		return appointmentMapper.toDto(updatedAppointment);
	}

	private static void updateAppointmentDetails(AppointmentRequestDto request, Appointment appointment, Pet pet, Vet vet) {
		appointment.updateAppointment(
			request.getApptDateTime(),
			request.getAppStatus(),
			request.getSymptoms(),
			pet,
			vet
		);
	}
}
