package com.afp.mylawyer.web.rest.vm;

import java.util.List;

import com.afp.mylawyer.service.dto.BookingDTO;

public class BookingResponseVM extends ResponseVM {
	private List<BookingDTO> bookings;

	public List<BookingDTO> getBookings() {
		return bookings;
	}

	public void setBookings(List<BookingDTO> bookings) {
		this.bookings = bookings;
	}

}
