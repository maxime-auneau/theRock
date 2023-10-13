package com.maximeDev.theRock.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.maximeDev.theRock.Entity.Enums.Role;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 255, nullable = false)
    private String firstname;

    @Column(length = 255, nullable = false)
    private String lastname;

    @Column(length = 255, unique = true, nullable = false, name = "email")
    private String email;

	@Column(length = 255, nullable = false, name = "password")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

	@Column(length = 255, nullable = false)
	@Enumerated(EnumType.STRING)
    private Role role;

    public User() {
    }

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
