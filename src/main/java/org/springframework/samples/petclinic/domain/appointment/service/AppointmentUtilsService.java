package org.springframework.samples.petclinic.domain.appointment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.common.error.AppointmentErrorCode;
import org.springframework.samples.petclinic.common.exception.ApiException;
import org.springframework.samples.petclinic.domain.appointment.dto.AppointmentRequestDto;
import org.springframework.samples.petclinic.domain.appointment.model.Appointment;
import org.springframework.samples.petclinic.domain.appointment.model.enums.ApptStatus;
import org.springframework.samples.petclinic.domain.appointment.repository.AppointmentRepository;
import org.springframework.samples.petclinic.domain.pet.model.Pet;
import org.springframework.samples.petclinic.domain.vet.model.Vet;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AppointmentUtilsService {

	private final AppointmentRepository appointmentRepository;

	public Appointment getAppointmentOrThrow(Integer appointmentId) {
		return appointmentRepository.findById(appointmentId)
			.orElseThrow(() -> new ApiException(AppointmentErrorCode.NO_APPOINTMENT));
	}

	// 요청값 검증
	public void validateRequestData(AppointmentRequestDto request, Pet pet, Vet vet) {
		validateAppointmentDate(request.getApptDateTime());
		validateAppointmentStatus(request.getAppStatus());
		validateSymptoms(request.getSymptoms());
		if (appointmentRepository.existsByPetAndVetAndApptDateTime(pet, vet, request.getApptDateTime())) {
			throw new ApiException(AppointmentErrorCode.CONFLICTING_APPOINTMENT);
		}
		validateNoOverlappingAppointments(request, vet);
	}

	// 날짜 검증
	public void validateAppointmentDate(LocalDateTime apptDateTime) {
		if (apptDateTime == null)
			throw new ApiException(AppointmentErrorCode.NULL_APPOINTMENT_DATE);

		if (apptDateTime.isBefore(LocalDateTime.now()))
			throw new ApiException(AppointmentErrorCode.INVALID_APPOINTMENT_DATE);

		if (!isWithinWorkingHours(apptDateTime))
			throw new ApiException(AppointmentErrorCode.OUTSIDE_WORKING_HOURS);
	}

	// 중복 검증
	public void validateNoOverlappingAppointments(AppointmentRequestDto request, Vet vet) {
		LocalDateTime startDateTime = request.getApptDateTime().minusMinutes(30);
		LocalDateTime endDateTime = request.getApptDateTime().plusMinutes(30);

		if (appointmentRepository.existsOverlappingAppointment(vet, startDateTime, endDateTime)) {
			throw new ApiException(AppointmentErrorCode.CONFLICTING_APPOINTMENT);
		}
	}

	public boolean isWithinWorkingHours(LocalDateTime dateTime) {
		int hour = dateTime.getHour();
		return hour >= 9 && hour < 18;
	}

	// 상태 검증
	public void validateAppointmentStatus(ApptStatus appStatus) {
		if (appStatus == null)
			throw new ApiException(AppointmentErrorCode.NULL_APPOINTMENT_STATUS);
	}

	// 증상 검증
	public void validateSymptoms(String symptoms) {
		if (symptoms == null || symptoms.isEmpty())
			throw new ApiException(AppointmentErrorCode.INVALID_SYMPTOMS);
	}
}
