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
 * A Educator.
 */
@Document(collection = "educator")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Educator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName;

    @Field("email")
    private String email;

    @DBRef
    @Field("educatorPreference")
    @JsonIgnoreProperties(value = { "educator" }, allowSetters = true)
    private Set<EducatorPreference> educatorPreferences = new HashSet<>();

    @DBRef
    @Field("activity")
    @JsonIgnoreProperties(value = { "concepts", "preconditions", "effects", "fragment", "preferred" }, allowSetters = true)
    private Set<Activity> activities = new HashSet<>();

    @DBRef
    @Field("scenarios")
    @JsonIgnoreProperties(value = { "modules", "educators", "competences", "learners", "domain" }, allowSetters = true)
    private Set<Scenario> scenarios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Educator id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Educator firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Educator lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public Educator email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<EducatorPreference> getEducatorPreferences() {
        return this.educatorPreferences;
    }

    public void setEducatorPreferences(Set<EducatorPreference> educatorPreferences) {
        if (this.educatorPreferences != null) {
            this.educatorPreferences.forEach(i -> i.setEducator(null));
        }
        if (educatorPreferences != null) {
            educatorPreferences.forEach(i -> i.setEducator(this));
        }
        this.educatorPreferences = educatorPreferences;
    }

    public Educator educatorPreferences(Set<EducatorPreference> educatorPreferences) {
        this.setEducatorPreferences(educatorPreferences);
        return this;
    }

    public Educator addEducatorPreference(EducatorPreference educatorPreference) {
        this.educatorPreferences.add(educatorPreference);
        educatorPreference.setEducator(this);
        return this;
    }

    public Educator removeEducatorPreference(EducatorPreference educatorPreference) {
        this.educatorPreferences.remove(educatorPreference);
        educatorPreference.setEducator(null);
        return this;
    }

    public Set<Activity> getActivities() {
        return this.activities;
    }

    public void setActivities(Set<Activity> activities) {
        if (this.activities != null) {
            this.activities.forEach(i -> i.setPreferred(null));
        }
        if (activities != null) {
            activities.forEach(i -> i.setPreferred(this));
        }
        this.activities = activities;
    }

    public Educator activities(Set<Activity> activities) {
        this.setActivities(activities);
        return this;
    }

    public Educator addActivity(Activity activity) {
        this.activities.add(activity);
        activity.setPreferred(this);
        return this;
    }

    public Educator removeActivity(Activity activity) {
        this.activities.remove(activity);
        activity.setPreferred(null);
        return this;
    }

    public Set<Scenario> getScenarios() {
        return this.scenarios;
    }

    public void setScenarios(Set<Scenario> scenarios) {
        if (this.scenarios != null) {
            this.scenarios.forEach(i -> i.removeEducator(this));
        }
        if (scenarios != null) {
            scenarios.forEach(i -> i.addEducator(this));
        }
        this.scenarios = scenarios;
    }

    public Educator scenarios(Set<Scenario> scenarios) {
        this.setScenarios(scenarios);
        return this;
    }

    public Educator addScenario(Scenario scenario) {
        this.scenarios.add(scenario);
        scenario.getEducators().add(this);
        return this;
    }

    public Educator removeScenario(Scenario scenario) {
        this.scenarios.remove(scenario);
        scenario.getEducators().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Educator)) {
            return false;
        }
        return id != null && id.equals(((Educator) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Educator{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
