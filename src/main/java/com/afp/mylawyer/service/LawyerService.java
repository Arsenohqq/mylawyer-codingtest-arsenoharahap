package com.afp.mylawyer.service;

import com.afp.mylawyer.domain.Lawyer;
import com.afp.mylawyer.repository.LawyerRepository;
import com.afp.mylawyer.service.dto.LawyerDTO;
import com.afp.mylawyer.service.mapper.LawyerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Lawyer}.
 */
@Service
@Transactional
public class LawyerService {

    private final Logger log = LoggerFactory.getLogger(LawyerService.class);

    private final LawyerRepository lawyerRepository;

    private final LawyerMapper lawyerMapper;

    public LawyerService(LawyerRepository lawyerRepository, LawyerMapper lawyerMapper) {
        this.lawyerRepository = lawyerRepository;
        this.lawyerMapper = lawyerMapper;
    }

    /**
     * Save a lawyer.
     *
     * @param lawyerDTO the entity to save.
     * @return the persisted entity.
     */
    public LawyerDTO save(LawyerDTO lawyerDTO) {
        log.debug("Request to save Lawyer : {}", lawyerDTO);
        Lawyer lawyer = lawyerMapper.toEntity(lawyerDTO);
        lawyer = lawyerRepository.save(lawyer);
        return lawyerMapper.toDto(lawyer);
    }

    /**
     * Get all the lawyers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LawyerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Lawyers");
        return lawyerRepository.findAll(pageable)
            .map(lawyerMapper::toDto);
    }


    /**
     * Get one lawyer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LawyerDTO> findOne(Long id) {
        log.debug("Request to get Lawyer : {}", id);
        return lawyerRepository.findById(id)
            .map(lawyerMapper::toDto);
    }

    /**
     * Delete the lawyer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Lawyer : {}", id);
        lawyerRepository.deleteById(id);
    }
}
