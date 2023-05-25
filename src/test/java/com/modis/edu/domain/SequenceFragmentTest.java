package com.modis.edu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.modis.edu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SequenceFragmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenceFragment.class);
        SequenceFragment sequenceFragment1 = new SequenceFragment();
        sequenceFragment1.setId("id1");
        SequenceFragment sequenceFragment2 = new SequenceFragment();
        sequenceFragment2.setId(sequenceFragment1.getId());
        assertThat(sequenceFragment1).isEqualTo(sequenceFragment2);
        sequenceFragment2.setId("id2");
        assertThat(sequenceFragment1).isNotEqualTo(sequenceFragment2);
        sequenceFragment1.setId(null);
        assertThat(sequenceFragment1).isNotEqualTo(sequenceFragment2);
    }
}
