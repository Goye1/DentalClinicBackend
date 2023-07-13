package com.DentalClinicX.DentalClinicManagement.persistance.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private Integer idCard;
    @Column(nullable = true)
    private LocalDate dischargeDate;

    @Column
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address" , referencedColumnName = "id")
    private Address address;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "patient")
    @JsonIgnore
    private Set<Appointment> appointments = new HashSet<>();

    public Patient() {
    }

    public Patient(String name, String surname, Integer idCard, LocalDate dischargeDate) {
        this.name = name;
        this.surname = surname;
        this.idCard = idCard;
        this.dischargeDate = dischargeDate;
    }

    public Patient(String name, String surname, Integer idCard,Address address) {
        this.name = name;
        this.surname = surname;
        this.idCard = idCard;
        this.address = address;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public int getIdCard() {
        return idCard;
    }

    public Address getAddress() {
        return address;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setIdCard(Integer idCard) {
        this.idCard = idCard;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDate getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(LocalDate dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
