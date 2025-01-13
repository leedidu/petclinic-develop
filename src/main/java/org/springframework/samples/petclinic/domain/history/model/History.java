package org.springframework.samples.petclinic.domain.history.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.samples.petclinic.domain.appointment.model.enums.ApptStatus;
import org.springframework.samples.petclinic.domain.pet.model.Pet;
import org.springframework.samples.petclinic.domain.vet.model.Vet;
import org.springframework.samples.petclinic.domain.visit.model.Visit;
import org.springframework.samples.petclinic.model.BaseEntity;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@Entity
@Table(name = "history")
public class History extends BaseEntity {
	private String symptoms;

	private String content;

	@ManyToOne
	@JoinColumn(name = "vet_id")
	private Vet vet;

	@OneToOne
	@JoinColumn(name = "visit_id")
	private Visit visit;

	public History updateHistory(String symptoms, String content, Vet vet, Visit visit) {
		return History.builder()
			.symptoms(symptoms != null ? symptoms : this.symptoms)
			.content(content != null ? content : this.content)
			.vet(vet != null ? vet : this.vet)
			.visit(visit != null ? visit : this.visit)
			.build();
	}
}
