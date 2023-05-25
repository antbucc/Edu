package com.modis.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.modis.edu.domain.enumeration.ActivityType;
import com.modis.edu.domain.enumeration.Difficulty;
import com.modis.edu.domain.enumeration.Tool;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Activity.
 */
@Document(collection = "activity")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("type")
    private ActivityType type;

    @Field("tool")
    private Tool tool;

    @Field("difficulty")
    private Difficulty difficulty;

    @DBRef
    @Field("precondition")
    @JsonIgnoreProperties(value = { "activity" }, allowSetters = true)
    private Set<Precondition> preconditions = new HashSet<>();

    @DBRef
    @Field("effect")
    @JsonIgnoreProperties(value = { "activity" }, allowSetters = true)
    private Set<Effect> effects = new HashSet<>();

    @DBRef
    @Field("concepts")
    @JsonIgnoreProperties(value = { "parents", "childs", "competences", "activities", "goals" }, allowSetters = true)
    private Set<Concept> concepts = new HashSet<>();

    @DBRef
    private Fragment fragment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Activity id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Activity title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Activity description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ActivityType getType() {
        return this.type;
    }

    public Activity type(ActivityType type) {
        this.setType(type);
        return this;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public Tool getTool() {
        return this.tool;
    }

    public Activity tool(Tool tool) {
        this.setTool(tool);
        return this;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public Activity difficulty(Difficulty difficulty) {
        this.setDifficulty(difficulty);
        return this;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Set<Precondition> getPreconditions() {
        return this.preconditions;
    }

    public void setPreconditions(Set<Precondition> preconditions) {
        if (this.preconditions != null) {
            this.preconditions.forEach(i -> i.setActivity(null));
        }
        if (preconditions != null) {
            preconditions.forEach(i -> i.setActivity(this));
        }
        this.preconditions = preconditions;
    }

    public Activity preconditions(Set<Precondition> preconditions) {
        this.setPreconditions(preconditions);
        return this;
    }

    public Activity addPrecondition(Precondition precondition) {
        this.preconditions.add(precondition);
        precondition.setActivity(this);
        return this;
    }

    public Activity removePrecondition(Precondition precondition) {
        this.preconditions.remove(precondition);
        precondition.setActivity(null);
        return this;
    }

    public Set<Effect> getEffects() {
        return this.effects;
    }

    public void setEffects(Set<Effect> effects) {
        if (this.effects != null) {
            this.effects.forEach(i -> i.setActivity(null));
        }
        if (effects != null) {
            effects.forEach(i -> i.setActivity(this));
        }
        this.effects = effects;
    }

    public Activity effects(Set<Effect> effects) {
        this.setEffects(effects);
        return this;
    }

    public Activity addEffect(Effect effect) {
        this.effects.add(effect);
        effect.setActivity(this);
        return this;
    }

    public Activity removeEffect(Effect effect) {
        this.effects.remove(effect);
        effect.setActivity(null);
        return this;
    }

    public Set<Concept> getConcepts() {
        return this.concepts;
    }

    public void setConcepts(Set<Concept> concepts) {
        this.concepts = concepts;
    }

    public Activity concepts(Set<Concept> concepts) {
        this.setConcepts(concepts);
        return this;
    }

    public Activity addConcept(Concept concept) {
        this.concepts.add(concept);
        concept.getActivities().add(this);
        return this;
    }

    public Activity removeConcept(Concept concept) {
        this.concepts.remove(concept);
        concept.getActivities().remove(this);
        return this;
    }

    public Fragment getFragment() {
        return this.fragment;
    }

    public void setFragment(Fragment fragment) {
        if (this.fragment != null) {
            this.fragment.setActivity(null);
        }
        if (fragment != null) {
            fragment.setActivity(this);
        }
        this.fragment = fragment;
    }

    public Activity fragment(Fragment fragment) {
        this.setFragment(fragment);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Activity)) {
            return false;
        }
        return id != null && id.equals(((Activity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Activity{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", tool='" + getTool() + "'" +
            ", difficulty='" + getDifficulty() + "'" +
            "}";
    }
}
