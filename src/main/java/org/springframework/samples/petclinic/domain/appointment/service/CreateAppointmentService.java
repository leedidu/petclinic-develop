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
public class CreateAppointmentService {

	private final AppointmentRepository appointmentRepository;
	private final VetUtilsService vetUtilsService;
	private final PetUtilsService petUtilsService;
	private final AppointmentMapper appointmentMapper;
	private final AppointmentUtilsService appointmentUtilsService;

	public Appointment createAppointment(AppointmentRequestDto request) {
		Vet vet = vetUtilsService.getVetOrThrow(request.getVetId());
		Pet pet = petUtilsService.getPetOrThrow(request.getPetId());

		appointmentUtilsService.validateRequestData(request, pet, vet);

		Appointment appointment = appointmentMapper.toEntity(request, pet, vet);
		return appointmentRepository.save(appointment);
	}
}
