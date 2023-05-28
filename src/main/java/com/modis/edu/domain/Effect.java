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
 * A Effect.
 */
@Document(collection = "effect")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Effect implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @DBRef
    @Field("concepts")
    @JsonIgnoreProperties(
        value = { "parents", "childs", "competences", "activities", "goals", "preconditions", "effects" },
        allowSetters = true
    )
    private Set<Concept> concepts = new HashSet<>();

    @DBRef
    @Field("activities")
    @JsonIgnoreProperties(value = { "concepts", "preconditions", "effects", "fragment", "preferred" }, allowSetters = true)
    private Set<Activity> activities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Effect id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Effect title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Concept> getConcepts() {
        return this.concepts;
    }

    public void setConcepts(Set<Concept> concepts) {
        this.concepts = concepts;
    }

    public Effect concepts(Set<Concept> concepts) {
        this.setConcepts(concepts);
        return this;
    }

    public Effect addConcept(Concept concept) {
        this.concepts.add(concept);
        concept.getEffects().add(this);
        return this;
    }

    public Effect removeConcept(Concept concept) {
        this.concepts.remove(concept);
        concept.getEffects().remove(this);
        return this;
    }

    public Set<Activity> getActivities() {
        return this.activities;
    }

    public void setActivities(Set<Activity> activities) {
        if (this.activities != null) {
            this.activities.forEach(i -> i.removeEffect(this));
        }
        if (activities != null) {
            activities.forEach(i -> i.addEffect(this));
        }
        this.activities = activities;
    }

    public Effect activities(Set<Activity> activities) {
        this.setActivities(activities);
        return this;
    }

    public Effect addActivity(Activity activity) {
        this.activities.add(activity);
        activity.getEffects().add(this);
        return this;
    }

    public Effect removeActivity(Activity activity) {
        this.activities.remove(activity);
        activity.getEffects().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Effect)) {
            return false;
        }
        return id != null && id.equals(((Effect) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Effect{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
