package com.modis.edu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.modis.edu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Set.class);
        Set set1 = new Set();
        set1.setId("id1");
        Set set2 = new Set();
        set2.setId(set1.getId());
        assertThat(set1).isEqualTo(set2);
        set2.setId("id2");
        assertThat(set1).isNotEqualTo(set2);
        set1.setId(null);
        assertThat(set1).isNotEqualTo(set2);
    }
}
