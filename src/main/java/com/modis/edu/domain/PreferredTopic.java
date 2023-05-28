package com.modis.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A PreferredTopic.
 */
@Document(collection = "preferred_topic")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PreferredTopic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("topic_id")
    private Long topicId;

    @Field("topic")
    private String topic;

    @DBRef
    @Field("educatorPreference")
    @JsonIgnoreProperties(value = { "educator" }, allowSetters = true)
    private EducatorPreference educatorPreference;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public PreferredTopic id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTopicId() {
        return this.topicId;
    }

    public PreferredTopic topicId(Long topicId) {
        this.setTopicId(topicId);
        return this;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getTopic() {
        return this.topic;
    }

    public PreferredTopic topic(String topic) {
        this.setTopic(topic);
        return this;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public EducatorPreference getEducatorPreference() {
        return this.educatorPreference;
    }

    public void setEducatorPreference(EducatorPreference educatorPreference) {
        this.educatorPreference = educatorPreference;
    }

    public PreferredTopic educatorPreference(EducatorPreference educatorPreference) {
        this.setEducatorPreference(educatorPreference);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PreferredTopic)) {
            return false;
        }
        return id != null && id.equals(((PreferredTopic) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PreferredTopic{" +
            "id=" + getId() +
            ", topicId=" + getTopicId() +
            ", topic='" + getTopic() + "'" +
            "}";
    }
}
