package org.springframework.samples.petclinic.domain.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.domain.appointment.model.Appointment;
import org.springframework.samples.petclinic.domain.pet.model.Pet;
import org.springframework.samples.petclinic.domain.vet.model.Vet;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
	boolean existsByPetAndVetAndApptDateTime(Pet pet, Vet vet, LocalDateTime apptDateTime);

	@Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Appointment a WHERE a.vet = :vet AND (:startDateTime < a.apptDateTime AND :endDateTime > a.apptDateTime OR :startDateTime = a.apptDateTime)")
	boolean existsOverlappingAppointment(@Param("vet") Vet vet, @Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime);
}
