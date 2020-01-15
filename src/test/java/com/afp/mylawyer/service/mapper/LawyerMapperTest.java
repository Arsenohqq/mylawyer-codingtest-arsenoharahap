package com.afp.mylawyer.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class LawyerMapperTest {

    private LawyerMapper lawyerMapper;

    @BeforeEach
    public void setUp() {
        lawyerMapper = new LawyerMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(lawyerMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(lawyerMapper.fromId(null)).isNull();
    }
}
