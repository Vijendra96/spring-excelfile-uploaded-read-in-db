package com.example.jpa3;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Employee {
	@Id
	private String empId;
	private String firstName;
	private String lastName;
	private Integer age;
	private String email;
	private Integer salary;
}
