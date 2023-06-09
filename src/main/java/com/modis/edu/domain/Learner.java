package com.modis.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.modis.edu.domain.enumeration.GenderType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Learner.
 */
@Document(collection = "learner")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Learner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName;

    @Field("email")
    private String email;

    @Field("phone_number")
    private String phoneNumber;

    @Field("gender")
    private GenderType gender;

    @DBRef
    @Field("learnerPreference")
    @JsonIgnoreProperties(value = { "learner" }, allowSetters = true)
    private Set<LearnerPreference> learnerPreferences = new HashSet<>();

    @DBRef
    @Field("scenarios")
    @JsonIgnoreProperties(value = { "modules", "educators", "competences", "learners", "domain" }, allowSetters = true)
    private Set<Scenario> scenarios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Learner id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Learner firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Learner lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public Learner email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Learner phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public GenderType getGender() {
        return this.gender;
    }

    public Learner gender(GenderType gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public Set<LearnerPreference> getLearnerPreferences() {
        return this.learnerPreferences;
    }

    public void setLearnerPreferences(Set<LearnerPreference> learnerPreferences) {
        if (this.learnerPreferences != null) {
            this.learnerPreferences.forEach(i -> i.setLearner(null));
        }
        if (learnerPreferences != null) {
            learnerPreferences.forEach(i -> i.setLearner(this));
        }
        this.learnerPreferences = learnerPreferences;
    }

    public Learner learnerPreferences(Set<LearnerPreference> learnerPreferences) {
        this.setLearnerPreferences(learnerPreferences);
        return this;
    }

    public Learner addLearnerPreference(LearnerPreference learnerPreference) {
        this.learnerPreferences.add(learnerPreference);
        learnerPreference.setLearner(this);
        return this;
    }

    public Learner removeLearnerPreference(LearnerPreference learnerPreference) {
        this.learnerPreferences.remove(learnerPreference);
        learnerPreference.setLearner(null);
        return this;
    }

    public Set<Scenario> getScenarios() {
        return this.scenarios;
    }

    public void setScenarios(Set<Scenario> scenarios) {
        if (this.scenarios != null) {
            this.scenarios.forEach(i -> i.removeLearner(this));
        }
        if (scenarios != null) {
            scenarios.forEach(i -> i.addLearner(this));
        }
        this.scenarios = scenarios;
    }

    public Learner scenarios(Set<Scenario> scenarios) {
        this.setScenarios(scenarios);
        return this;
    }

    public Learner addScenario(Scenario scenario) {
        this.scenarios.add(scenario);
        scenario.getLearners().add(this);
        return this;
    }

    public Learner removeScenario(Scenario scenario) {
        this.scenarios.remove(scenario);
        scenario.getLearners().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Learner)) {
            return false;
        }
        return id != null && id.equals(((Learner) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Learner{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", gender='" + getGender() + "'" +
            "}";
    }
}
