package org.springframework.samples.petclinic.domain.owner.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {
	private String userId;
	private String password;
	private String name;
	private String address;
	private String city;
	private String telephone;
}
