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
 * A AbstractActivity.
 */
@Document(collection = "abstract_activity")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AbstractActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @DBRef
    @Field("activity")
    @JsonIgnoreProperties(value = { "concepts", "goals" }, allowSetters = true)
    private Set<Goal> activities = new HashSet<>();

    @DBRef
    @Field("fragments")
    @JsonIgnoreProperties(value = { "activity", "abstractActivities", "sequences", "modules", "setOfs" }, allowSetters = true)
    private Set<Fragment> fragments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public AbstractActivity id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public AbstractActivity title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Goal> getActivities() {
        return this.activities;
    }

    public void setActivities(Set<Goal> goals) {
        if (this.activities != null) {
            this.activities.forEach(i -> i.setGoals(null));
        }
        if (goals != null) {
            goals.forEach(i -> i.setGoals(this));
        }
        this.activities = goals;
    }

    public AbstractActivity activities(Set<Goal> goals) {
        this.setActivities(goals);
        return this;
    }

    public AbstractActivity addActivity(Goal goal) {
        this.activities.add(goal);
        goal.setGoals(this);
        return this;
    }

    public AbstractActivity removeActivity(Goal goal) {
        this.activities.remove(goal);
        goal.setGoals(null);
        return this;
    }

    public Set<Fragment> getFragments() {
        return this.fragments;
    }

    public void setFragments(Set<Fragment> fragments) {
        if (this.fragments != null) {
            this.fragments.forEach(i -> i.removeAbstractActivity(this));
        }
        if (fragments != null) {
            fragments.forEach(i -> i.addAbstractActivity(this));
        }
        this.fragments = fragments;
    }

    public AbstractActivity fragments(Set<Fragment> fragments) {
        this.setFragments(fragments);
        return this;
    }

    public AbstractActivity addFragment(Fragment fragment) {
        this.fragments.add(fragment);
        fragment.getAbstractActivities().add(this);
        return this;
    }

    public AbstractActivity removeFragment(Fragment fragment) {
        this.fragments.remove(fragment);
        fragment.getAbstractActivities().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractActivity)) {
            return false;
        }
        return id != null && id.equals(((AbstractActivity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AbstractActivity{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
