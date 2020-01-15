package com.afp.mylawyer.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.afp.mylawyer.web.rest.TestUtil;

public class LawyerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lawyer.class);
        Lawyer lawyer1 = new Lawyer();
        lawyer1.setId(1L);
        Lawyer lawyer2 = new Lawyer();
        lawyer2.setId(lawyer1.getId());
        assertThat(lawyer1).isEqualTo(lawyer2);
        lawyer2.setId(2L);
        assertThat(lawyer1).isNotEqualTo(lawyer2);
        lawyer1.setId(null);
        assertThat(lawyer1).isNotEqualTo(lawyer2);
    }
}
