package com.modis.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.modis.edu.domain.enumeration.ActivityType;
import java.io.Serializable;
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

    @Field("activity")
    private ActivityType activity;

    @DBRef
    @Field("educatorPreference")
    @JsonIgnoreProperties(
        value = { "preferredTopics", "preferredActivities", "preferredModalities", "preferredLearningStyles", "eductarPreferences" },
        allowSetters = true
    )
    private EducatorPreference educatorPreference;

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

    public ActivityType getActivity() {
        return this.activity;
    }

    public PreferredActivity activity(ActivityType activity) {
        this.setActivity(activity);
        return this;
    }

    public void setActivity(ActivityType activity) {
        this.activity = activity;
    }

    public EducatorPreference getEducatorPreference() {
        return this.educatorPreference;
    }

    public void setEducatorPreference(EducatorPreference educatorPreference) {
        this.educatorPreference = educatorPreference;
    }

    public PreferredActivity educatorPreference(EducatorPreference educatorPreference) {
        this.setEducatorPreference(educatorPreference);
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
            ", activity='" + getActivity() + "'" +
            "}";
    }
}
