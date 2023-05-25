package com.modis.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Goal.
 */
@Document(collection = "goal")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Goal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @DBRef
    @Field("goal")
    @JsonIgnoreProperties(value = { "parents", "concepts", "childs", "competences", "activities" }, allowSetters = true)
    private Set<Concept> goals = new HashSet<>();

    @DBRef
    @Field("abstractActivities")
    @JsonIgnoreProperties(value = { "goals", "fragments" }, allowSetters = true)
    private Set<AbstractActivity> abstractActivities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Goal id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Goal title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Concept> getGoals() {
        return this.goals;
    }

    public void setGoals(Set<Concept> concepts) {
        if (this.goals != null) {
            this.goals.forEach(i -> i.setConcepts(null));
        }
        if (concepts != null) {
            concepts.forEach(i -> i.setConcepts(this));
        }
        this.goals = concepts;
    }

    public Goal goals(Set<Concept> concepts) {
        this.setGoals(concepts);
        return this;
    }

    public Goal addGoal(Concept concept) {
        this.goals.add(concept);
        concept.setConcepts(this);
        return this;
    }

    public Goal removeGoal(Concept concept) {
        this.goals.remove(concept);
        concept.setConcepts(null);
        return this;
    }

    public Set<AbstractActivity> getAbstractActivities() {
        return this.abstractActivities;
    }

    public void setAbstractActivities(Set<AbstractActivity> abstractActivities) {
        if (this.abstractActivities != null) {
            this.abstractActivities.forEach(i -> i.removeGoal(this));
        }
        if (abstractActivities != null) {
            abstractActivities.forEach(i -> i.addGoal(this));
        }
        this.abstractActivities = abstractActivities;
    }

    public Goal abstractActivities(Set<AbstractActivity> abstractActivities) {
        this.setAbstractActivities(abstractActivities);
        return this;
    }

    public Goal addAbstractActivity(AbstractActivity abstractActivity) {
        this.abstractActivities.add(abstractActivity);
        abstractActivity.getGoals().add(this);
        return this;
    }

    public Goal removeAbstractActivity(AbstractActivity abstractActivity) {
        this.abstractActivities.remove(abstractActivity);
        abstractActivity.getGoals().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Goal)) {
            return false;
        }
        return id != null && id.equals(((Goal) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Goal{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
