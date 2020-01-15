package com.afp.mylawyer.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Booking.
 */
@Entity
@Table(name = "booking")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_id")
    private String bookingId;

    @Column(name = "booking_name")
    private String bookingName;

    @Column(name = "payment_approved")
    private Boolean paymentApproved;

    @ManyToOne
    @JsonIgnoreProperties("bookings")
    private Lawyer lawyerId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public Booking bookingId(String bookingId) {
        this.bookingId = bookingId;
        return this;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingName() {
        return bookingName;
    }

    public Booking bookingName(String bookingName) {
        this.bookingName = bookingName;
        return this;
    }

    public void setBookingName(String bookingName) {
        this.bookingName = bookingName;
    }

    public Boolean isPaymentApproved() {
        return paymentApproved;
    }

    public Booking paymentApproved(Boolean paymentApproved) {
        this.paymentApproved = paymentApproved;
        return this;
    }

    public void setPaymentApproved(Boolean paymentApproved) {
        this.paymentApproved = paymentApproved;
    }

    public Lawyer getLawyerId() {
        return lawyerId;
    }

    public Booking lawyerId(Lawyer lawyer) {
        this.lawyerId = lawyer;
        return this;
    }

    public void setLawyerId(Lawyer lawyer) {
        this.lawyerId = lawyer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Booking)) {
            return false;
        }
        return id != null && id.equals(((Booking) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Booking{" +
            "id=" + getId() +
            ", bookingId='" + getBookingId() + "'" +
            ", bookingName='" + getBookingName() + "'" +
            ", paymentApproved='" + isPaymentApproved() + "'" +
            "}";
    }
}
