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
 * A PreferredActivity.
 */
@Document(collection = "preferred_activity")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PreferredActivity implements Serializable {

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
    @Field("preferences")
    @JsonIgnoreProperties(value = { "preferredActivities", "educator" }, allowSetters = true)
    private Set<EducatorPreference> preferences = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public PreferredActivity id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public PreferredActivity title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public PreferredActivity description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ActivityType getType() {
        return this.type;
    }

    public PreferredActivity type(ActivityType type) {
        this.setType(type);
        return this;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public Tool getTool() {
        return this.tool;
    }

    public PreferredActivity tool(Tool tool) {
        this.setTool(tool);
        return this;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public PreferredActivity difficulty(Difficulty difficulty) {
        this.setDifficulty(difficulty);
        return this;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Set<EducatorPreference> getPreferences() {
        return this.preferences;
    }

    public void setPreferences(Set<EducatorPreference> educatorPreferences) {
        if (this.preferences != null) {
            this.preferences.forEach(i -> i.removePreferredActivities(this));
        }
        if (educatorPreferences != null) {
            educatorPreferences.forEach(i -> i.addPreferredActivities(this));
        }
        this.preferences = educatorPreferences;
    }

    public PreferredActivity preferences(Set<EducatorPreference> educatorPreferences) {
        this.setPreferences(educatorPreferences);
        return this;
    }

    public PreferredActivity addPreferences(EducatorPreference educatorPreference) {
        this.preferences.add(educatorPreference);
        educatorPreference.getPreferredActivities().add(this);
        return this;
    }

    public PreferredActivity removePreferences(EducatorPreference educatorPreference) {
        this.preferences.remove(educatorPreference);
        educatorPreference.getPreferredActivities().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PreferredActivity)) {
            return false;
        }
        return id != null && id.equals(((PreferredActivity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PreferredActivity{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", tool='" + getTool() + "'" +
            ", difficulty='" + getDifficulty() + "'" +
            "}";
    }
}
