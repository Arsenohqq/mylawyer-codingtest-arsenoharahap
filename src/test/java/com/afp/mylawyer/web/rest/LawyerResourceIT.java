package com.afp.mylawyer.web.rest;

import com.afp.mylawyer.MylawyerarsenohaApp;
import com.afp.mylawyer.domain.Lawyer;
import com.afp.mylawyer.repository.LawyerRepository;
import com.afp.mylawyer.service.LawyerService;
import com.afp.mylawyer.service.dto.LawyerDTO;
import com.afp.mylawyer.service.mapper.LawyerMapper;
import com.afp.mylawyer.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.afp.mylawyer.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LawyerResource} REST controller.
 */
@SpringBootTest(classes = MylawyerarsenohaApp.class)
public class LawyerResourceIT {

    private static final String DEFAULT_LAWYER_ID = "AAAAAAAAAA";
    private static final String UPDATED_LAWYER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LAWYER_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAWYER_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAWYER_PHONE_NUMBER = "71";
    private static final String UPDATED_LAWYER_PHONE_NUMBER = "2-";

    @Autowired
    private LawyerRepository lawyerRepository;

    @Autowired
    private LawyerMapper lawyerMapper;

    @Autowired
    private LawyerService lawyerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restLawyerMockMvc;

    private Lawyer lawyer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LawyerResource lawyerResource = new LawyerResource(lawyerService);
        this.restLawyerMockMvc = MockMvcBuilders.standaloneSetup(lawyerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lawyer createEntity(EntityManager em) {
        Lawyer lawyer = new Lawyer()
            .lawyerId(DEFAULT_LAWYER_ID)
            .lawyerFullName(DEFAULT_LAWYER_FULL_NAME)
            .lawyerPhoneNumber(DEFAULT_LAWYER_PHONE_NUMBER);
        return lawyer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lawyer createUpdatedEntity(EntityManager em) {
        Lawyer lawyer = new Lawyer()
            .lawyerId(UPDATED_LAWYER_ID)
            .lawyerFullName(UPDATED_LAWYER_FULL_NAME)
            .lawyerPhoneNumber(UPDATED_LAWYER_PHONE_NUMBER);
        return lawyer;
    }

    @BeforeEach
    public void initTest() {
        lawyer = createEntity(em);
    }

    @Test
    @Transactional
    public void createLawyer() throws Exception {
        int databaseSizeBeforeCreate = lawyerRepository.findAll().size();

        // Create the Lawyer
        LawyerDTO lawyerDTO = lawyerMapper.toDto(lawyer);
        restLawyerMockMvc.perform(post("/api/lawyers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lawyerDTO)))
            .andExpect(status().isCreated());

        // Validate the Lawyer in the database
        List<Lawyer> lawyerList = lawyerRepository.findAll();
        assertThat(lawyerList).hasSize(databaseSizeBeforeCreate + 1);
        Lawyer testLawyer = lawyerList.get(lawyerList.size() - 1);
        assertThat(testLawyer.getLawyerId()).isEqualTo(DEFAULT_LAWYER_ID);
        assertThat(testLawyer.getLawyerFullName()).isEqualTo(DEFAULT_LAWYER_FULL_NAME);
        assertThat(testLawyer.getLawyerPhoneNumber()).isEqualTo(DEFAULT_LAWYER_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void createLawyerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lawyerRepository.findAll().size();

        // Create the Lawyer with an existing ID
        lawyer.setId(1L);
        LawyerDTO lawyerDTO = lawyerMapper.toDto(lawyer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLawyerMockMvc.perform(post("/api/lawyers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lawyerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lawyer in the database
        List<Lawyer> lawyerList = lawyerRepository.findAll();
        assertThat(lawyerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLawyers() throws Exception {
        // Initialize the database
        lawyerRepository.saveAndFlush(lawyer);

        // Get all the lawyerList
        restLawyerMockMvc.perform(get("/api/lawyers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lawyer.getId().intValue())))
            .andExpect(jsonPath("$.[*].lawyerId").value(hasItem(DEFAULT_LAWYER_ID)))
            .andExpect(jsonPath("$.[*].lawyerFullName").value(hasItem(DEFAULT_LAWYER_FULL_NAME)))
            .andExpect(jsonPath("$.[*].lawyerPhoneNumber").value(hasItem(DEFAULT_LAWYER_PHONE_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getLawyer() throws Exception {
        // Initialize the database
        lawyerRepository.saveAndFlush(lawyer);

        // Get the lawyer
        restLawyerMockMvc.perform(get("/api/lawyers/{id}", lawyer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lawyer.getId().intValue()))
            .andExpect(jsonPath("$.lawyerId").value(DEFAULT_LAWYER_ID))
            .andExpect(jsonPath("$.lawyerFullName").value(DEFAULT_LAWYER_FULL_NAME))
            .andExpect(jsonPath("$.lawyerPhoneNumber").value(DEFAULT_LAWYER_PHONE_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingLawyer() throws Exception {
        // Get the lawyer
        restLawyerMockMvc.perform(get("/api/lawyers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLawyer() throws Exception {
        // Initialize the database
        lawyerRepository.saveAndFlush(lawyer);

        int databaseSizeBeforeUpdate = lawyerRepository.findAll().size();

        // Update the lawyer
        Lawyer updatedLawyer = lawyerRepository.findById(lawyer.getId()).get();
        // Disconnect from session so that the updates on updatedLawyer are not directly saved in db
        em.detach(updatedLawyer);
        updatedLawyer
            .lawyerId(UPDATED_LAWYER_ID)
            .lawyerFullName(UPDATED_LAWYER_FULL_NAME)
            .lawyerPhoneNumber(UPDATED_LAWYER_PHONE_NUMBER);
        LawyerDTO lawyerDTO = lawyerMapper.toDto(updatedLawyer);

        restLawyerMockMvc.perform(put("/api/lawyers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lawyerDTO)))
            .andExpect(status().isOk());

        // Validate the Lawyer in the database
        List<Lawyer> lawyerList = lawyerRepository.findAll();
        assertThat(lawyerList).hasSize(databaseSizeBeforeUpdate);
        Lawyer testLawyer = lawyerList.get(lawyerList.size() - 1);
        assertThat(testLawyer.getLawyerId()).isEqualTo(UPDATED_LAWYER_ID);
        assertThat(testLawyer.getLawyerFullName()).isEqualTo(UPDATED_LAWYER_FULL_NAME);
        assertThat(testLawyer.getLawyerPhoneNumber()).isEqualTo(UPDATED_LAWYER_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingLawyer() throws Exception {
        int databaseSizeBeforeUpdate = lawyerRepository.findAll().size();

        // Create the Lawyer
        LawyerDTO lawyerDTO = lawyerMapper.toDto(lawyer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLawyerMockMvc.perform(put("/api/lawyers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lawyerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lawyer in the database
        List<Lawyer> lawyerList = lawyerRepository.findAll();
        assertThat(lawyerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLawyer() throws Exception {
        // Initialize the database
        lawyerRepository.saveAndFlush(lawyer);

        int databaseSizeBeforeDelete = lawyerRepository.findAll().size();

        // Delete the lawyer
        restLawyerMockMvc.perform(delete("/api/lawyers/{id}", lawyer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Lawyer> lawyerList = lawyerRepository.findAll();
        assertThat(lawyerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
