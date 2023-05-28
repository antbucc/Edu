package com.modis.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.modis.edu.domain.enumeration.Difficulty;
import com.modis.edu.domain.enumeration.DisabilityType;
import com.modis.edu.domain.enumeration.LearningStyleType;
import com.modis.edu.domain.enumeration.ModalityType;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A LearnerPreference.
 */
@Document(collection = "learner_preference")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LearnerPreference implements Serializable {

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

    @Field("disability")
    private DisabilityType disability;

    @DBRef
    @Field("learner")
    @JsonIgnoreProperties(value = { "learnerPreferences", "scenarios" }, allowSetters = true)
    private Learner learner;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public LearnerPreference id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public LearnerPreference title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LearningStyleType getStyle() {
        return this.style;
    }

    public LearnerPreference style(LearningStyleType style) {
        this.setStyle(style);
        return this;
    }

    public void setStyle(LearningStyleType style) {
        this.style = style;
    }

    public ModalityType getModality() {
        return this.modality;
    }

    public LearnerPreference modality(ModalityType modality) {
        this.setModality(modality);
        return this;
    }

    public void setModality(ModalityType modality) {
        this.modality = modality;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public LearnerPreference difficulty(Difficulty difficulty) {
        this.setDifficulty(difficulty);
        return this;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public DisabilityType getDisability() {
        return this.disability;
    }

    public LearnerPreference disability(DisabilityType disability) {
        this.setDisability(disability);
        return this;
    }

    public void setDisability(DisabilityType disability) {
        this.disability = disability;
    }

    public Learner getLearner() {
        return this.learner;
    }

    public void setLearner(Learner learner) {
        this.learner = learner;
    }

    public LearnerPreference learner(Learner learner) {
        this.setLearner(learner);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LearnerPreference)) {
            return false;
        }
        return id != null && id.equals(((LearnerPreference) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LearnerPreference{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", style='" + getStyle() + "'" +
            ", modality='" + getModality() + "'" +
            ", difficulty='" + getDifficulty() + "'" +
            ", disability='" + getDisability() + "'" +
            "}";
    }
}
