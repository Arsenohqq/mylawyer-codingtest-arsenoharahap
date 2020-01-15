package com.afp.mylawyer.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.afp.mylawyer.domain.Booking} entity.
 */
public class BookingDTO implements Serializable {

    private Long id;

    private String bookingId;

    private String bookingName;

    private Boolean paymentApproved;


    private Long lawyerIdId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingName() {
        return bookingName;
    }

    public void setBookingName(String bookingName) {
        this.bookingName = bookingName;
    }

    public Boolean isPaymentApproved() {
        return paymentApproved;
    }

    public void setPaymentApproved(Boolean paymentApproved) {
        this.paymentApproved = paymentApproved;
    }

    public Long getLawyerIdId() {
        return lawyerIdId;
    }

    public void setLawyerIdId(Long lawyerId) {
        this.lawyerIdId = lawyerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BookingDTO bookingDTO = (BookingDTO) o;
        if (bookingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookingDTO{" +
            "id=" + getId() +
            ", bookingId='" + getBookingId() + "'" +
            ", bookingName='" + getBookingName() + "'" +
            ", paymentApproved='" + isPaymentApproved() + "'" +
            ", lawyerId=" + getLawyerIdId() +
            "}";
    }
}
