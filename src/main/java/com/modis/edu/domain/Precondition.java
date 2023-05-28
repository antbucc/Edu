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
 * A Precondition.
 */
@Document(collection = "precondition")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Precondition implements Serializable {

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

    public Precondition id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Precondition title(String title) {
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

    public Precondition concepts(Set<Concept> concepts) {
        this.setConcepts(concepts);
        return this;
    }

    public Precondition addConcept(Concept concept) {
        this.concepts.add(concept);
        concept.getPreconditions().add(this);
        return this;
    }

    public Precondition removeConcept(Concept concept) {
        this.concepts.remove(concept);
        concept.getPreconditions().remove(this);
        return this;
    }

    public Set<Activity> getActivities() {
        return this.activities;
    }

    public void setActivities(Set<Activity> activities) {
        if (this.activities != null) {
            this.activities.forEach(i -> i.removePrecondition(this));
        }
        if (activities != null) {
            activities.forEach(i -> i.addPrecondition(this));
        }
        this.activities = activities;
    }

    public Precondition activities(Set<Activity> activities) {
        this.setActivities(activities);
        return this;
    }

    public Precondition addActivity(Activity activity) {
        this.activities.add(activity);
        activity.getPreconditions().add(this);
        return this;
    }

    public Precondition removeActivity(Activity activity) {
        this.activities.remove(activity);
        activity.getPreconditions().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Precondition)) {
            return false;
        }
        return id != null && id.equals(((Precondition) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Precondition{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
