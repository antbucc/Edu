package com.modis.edu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.modis.edu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PreferredActivityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreferredActivity.class);
        PreferredActivity preferredActivity1 = new PreferredActivity();
        preferredActivity1.setId("id1");
        PreferredActivity preferredActivity2 = new PreferredActivity();
        preferredActivity2.setId(preferredActivity1.getId());
        assertThat(preferredActivity1).isEqualTo(preferredActivity2);
        preferredActivity2.setId("id2");
        assertThat(preferredActivity1).isNotEqualTo(preferredActivity2);
        preferredActivity1.setId(null);
        assertThat(preferredActivity1).isNotEqualTo(preferredActivity2);
    }
}
