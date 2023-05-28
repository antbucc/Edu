package com.modis.edu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.modis.edu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LearningDisabilityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LearningDisability.class);
        LearningDisability learningDisability1 = new LearningDisability();
        learningDisability1.setId("id1");
        LearningDisability learningDisability2 = new LearningDisability();
        learningDisability2.setId(learningDisability1.getId());
        assertThat(learningDisability1).isEqualTo(learningDisability2);
        learningDisability2.setId("id2");
        assertThat(learningDisability1).isNotEqualTo(learningDisability2);
        learningDisability1.setId(null);
        assertThat(learningDisability1).isNotEqualTo(learningDisability2);
    }
}
