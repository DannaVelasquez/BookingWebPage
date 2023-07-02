package com.digitalhouse.digitalexpirience.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name="code_confirm")
public class CodeConfirm implements Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Column(name = "code_confirm")
	private  String code;
	
	@NotNull
	@Column(name = "email")
	private  String email;

	@Column(name = "status_validation")
	private Boolean status;

	@NotNull
	@Column(name = "register_date")
    @Temporal(TemporalType.TIMESTAMP)
    private  Calendar registerDate;
	


}
