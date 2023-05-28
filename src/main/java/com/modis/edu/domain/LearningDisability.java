package com.modis.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.modis.edu.domain.enumeration.DisabilityType;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A LearningDisability.
 */
@Document(collection = "learning_disability")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LearningDisability implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @Field("disability_type")
    private DisabilityType disabilityType;

    @DBRef
    @Field("learner")
    @JsonIgnoreProperties(value = { "learningDisabilities", "scenarios" }, allowSetters = true)
    private Learner learner;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public LearningDisability id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public LearningDisability name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public LearningDisability description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DisabilityType getDisabilityType() {
        return this.disabilityType;
    }

    public LearningDisability disabilityType(DisabilityType disabilityType) {
        this.setDisabilityType(disabilityType);
        return this;
    }

    public void setDisabilityType(DisabilityType disabilityType) {
        this.disabilityType = disabilityType;
    }

    public Learner getLearner() {
        return this.learner;
    }

    public void setLearner(Learner learner) {
        this.learner = learner;
    }

    public LearningDisability learner(Learner learner) {
        this.setLearner(learner);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LearningDisability)) {
            return false;
        }
        return id != null && id.equals(((LearningDisability) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LearningDisability{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", disabilityType='" + getDisabilityType() + "'" +
            "}";
    }
}
