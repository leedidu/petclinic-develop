package org.springframework.samples.petclinic.domain.appointment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.samples.petclinic.domain.appointment.model.enums.ApptStatus;
import org.springframework.samples.petclinic.domain.pet.model.Pet;
import org.springframework.samples.petclinic.domain.vet.model.Vet;
import org.springframework.samples.petclinic.model.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
public class Appointment extends BaseEntity {

	@Column(name = "appt_date", nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime apptDateTime;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private ApptStatus status;

	@Column(name = "symptoms")
	private String symptoms;

	@ManyToOne
	@JoinColumn(name = "pet_id", nullable = false)
	private Pet pet;

	@ManyToOne
	@JoinColumn(name = "vet_id", nullable = false)
	private Vet vet;

	public void updateAppointment(LocalDateTime apptDateTime, ApptStatus status, String symptoms, Pet pet, Vet vet) {
		this.apptDateTime = apptDateTime != null ? apptDateTime : this.apptDateTime;
		this.status = status != null ? status : this.status;
		this.symptoms = symptoms != null ? symptoms : this.symptoms;
		this.pet = pet != null ? pet : this.pet;
		this.vet = vet != null ? vet : this.vet;
	}
}

