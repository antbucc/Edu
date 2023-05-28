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
 * A Concept.
 */
@Document(collection = "concept")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Concept implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @DBRef
    @Field("parent")
    @JsonIgnoreProperties(
        value = { "parents", "childs", "competences", "activities", "goals", "preconditions", "effects" },
        allowSetters = true
    )
    private Set<Concept> parents = new HashSet<>();

    @DBRef
    @Field("childs")
    @JsonIgnoreProperties(
        value = { "parents", "childs", "competences", "activities", "goals", "preconditions", "effects" },
        allowSetters = true
    )
    private Concept childs;

    @DBRef
    @Field("competences")
    @JsonIgnoreProperties(value = { "concepts", "scenarios" }, allowSetters = true)
    private Set<Competence> competences = new HashSet<>();

    @DBRef
    @Field("activities")
    @JsonIgnoreProperties(value = { "concepts", "preconditions", "effects", "fragment" }, allowSetters = true)
    private Set<Activity> activities = new HashSet<>();

    @DBRef
    @Field("goals")
    @JsonIgnoreProperties(value = { "concepts", "abstractActivities" }, allowSetters = true)
    private Set<Goal> goals = new HashSet<>();

    @DBRef
    @Field("preconditions")
    @JsonIgnoreProperties(value = { "concepts", "activities" }, allowSetters = true)
    private Set<Precondition> preconditions = new HashSet<>();

    @DBRef
    @Field("effects")
    @JsonIgnoreProperties(value = { "concepts", "activities" }, allowSetters = true)
    private Set<Effect> effects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Concept id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Concept title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Concept description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Concept> getParents() {
        return this.parents;
    }

    public void setParents(Set<Concept> concepts) {
        if (this.parents != null) {
            this.parents.forEach(i -> i.setChilds(null));
        }
        if (concepts != null) {
            concepts.forEach(i -> i.setChilds(this));
        }
        this.parents = concepts;
    }

    public Concept parents(Set<Concept> concepts) {
        this.setParents(concepts);
        return this;
    }

    public Concept addParent(Concept concept) {
        this.parents.add(concept);
        concept.setChilds(this);
        return this;
    }

    public Concept removeParent(Concept concept) {
        this.parents.remove(concept);
        concept.setChilds(null);
        return this;
    }

    public Concept getChilds() {
        return this.childs;
    }

    public void setChilds(Concept concept) {
        this.childs = concept;
    }

    public Concept childs(Concept concept) {
        this.setChilds(concept);
        return this;
    }

    public Set<Competence> getCompetences() {
        return this.competences;
    }

    public void setCompetences(Set<Competence> competences) {
        if (this.competences != null) {
            this.competences.forEach(i -> i.removeConcept(this));
        }
        if (competences != null) {
            competences.forEach(i -> i.addConcept(this));
        }
        this.competences = competences;
    }

    public Concept competences(Set<Competence> competences) {
        this.setCompetences(competences);
        return this;
    }

    public Concept addCompetence(Competence competence) {
        this.competences.add(competence);
        competence.getConcepts().add(this);
        return this;
    }

    public Concept removeCompetence(Competence competence) {
        this.competences.remove(competence);
        competence.getConcepts().remove(this);
        return this;
    }

    public Set<Activity> getActivities() {
        return this.activities;
    }

    public void setActivities(Set<Activity> activities) {
        if (this.activities != null) {
            this.activities.forEach(i -> i.removeConcept(this));
        }
        if (activities != null) {
            activities.forEach(i -> i.addConcept(this));
        }
        this.activities = activities;
    }

    public Concept activities(Set<Activity> activities) {
        this.setActivities(activities);
        return this;
    }

    public Concept addActivity(Activity activity) {
        this.activities.add(activity);
        activity.getConcepts().add(this);
        return this;
    }

    public Concept removeActivity(Activity activity) {
        this.activities.remove(activity);
        activity.getConcepts().remove(this);
        return this;
    }

    public Set<Goal> getGoals() {
        return this.goals;
    }

    public void setGoals(Set<Goal> goals) {
        if (this.goals != null) {
            this.goals.forEach(i -> i.removeConcept(this));
        }
        if (goals != null) {
            goals.forEach(i -> i.addConcept(this));
        }
        this.goals = goals;
    }

    public Concept goals(Set<Goal> goals) {
        this.setGoals(goals);
        return this;
    }

    public Concept addGoal(Goal goal) {
        this.goals.add(goal);
        goal.getConcepts().add(this);
        return this;
    }

    public Concept removeGoal(Goal goal) {
        this.goals.remove(goal);
        goal.getConcepts().remove(this);
        return this;
    }

    public Set<Precondition> getPreconditions() {
        return this.preconditions;
    }

    public void setPreconditions(Set<Precondition> preconditions) {
        if (this.preconditions != null) {
            this.preconditions.forEach(i -> i.removeConcept(this));
        }
        if (preconditions != null) {
            preconditions.forEach(i -> i.addConcept(this));
        }
        this.preconditions = preconditions;
    }

    public Concept preconditions(Set<Precondition> preconditions) {
        this.setPreconditions(preconditions);
        return this;
    }

    public Concept addPrecondition(Precondition precondition) {
        this.preconditions.add(precondition);
        precondition.getConcepts().add(this);
        return this;
    }

    public Concept removePrecondition(Precondition precondition) {
        this.preconditions.remove(precondition);
        precondition.getConcepts().remove(this);
        return this;
    }

    public Set<Effect> getEffects() {
        return this.effects;
    }

    public void setEffects(Set<Effect> effects) {
        if (this.effects != null) {
            this.effects.forEach(i -> i.removeConcept(this));
        }
        if (effects != null) {
            effects.forEach(i -> i.addConcept(this));
        }
        this.effects = effects;
    }

    public Concept effects(Set<Effect> effects) {
        this.setEffects(effects);
        return this;
    }

    public Concept addEffect(Effect effect) {
        this.effects.add(effect);
        effect.getConcepts().add(this);
        return this;
    }

    public Concept removeEffect(Effect effect) {
        this.effects.remove(effect);
        effect.getConcepts().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Concept)) {
            return false;
        }
        return id != null && id.equals(((Concept) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Concept{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
