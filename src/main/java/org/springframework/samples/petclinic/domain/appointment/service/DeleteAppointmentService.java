package org.springframework.samples.petclinic.domain.appointment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.domain.appointment.model.Appointment;
import org.springframework.samples.petclinic.domain.appointment.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAppointmentService {

	private final AppointmentRepository appointmentRepository;
	private final AppointmentUtilsService appointmentUtilsService;

	public void deleteAppointment(Integer appointmentId) {
		Appointment appointment = appointmentUtilsService.getAppointmentOrThrow(appointmentId);
		appointmentRepository.delete(appointment);
	}
}
