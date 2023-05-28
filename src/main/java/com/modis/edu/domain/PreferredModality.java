package com.modis.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.modis.edu.domain.enumeration.ModalityType;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A PreferredModality.
 */
@Document(collection = "preferred_modality")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PreferredModality implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("modality")
    private ModalityType modality;

    @DBRef
    @Field("educatorPreference")
    @JsonIgnoreProperties(value = { "educator" }, allowSetters = true)
    private EducatorPreference educatorPreference;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public PreferredModality id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ModalityType getModality() {
        return this.modality;
    }

    public PreferredModality modality(ModalityType modality) {
        this.setModality(modality);
        return this;
    }

    public void setModality(ModalityType modality) {
        this.modality = modality;
    }

    public EducatorPreference getEducatorPreference() {
        return this.educatorPreference;
    }

    public void setEducatorPreference(EducatorPreference educatorPreference) {
        this.educatorPreference = educatorPreference;
    }

    public PreferredModality educatorPreference(EducatorPreference educatorPreference) {
        this.setEducatorPreference(educatorPreference);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PreferredModality)) {
            return false;
        }
        return id != null && id.equals(((PreferredModality) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PreferredModality{" +
            "id=" + getId() +
            ", modality='" + getModality() + "'" +
            "}";
    }
}
