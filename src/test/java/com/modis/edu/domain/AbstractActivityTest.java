package com.modis.edu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.modis.edu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AbstractActivityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AbstractActivity.class);
        AbstractActivity abstractActivity1 = new AbstractActivity();
        abstractActivity1.setId("id1");
        AbstractActivity abstractActivity2 = new AbstractActivity();
        abstractActivity2.setId(abstractActivity1.getId());
        assertThat(abstractActivity1).isEqualTo(abstractActivity2);
        abstractActivity2.setId("id2");
        assertThat(abstractActivity1).isNotEqualTo(abstractActivity2);
        abstractActivity1.setId(null);
        assertThat(abstractActivity1).isNotEqualTo(abstractActivity2);
    }
}
