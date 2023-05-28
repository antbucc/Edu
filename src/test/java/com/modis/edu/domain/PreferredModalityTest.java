package com.modis.edu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.modis.edu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PreferredModalityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreferredModality.class);
        PreferredModality preferredModality1 = new PreferredModality();
        preferredModality1.setId("id1");
        PreferredModality preferredModality2 = new PreferredModality();
        preferredModality2.setId(preferredModality1.getId());
        assertThat(preferredModality1).isEqualTo(preferredModality2);
        preferredModality2.setId("id2");
        assertThat(preferredModality1).isNotEqualTo(preferredModality2);
        preferredModality1.setId(null);
        assertThat(preferredModality1).isNotEqualTo(preferredModality2);
    }
}
