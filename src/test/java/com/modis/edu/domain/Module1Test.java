package com.modis.edu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.modis.edu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class Module1Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Module1.class);
        Module1 module11 = new Module1();
        module11.setId("id1");
        Module1 module12 = new Module1();
        module12.setId(module11.getId());
        assertThat(module11).isEqualTo(module12);
        module12.setId("id2");
        assertThat(module11).isNotEqualTo(module12);
        module11.setId(null);
        assertThat(module11).isNotEqualTo(module12);
    }
}
