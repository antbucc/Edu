package com.modis.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.modis.edu.domain.enumeration.Difficulty;
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

    @Field("subject")
    private String subject;

    @Field("difficulty")
    private Difficulty difficulty;

    @DBRef
    @Field("preferredTopics")
    @JsonIgnoreProperties(value = { "educatorPreference" }, allowSetters = true)
    private Set<PreferredTopic> preferredTopics = new HashSet<>();

    @DBRef
    @Field("preferredActivities")
    @JsonIgnoreProperties(value = { "educatorPreference" }, allowSetters = true)
    private Set<PreferredActivity> preferredActivities = new HashSet<>();

    @DBRef
    @Field("preferredModalities")
    @JsonIgnoreProperties(value = { "educatorPreference" }, allowSetters = true)
    private Set<PreferredModality> preferredModalities = new HashSet<>();

    @DBRef
    @Field("preferredLearningStyles")
    @JsonIgnoreProperties(value = { "educatorPreference" }, allowSetters = true)
    private Set<PreferredLearningStyle> preferredLearningStyles = new HashSet<>();

    @DBRef
    @Field("eductarPreferences")
    @JsonIgnoreProperties(value = { "preferences", "scenarios" }, allowSetters = true)
    private Educator eductarPreferences;

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

    public String getSubject() {
        return this.subject;
    }

    public EducatorPreference subject(String subject) {
        this.setSubject(subject);
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public Set<PreferredTopic> getPreferredTopics() {
        return this.preferredTopics;
    }

    public void setPreferredTopics(Set<PreferredTopic> preferredTopics) {
        if (this.preferredTopics != null) {
            this.preferredTopics.forEach(i -> i.setEducatorPreference(null));
        }
        if (preferredTopics != null) {
            preferredTopics.forEach(i -> i.setEducatorPreference(this));
        }
        this.preferredTopics = preferredTopics;
    }

    public EducatorPreference preferredTopics(Set<PreferredTopic> preferredTopics) {
        this.setPreferredTopics(preferredTopics);
        return this;
    }

    public EducatorPreference addPreferredTopics(PreferredTopic preferredTopic) {
        this.preferredTopics.add(preferredTopic);
        preferredTopic.setEducatorPreference(this);
        return this;
    }

    public EducatorPreference removePreferredTopics(PreferredTopic preferredTopic) {
        this.preferredTopics.remove(preferredTopic);
        preferredTopic.setEducatorPreference(null);
        return this;
    }

    public Set<PreferredActivity> getPreferredActivities() {
        return this.preferredActivities;
    }

    public void setPreferredActivities(Set<PreferredActivity> preferredActivities) {
        if (this.preferredActivities != null) {
            this.preferredActivities.forEach(i -> i.setEducatorPreference(null));
        }
        if (preferredActivities != null) {
            preferredActivities.forEach(i -> i.setEducatorPreference(this));
        }
        this.preferredActivities = preferredActivities;
    }

    public EducatorPreference preferredActivities(Set<PreferredActivity> preferredActivities) {
        this.setPreferredActivities(preferredActivities);
        return this;
    }

    public EducatorPreference addPreferredActivities(PreferredActivity preferredActivity) {
        this.preferredActivities.add(preferredActivity);
        preferredActivity.setEducatorPreference(this);
        return this;
    }

    public EducatorPreference removePreferredActivities(PreferredActivity preferredActivity) {
        this.preferredActivities.remove(preferredActivity);
        preferredActivity.setEducatorPreference(null);
        return this;
    }

    public Set<PreferredModality> getPreferredModalities() {
        return this.preferredModalities;
    }

    public void setPreferredModalities(Set<PreferredModality> preferredModalities) {
        if (this.preferredModalities != null) {
            this.preferredModalities.forEach(i -> i.setEducatorPreference(null));
        }
        if (preferredModalities != null) {
            preferredModalities.forEach(i -> i.setEducatorPreference(this));
        }
        this.preferredModalities = preferredModalities;
    }

    public EducatorPreference preferredModalities(Set<PreferredModality> preferredModalities) {
        this.setPreferredModalities(preferredModalities);
        return this;
    }

    public EducatorPreference addPreferredModalities(PreferredModality preferredModality) {
        this.preferredModalities.add(preferredModality);
        preferredModality.setEducatorPreference(this);
        return this;
    }

    public EducatorPreference removePreferredModalities(PreferredModality preferredModality) {
        this.preferredModalities.remove(preferredModality);
        preferredModality.setEducatorPreference(null);
        return this;
    }

    public Set<PreferredLearningStyle> getPreferredLearningStyles() {
        return this.preferredLearningStyles;
    }

    public void setPreferredLearningStyles(Set<PreferredLearningStyle> preferredLearningStyles) {
        if (this.preferredLearningStyles != null) {
            this.preferredLearningStyles.forEach(i -> i.setEducatorPreference(null));
        }
        if (preferredLearningStyles != null) {
            preferredLearningStyles.forEach(i -> i.setEducatorPreference(this));
        }
        this.preferredLearningStyles = preferredLearningStyles;
    }

    public EducatorPreference preferredLearningStyles(Set<PreferredLearningStyle> preferredLearningStyles) {
        this.setPreferredLearningStyles(preferredLearningStyles);
        return this;
    }

    public EducatorPreference addPreferredLearningStyles(PreferredLearningStyle preferredLearningStyle) {
        this.preferredLearningStyles.add(preferredLearningStyle);
        preferredLearningStyle.setEducatorPreference(this);
        return this;
    }

    public EducatorPreference removePreferredLearningStyles(PreferredLearningStyle preferredLearningStyle) {
        this.preferredLearningStyles.remove(preferredLearningStyle);
        preferredLearningStyle.setEducatorPreference(null);
        return this;
    }

    public Educator getEductarPreferences() {
        return this.eductarPreferences;
    }

    public void setEductarPreferences(Educator educator) {
        this.eductarPreferences = educator;
    }

    public EducatorPreference eductarPreferences(Educator educator) {
        this.setEductarPreferences(educator);
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
            ", subject='" + getSubject() + "'" +
            ", difficulty='" + getDifficulty() + "'" +
            "}";
    }
}
