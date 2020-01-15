package com.afp.mylawyer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.afp.mylawyer.domain.Lawyer;
import com.afp.mylawyer.repository.LawyerRepository;
import com.afp.mylawyer.service.dto.LawyerDTO;
import com.afp.mylawyer.service.mapper.LawyerMapper;
import com.afp.mylawyer.web.service.util.LawyerAssigner;
import com.afp.mylawyer.domain.Booking;
import com.afp.mylawyer.repository.BookingRepository;


@Service
@Transactional
public class BookingManagementService {

    private final Logger log = LoggerFactory.getLogger(BookingManagementService.class);

    private final BookingRepository bookingRepository;
    private final LawyerRepository lawyerRepository;



    public BookingManagementService(BookingRepository bookingRepository,
                          LawyerRepository lawyerRepository) {
        this.bookingRepository = bookingRepository;
        this.lawyerRepository = lawyerRepository;
    }

    public boolean assignLawyer(LawyerAssigner assigner){
    	try {
    		Booking booking = bookingRepository.getOne(assigner.getBookingId());
        	Lawyer lawyer = lawyerRepository.getOne(assigner.getLawyerId());
        	
        	if(booking.isPaymentApproved()) { // (AH) payment approved, can assign lawyer
        		booking.setLawyerId(lawyer);
            	bookingRepository.save(booking);
            	
            	return true;
        	} else { // (AH) payment not yet approved, cannot assign lawyer
        		return false;
        	}
        	
        	
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
    
    public boolean acceptPayment(Long id) {
    	try {
    		Booking booking = bookingRepository.getOne(id);
        	booking.setPaymentApproved(true);    	
        	bookingRepository.save(booking);
        	
        	return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    };

}
