package com.afp.mylawyer.web.rest;

import com.afp.mylawyer.MylawyerarsenohaApp;
import com.afp.mylawyer.service.BookingManagementService;
import com.afp.mylawyer.service.BookingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the BookingManagerResource REST controller.
 *
 * @see BookingManagerResource
 */
@SpringBootTest(classes = MylawyerarsenohaApp.class)
public class BookingManagerResourceIT {

    private MockMvc restMockMvc;
    
    @Autowired
    private BookingManagementService bookingManagementService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        BookingManagerResource bookingManagerResource = new BookingManagerResource(bookingManagementService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(bookingManagerResource)
            .build();
    }

    /**
     * Test approveBookingPayment
     */
    @Test
    public void testApproveBookingPayment() throws Exception {
        restMockMvc.perform(get("/api/booking-manager/approve-booking-payment"))
            .andExpect(status().isOk());
    }

    /**
     * Test assignLawyer
     */
    @Test
    public void testAssignLawyer() throws Exception {
        restMockMvc.perform(post("/api/booking-manager/assign-lawyer"))
            .andExpect(status().isOk());
    }
}
