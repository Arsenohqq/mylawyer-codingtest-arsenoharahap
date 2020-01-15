package com.afp.mylawyer.service.mapper;

import com.afp.mylawyer.domain.*;
import com.afp.mylawyer.service.dto.LawyerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Lawyer} and its DTO {@link LawyerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LawyerMapper extends EntityMapper<LawyerDTO, Lawyer> {



    default Lawyer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lawyer lawyer = new Lawyer();
        lawyer.setId(id);
        return lawyer;
    }
}
