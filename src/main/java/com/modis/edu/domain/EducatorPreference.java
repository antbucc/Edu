package com.modis.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.modis.edu.domain.enumeration.Difficulty;
import com.modis.edu.domain.enumeration.LearningStyleType;
import com.modis.edu.domain.enumeration.ModalityType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A EducatorPreference.
 */
@Document(collection = "educator_preference")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EducatorPreference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("style")
    private LearningStyleType style;

    @Field("modality")
    private ModalityType modality;

    @Field("difficulty")
    private Difficulty difficulty;

    @DBRef
    @Field("preferredActivities")
    @JsonIgnoreProperties(value = { "preferences" }, allowSetters = true)
    private Set<PreferredActivity> preferredActivities = new HashSet<>();

    @DBRef
    @Field("educator")
    @JsonIgnoreProperties(value = { "educatorPreferences", "scenarios" }, allowSetters = true)
    private Educator educator;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public EducatorPreference id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public EducatorPreference title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LearningStyleType getStyle() {
        return this.style;
    }

    public EducatorPreference style(LearningStyleType style) {
        this.setStyle(style);
        return this;
    }

    public void setStyle(LearningStyleType style) {
        this.style = style;
    }

    public ModalityType getModality() {
        return this.modality;
    }

    public EducatorPreference modality(ModalityType modality) {
        this.setModality(modality);
        return this;
    }

    public void setModality(ModalityType modality) {
        this.modality = modality;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public EducatorPreference difficulty(Difficulty difficulty) {
        this.setDifficulty(difficulty);
        return this;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Set<PreferredActivity> getPreferredActivities() {
        return this.preferredActivities;
    }

    public void setPreferredActivities(Set<PreferredActivity> preferredActivities) {
        this.preferredActivities = preferredActivities;
    }

    public EducatorPreference preferredActivities(Set<PreferredActivity> preferredActivities) {
        this.setPreferredActivities(preferredActivities);
        return this;
    }

    public EducatorPreference addPreferredActivity(PreferredActivity preferredActivity) {
        this.preferredActivities.add(preferredActivity);
        preferredActivity.getPreferences().add(this);
        return this;
    }

    public EducatorPreference removePreferredActivity(PreferredActivity preferredActivity) {
        this.preferredActivities.remove(preferredActivity);
        preferredActivity.getPreferences().remove(this);
        return this;
    }

    public Educator getEducator() {
        return this.educator;
    }

    public void setEducator(Educator educator) {
        this.educator = educator;
    }

    public EducatorPreference educator(Educator educator) {
        this.setEducator(educator);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EducatorPreference)) {
            return false;
        }
        return id != null && id.equals(((EducatorPreference) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EducatorPreference{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", style='" + getStyle() + "'" +
            ", modality='" + getModality() + "'" +
            ", difficulty='" + getDifficulty() + "'" +
            "}";
    }
}
