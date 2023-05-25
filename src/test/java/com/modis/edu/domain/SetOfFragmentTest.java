package com.modis.edu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.modis.edu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SetOfFragmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SetOfFragment.class);
        SetOfFragment setOfFragment1 = new SetOfFragment();
        setOfFragment1.setId("id1");
        SetOfFragment setOfFragment2 = new SetOfFragment();
        setOfFragment2.setId(setOfFragment1.getId());
        assertThat(setOfFragment1).isEqualTo(setOfFragment2);
        setOfFragment2.setId("id2");
        assertThat(setOfFragment1).isNotEqualTo(setOfFragment2);
        setOfFragment1.setId(null);
        assertThat(setOfFragment1).isNotEqualTo(setOfFragment2);
    }
}
