package com.DentalClinicX.DentalClinicManagement.persistance.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table
public class Dentist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private Integer licenseNumber;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "dentist")
    @JsonIgnore
    private Set<Appointment> appointments = new HashSet<>();
    public Dentist() {
    }

    public Dentist(Long id, String name, String surname, Integer licenseNumber, Set<Appointment> appointments) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.licenseNumber = licenseNumber;
        this.appointments = appointments;
    }

    public Dentist(String name, String surname, Integer licenseNumber) {
        this.name = name;
        this.surname = surname;
        this.licenseNumber = licenseNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(Integer licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }
}
