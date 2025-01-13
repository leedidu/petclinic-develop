package org.springframework.samples.petclinic.domain.appointment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.common.error.AppointmentErrorCode;
import org.springframework.samples.petclinic.common.exception.ApiException;
import org.springframework.samples.petclinic.domain.appointment.dto.AppointmentResponseDto;
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

	public List<AppointmentResponseDto> findAllAppointments() {
		return appointmentRepository.findAll().stream()
			.map(appointmentMapper::toDto)
			.collect(Collectors.toList());
	}

	public AppointmentResponseDto findAppointment(Integer appointmentId) {
		Appointment appointment = appointmentUtilsService.getAppointmentOrThrow(appointmentId);
		return appointmentMapper.toDto(appointment);
	}
}
