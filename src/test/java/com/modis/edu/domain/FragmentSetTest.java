package com.modis.edu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.modis.edu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FragmentSetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FragmentSet.class);
        FragmentSet fragmentSet1 = new FragmentSet();
        fragmentSet1.setId("id1");
        FragmentSet fragmentSet2 = new FragmentSet();
        fragmentSet2.setId(fragmentSet1.getId());
        assertThat(fragmentSet1).isEqualTo(fragmentSet2);
        fragmentSet2.setId("id2");
        assertThat(fragmentSet1).isNotEqualTo(fragmentSet2);
        fragmentSet1.setId(null);
        assertThat(fragmentSet1).isNotEqualTo(fragmentSet2);
    }
}
