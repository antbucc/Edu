package com.modis.edu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.modis.edu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SetOfTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SetOf.class);
        SetOf setOf1 = new SetOf();
        setOf1.setId("id1");
        SetOf setOf2 = new SetOf();
        setOf2.setId(setOf1.getId());
        assertThat(setOf1).isEqualTo(setOf2);
        setOf2.setId("id2");
        assertThat(setOf1).isNotEqualTo(setOf2);
        setOf1.setId(null);
        assertThat(setOf1).isNotEqualTo(setOf2);
    }
}
