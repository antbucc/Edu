package com.modis.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;
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

    @NotNull
    @Field("title")
    private String title;

    @DBRef
    @Field("goals")
    @JsonIgnoreProperties(value = { "concepts", "abstractActivities" }, allowSetters = true)
    private Set<Goal> goals = new HashSet<>();

    @DBRef
    private Fragment fragment;

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

    public Set<Goal> getGoals() {
        return this.goals;
    }

    public void setGoals(Set<Goal> goals) {
        this.goals = goals;
    }

    public AbstractActivity goals(Set<Goal> goals) {
        this.setGoals(goals);
        return this;
    }

    public AbstractActivity addGoal(Goal goal) {
        this.goals.add(goal);
        goal.getAbstractActivities().add(this);
        return this;
    }

    public AbstractActivity removeGoal(Goal goal) {
        this.goals.remove(goal);
        goal.getAbstractActivities().remove(this);
        return this;
    }

    public Fragment getFragment() {
        return this.fragment;
    }

    public void setFragment(Fragment fragment) {
        if (this.fragment != null) {
            this.fragment.setAbstractActivity(null);
        }
        if (fragment != null) {
            fragment.setAbstractActivity(this);
        }
        this.fragment = fragment;
    }

    public AbstractActivity fragment(Fragment fragment) {
        this.setFragment(fragment);
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
