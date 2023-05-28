package com.modis.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.modis.edu.domain.enumeration.LearningStyleType;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A PreferredLearningStyle.
 */
@Document(collection = "preferred_learning_style")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PreferredLearningStyle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("style")
    private LearningStyleType style;

    @DBRef
    @Field("educatorPreference")
    @JsonIgnoreProperties(
        value = { "preferredTopics", "preferredActivities", "preferredModalities", "preferredLearningStyles", "eductarPreferences" },
        allowSetters = true
    )
    private EducatorPreference educatorPreference;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public PreferredLearningStyle id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LearningStyleType getStyle() {
        return this.style;
    }

    public PreferredLearningStyle style(LearningStyleType style) {
        this.setStyle(style);
        return this;
    }

    public void setStyle(LearningStyleType style) {
        this.style = style;
    }

    public EducatorPreference getEducatorPreference() {
        return this.educatorPreference;
    }

    public void setEducatorPreference(EducatorPreference educatorPreference) {
        this.educatorPreference = educatorPreference;
    }

    public PreferredLearningStyle educatorPreference(EducatorPreference educatorPreference) {
        this.setEducatorPreference(educatorPreference);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PreferredLearningStyle)) {
            return false;
        }
        return id != null && id.equals(((PreferredLearningStyle) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PreferredLearningStyle{" +
            "id=" + getId() +
            ", style='" + getStyle() + "'" +
            "}";
    }
}
