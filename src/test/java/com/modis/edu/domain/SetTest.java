package com.modis.edu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.modis.edu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SetOf.class);
        SetOf set1 = new SetOf();
        set1.setId("id1");
        SetOf set2 = new SetOf();
        set2.setId(set1.getId());
        assertThat(set1).isEqualTo(set2);
        set2.setId("id2");
        assertThat(set1).isNotEqualTo(set2);
        set1.setId(null);
        assertThat(set1).isNotEqualTo(set2);
    }
}
