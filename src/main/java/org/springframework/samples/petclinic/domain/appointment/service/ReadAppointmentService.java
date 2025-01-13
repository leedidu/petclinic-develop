package org.springframework.samples.petclinic.domain.appointment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.domain.appointment.mapper.AppointmentMapper;
import org.springframework.samples.petclinic.domain.appointment.model.Appointment;
import org.springframework.samples.petclinic.domain.appointment.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReadAppointmentService {

	private final AppointmentRepository appointmentRepository;
	private final AppointmentMapper appointmentMapper;
	private final AppointmentUtilsService appointmentUtilsService;

	public List<Appointment> findAllAppointments() {
		return appointmentRepository.findAll();
	}

	public Appointment findAppointment(Integer appointmentId) {
		return appointmentUtilsService.getAppointmentOrThrow(appointmentId);
	}
}
