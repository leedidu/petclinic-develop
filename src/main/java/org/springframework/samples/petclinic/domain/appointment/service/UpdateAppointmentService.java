package org.springframework.samples.petclinic.domain.appointment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.domain.appointment.dto.AppointmentRequestDto;
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

	public Appointment updateAppointment(Integer appointmentId, AppointmentRequestDto request) {
		Appointment appointment = appointmentUtilsService.getAppointmentOrThrow(appointmentId);
		Pet pet = petUtilsService.getPetOrThrow(request.getPetId());
		Vet vet = vetUtilsService.getVetOrThrow(request.getVetId());
		appointmentUtilService.validateRequestData(request, pet, vet);

		Appointment updatedAppointment = appointment.updateAppointment(request.getApptDateTime(), request.getStatus(), request.getSymptoms(), pet, vet);

		return appointmentRepository.save(updatedAppointment);
	}
}
