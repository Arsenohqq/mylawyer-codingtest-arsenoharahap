package com.afp.mylawyer.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afp.mylawyer.service.BookingManagementService;
import com.afp.mylawyer.service.BookingService;
import com.afp.mylawyer.web.rest.vm.APIStatus;
import com.afp.mylawyer.web.rest.vm.ResponseVM;
import com.afp.mylawyer.web.service.util.LawyerAssigner;

import io.github.jhipster.web.util.ResponseUtil;

import org.springframework.http.ResponseEntity;

/**
 * BookingManagerResource controller
 */
@RestController
@RequestMapping("/api/bookingmanagement")
public class BookingManagerResource {

    private final Logger log = LoggerFactory.getLogger(BookingManagerResource.class);
    
    private final BookingManagementService bookingManagementService;

    public BookingManagerResource(BookingManagementService bookingManagementService) {
        this.bookingManagementService = bookingManagementService;
    }
    

    /**
    * GET approveBookingPayment
    * 
    *  Coding Test #2
    */
    @GetMapping("/approveBookingPayment/{id}")
    public ResponseEntity<ResponseVM> approveBookingPayment(@PathVariable Long id) {
        log.info("===Approving booking payment for booking {}===", id);
        
        Boolean success = bookingManagementService.acceptPayment(id);
        ResponseEntity<ResponseVM> response;
        ResponseVM responseVm = new ResponseVM();
        
        if (success) {
        	responseVm.setApiStatus(APIStatus.SUCCESS);
            response = ResponseEntity.ok(responseVm);
		} else {
			responseVm.setApiStatus(APIStatus.FAILED);
			response = ResponseEntity.badRequest().body(responseVm);
		}

        return response;
    }

    /**
    * POST assignLawyer
    * 
    * Coding Test #4
    * 
    */
    @PostMapping("/assignLawyer")
    public ResponseEntity<ResponseVM> assignLawyer(@RequestBody LawyerAssigner assigner) {
        log.info("===assigning booking {} to lawyer {}===", assigner.getBookingId(), assigner.getLawyerId());
        Boolean success = bookingManagementService.assignLawyer(assigner);
        ResponseEntity<ResponseVM> response;
        ResponseVM responseVm = new ResponseVM();
        
        if (success) {
        	responseVm.setApiStatus(APIStatus.SUCCESS);
            response = ResponseEntity.ok(responseVm);
		} else {
			responseVm.setApiStatus(APIStatus.FAILED);
			response = ResponseEntity.badRequest().body(responseVm);
		}

        
        return response;
    }

}
