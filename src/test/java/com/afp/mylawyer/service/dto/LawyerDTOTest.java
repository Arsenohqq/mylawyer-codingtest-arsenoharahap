package com.afp.mylawyer.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.afp.mylawyer.web.rest.TestUtil;

public class LawyerDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LawyerDTO.class);
        LawyerDTO lawyerDTO1 = new LawyerDTO();
        lawyerDTO1.setId(1L);
        LawyerDTO lawyerDTO2 = new LawyerDTO();
        assertThat(lawyerDTO1).isNotEqualTo(lawyerDTO2);
        lawyerDTO2.setId(lawyerDTO1.getId());
        assertThat(lawyerDTO1).isEqualTo(lawyerDTO2);
        lawyerDTO2.setId(2L);
        assertThat(lawyerDTO1).isNotEqualTo(lawyerDTO2);
        lawyerDTO1.setId(null);
        assertThat(lawyerDTO1).isNotEqualTo(lawyerDTO2);
    }
}
