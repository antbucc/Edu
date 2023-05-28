package com.modis.edu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.modis.edu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EducatorPreferenceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EducatorPreference.class);
        EducatorPreference educatorPreference1 = new EducatorPreference();
        educatorPreference1.setId("id1");
        EducatorPreference educatorPreference2 = new EducatorPreference();
        educatorPreference2.setId(educatorPreference1.getId());
        assertThat(educatorPreference1).isEqualTo(educatorPreference2);
        educatorPreference2.setId("id2");
        assertThat(educatorPreference1).isNotEqualTo(educatorPreference2);
        educatorPreference1.setId(null);
        assertThat(educatorPreference1).isNotEqualTo(educatorPreference2);
    }
}
