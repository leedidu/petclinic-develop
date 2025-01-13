package org.springframework.samples.petclinic.domain.owner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.samples.petclinic.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@Entity
@Table(name = "owner")
public class Owner extends BaseEntity {
	@Column(length = 100, nullable = false)
	private String userId;

	@Column(length = 100, nullable = false)
	private String password;

	@Column(length = 15, nullable = false)
	private String name;

	@Column(nullable = false)
	private String address;

	@Column(length = 15, nullable = false)
	private String city;

	@Column(length = 15, nullable = false)
	private String telephone;

	public void updatePassword(String updatePassword) {
		this.password = updatePassword;
	}

	public Owner updateOwner(String name, String address, String city, String telephone) {
		return Owner.builder()
			.name(name != null ? name : this.name)
			.address(address != null ? address : this.address)
			.city(city != null ? city : this.city)
			.telephone(telephone != null ? telephone : this.telephone)
			.build();
	}
}
