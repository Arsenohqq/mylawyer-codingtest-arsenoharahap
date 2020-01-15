package com.afp.mylawyer.service.mapper;

import com.afp.mylawyer.domain.*;
import com.afp.mylawyer.service.dto.BookingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Booking} and its DTO {@link BookingDTO}.
 */
@Mapper(componentModel = "spring", uses = {LawyerMapper.class})
public interface BookingMapper extends EntityMapper<BookingDTO, Booking> {

    @Mapping(source = "lawyerId.id", target = "lawyerIdId")
    BookingDTO toDto(Booking booking);

    @Mapping(source = "lawyerIdId", target = "lawyerId")
    Booking toEntity(BookingDTO bookingDTO);

    default Booking fromId(Long id) {
        if (id == null) {
            return null;
        }
        Booking booking = new Booking();
        booking.setId(id);
        return booking;
    }
}
