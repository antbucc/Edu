package com.modis.edu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.modis.edu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PreferredLearningStyleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreferredLearningStyle.class);
        PreferredLearningStyle preferredLearningStyle1 = new PreferredLearningStyle();
        preferredLearningStyle1.setId("id1");
        PreferredLearningStyle preferredLearningStyle2 = new PreferredLearningStyle();
        preferredLearningStyle2.setId(preferredLearningStyle1.getId());
        assertThat(preferredLearningStyle1).isEqualTo(preferredLearningStyle2);
        preferredLearningStyle2.setId("id2");
        assertThat(preferredLearningStyle1).isNotEqualTo(preferredLearningStyle2);
        preferredLearningStyle1.setId(null);
        assertThat(preferredLearningStyle1).isNotEqualTo(preferredLearningStyle2);
    }
}
