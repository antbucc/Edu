package com.modis.edu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.modis.edu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PreferredTopicTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreferredTopic.class);
        PreferredTopic preferredTopic1 = new PreferredTopic();
        preferredTopic1.setId("id1");
        PreferredTopic preferredTopic2 = new PreferredTopic();
        preferredTopic2.setId(preferredTopic1.getId());
        assertThat(preferredTopic1).isEqualTo(preferredTopic2);
        preferredTopic2.setId("id2");
        assertThat(preferredTopic1).isNotEqualTo(preferredTopic2);
        preferredTopic1.setId(null);
        assertThat(preferredTopic1).isNotEqualTo(preferredTopic2);
    }
}
