package com.afp.mylawyer.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.afp.mylawyer.domain.Lawyer} entity.
 */
public class LawyerDTO implements Serializable {

    private Long id;

    private String lawyerId;

    private String lawyerFullName;

    @Pattern(regexp = "[0-9\\-\\+]+")
    private String lawyerPhoneNumber;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLawyerId() {
        return lawyerId;
    }

    public void setLawyerId(String lawyerId) {
        this.lawyerId = lawyerId;
    }

    public String getLawyerFullName() {
        return lawyerFullName;
    }

    public void setLawyerFullName(String lawyerFullName) {
        this.lawyerFullName = lawyerFullName;
    }

    public String getLawyerPhoneNumber() {
        return lawyerPhoneNumber;
    }

    public void setLawyerPhoneNumber(String lawyerPhoneNumber) {
        this.lawyerPhoneNumber = lawyerPhoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LawyerDTO lawyerDTO = (LawyerDTO) o;
        if (lawyerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lawyerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LawyerDTO{" +
            "id=" + getId() +
            ", lawyerId='" + getLawyerId() + "'" +
            ", lawyerFullName='" + getLawyerFullName() + "'" +
            ", lawyerPhoneNumber='" + getLawyerPhoneNumber() + "'" +
            "}";
    }
}
