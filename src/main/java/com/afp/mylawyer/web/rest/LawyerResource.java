package com.afp.mylawyer.web.rest;

import com.afp.mylawyer.service.LawyerService;
import com.afp.mylawyer.web.rest.errors.BadRequestAlertException;
import com.afp.mylawyer.web.rest.vm.APIStatus;
import com.afp.mylawyer.web.rest.vm.LawyerResponseVM;
import com.afp.mylawyer.service.dto.LawyerDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.afp.mylawyer.domain.Lawyer}.
 */
@RestController
@RequestMapping("/api")
public class LawyerResource {

    private final Logger log = LoggerFactory.getLogger(LawyerResource.class);

    private static final String ENTITY_NAME = "lawyer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LawyerService lawyerService;

    public LawyerResource(LawyerService lawyerService) {
        this.lawyerService = lawyerService;
    }

    /**
     * {@code POST  /lawyers} : Create a new lawyer.
     *
     * @param lawyerDTO the lawyerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lawyerDTO, or with status {@code 400 (Bad Request)} if the lawyer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lawyers")
    public ResponseEntity<LawyerDTO> createLawyer(@Valid @RequestBody LawyerDTO lawyerDTO) throws URISyntaxException {
        log.debug("REST request to save Lawyer : {}", lawyerDTO);
        if (lawyerDTO.getId() != null) {
            throw new BadRequestAlertException("A new lawyer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LawyerDTO result = lawyerService.save(lawyerDTO);
        return ResponseEntity.created(new URI("/api/lawyers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lawyers} : Updates an existing lawyer.
     *
     * @param lawyerDTO the lawyerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lawyerDTO,
     * or with status {@code 400 (Bad Request)} if the lawyerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lawyerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lawyers")
    public ResponseEntity<LawyerDTO> updateLawyer(@Valid @RequestBody LawyerDTO lawyerDTO) throws URISyntaxException {
        log.debug("REST request to update Lawyer : {}", lawyerDTO);
        if (lawyerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LawyerDTO result = lawyerService.save(lawyerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lawyerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /lawyers} : get all the lawyers.
     * 
     * (AH) Coding Test #3
     * modified from the generated template provided by JHipster
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lawyers in body.
     */
    @GetMapping({"/lawyers", "/getLawyerList"})
    public ResponseEntity<LawyerResponseVM> getAllLawyers(Pageable pageable) {
        log.debug("REST request to get a page of Lawyers");
        LawyerResponseVM responseVm = new LawyerResponseVM();
        try {
        	Page<LawyerDTO> page = lawyerService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            responseVm.setApiStatus(APIStatus.SUCCESS);
            responseVm.setLawyers(page.getContent());
            return ResponseEntity.ok().headers(headers).body(responseVm);
		} catch (Exception e) {
			e.printStackTrace();
			responseVm.setApiStatus(APIStatus.FAILED);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseVm);
		}
        
    }

    /**
     * {@code GET  /lawyers/:id} : get the "id" lawyer.
     *
     * @param id the id of the lawyerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lawyerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lawyers/{id}")
    public ResponseEntity<LawyerDTO> getLawyer(@PathVariable Long id) {
        log.debug("REST request to get Lawyer : {}", id);
        Optional<LawyerDTO> lawyerDTO = lawyerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lawyerDTO);
    }

    /**
     * {@code DELETE  /lawyers/:id} : delete the "id" lawyer.
     *
     * @param id the id of the lawyerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lawyers/{id}")
    public ResponseEntity<Void> deleteLawyer(@PathVariable Long id) {
        log.debug("REST request to delete Lawyer : {}", id);
        lawyerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
