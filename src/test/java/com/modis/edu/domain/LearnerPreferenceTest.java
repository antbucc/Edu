package com.modis.edu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.modis.edu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LearnerPreferenceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LearnerPreference.class);
        LearnerPreference learnerPreference1 = new LearnerPreference();
        learnerPreference1.setId("id1");
        LearnerPreference learnerPreference2 = new LearnerPreference();
        learnerPreference2.setId(learnerPreference1.getId());
        assertThat(learnerPreference1).isEqualTo(learnerPreference2);
        learnerPreference2.setId("id2");
        assertThat(learnerPreference1).isNotEqualTo(learnerPreference2);
        learnerPreference1.setId(null);
        assertThat(learnerPreference1).isNotEqualTo(learnerPreference2);
    }
}
