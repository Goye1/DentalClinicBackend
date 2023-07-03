package com.DentalClinicX.DentalClinicManagement.model.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientDTO implements Serializable {
    private String name;
    private String surname;
    private Boolean isDischarged;
    private Integer idCard;
    private LocalDate dischargeDate;
    private AddressDTO address;

    public PatientDTO() {
    }

    public PatientDTO(String name, String surname, Boolean isDischarged, Integer idCard, AddressDTO address) {
        this.name = name;
        this.surname = surname;
        this.isDischarged = isDischarged;
        this.idCard = idCard;
        this.address = address;
    }

    public Integer getIdCard() {
        return idCard;
    }

    public Boolean getDischarged() {
        return isDischarged;
    }

    public void setDischarged(Boolean discharged) {
        isDischarged = discharged;
    }

    public void setIdCard(Integer idCard) {
        this.idCard = idCard;
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

    public LocalDate getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(LocalDate dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }
}
