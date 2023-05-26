package com.modis.edu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.modis.edu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FragmentOrderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FragmentOrder.class);
        FragmentOrder fragmentOrder1 = new FragmentOrder();
        fragmentOrder1.setId("id1");
        FragmentOrder fragmentOrder2 = new FragmentOrder();
        fragmentOrder2.setId(fragmentOrder1.getId());
        assertThat(fragmentOrder1).isEqualTo(fragmentOrder2);
        fragmentOrder2.setId("id2");
        assertThat(fragmentOrder1).isNotEqualTo(fragmentOrder2);
        fragmentOrder1.setId(null);
        assertThat(fragmentOrder1).isNotEqualTo(fragmentOrder2);
    }
}
