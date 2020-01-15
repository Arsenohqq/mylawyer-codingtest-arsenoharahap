package com.afp.mylawyer.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Lawyer.
 */
@Entity
@Table(name = "lawyer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Lawyer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lawyer_id")
    private String lawyerId;

    @Column(name = "lawyer_full_name")
    private String lawyerFullName;

    @Pattern(regexp = "[0-9\\-\\+]+")
    @Column(name = "lawyer_phone_number")
    private String lawyerPhoneNumber;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLawyerId() {
        return lawyerId;
    }

    public Lawyer lawyerId(String lawyerId) {
        this.lawyerId = lawyerId;
        return this;
    }

    public void setLawyerId(String lawyerId) {
        this.lawyerId = lawyerId;
    }

    public String getLawyerFullName() {
        return lawyerFullName;
    }

    public Lawyer lawyerFullName(String lawyerFullName) {
        this.lawyerFullName = lawyerFullName;
        return this;
    }

    public void setLawyerFullName(String lawyerFullName) {
        this.lawyerFullName = lawyerFullName;
    }

    public String getLawyerPhoneNumber() {
        return lawyerPhoneNumber;
    }

    public Lawyer lawyerPhoneNumber(String lawyerPhoneNumber) {
        this.lawyerPhoneNumber = lawyerPhoneNumber;
        return this;
    }

    public void setLawyerPhoneNumber(String lawyerPhoneNumber) {
        this.lawyerPhoneNumber = lawyerPhoneNumber;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lawyer)) {
            return false;
        }
        return id != null && id.equals(((Lawyer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Lawyer{" +
            "id=" + getId() +
            ", lawyerId='" + getLawyerId() + "'" +
            ", lawyerFullName='" + getLawyerFullName() + "'" +
            ", lawyerPhoneNumber='" + getLawyerPhoneNumber() + "'" +
            "}";
    }
}
