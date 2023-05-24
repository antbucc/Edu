package com.modis.edu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.modis.edu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SequenceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sequence.class);
        Sequence sequence1 = new Sequence();
        sequence1.setId("id1");
        Sequence sequence2 = new Sequence();
        sequence2.setId(sequence1.getId());
        assertThat(sequence1).isEqualTo(sequence2);
        sequence2.setId("id2");
        assertThat(sequence1).isNotEqualTo(sequence2);
        sequence1.setId(null);
        assertThat(sequence1).isNotEqualTo(sequence2);
    }
}
