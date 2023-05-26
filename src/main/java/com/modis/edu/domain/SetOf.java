package com.modis.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A SetOf.
 */
@Document(collection = "set_of")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SetOf implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("title")
    private String title;

    @DBRef
    @Field("fragment")
    @JsonIgnoreProperties(value = { "activity", "abstractActivity", "setOf", "sequence", "modules" }, allowSetters = true)
    private Fragment fragment;

    @DBRef
    private Fragment setOff;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public SetOf id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public SetOf title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return this.fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public SetOf fragment(Fragment fragment) {
        this.setFragment(fragment);
        return this;
    }

    public Fragment getSetOff() {
        return this.setOff;
    }

    public void setSetOff(Fragment fragment) {
        if (this.setOff != null) {
            this.setOff.setSetOf(null);
        }
        if (fragment != null) {
            fragment.setSetOf(this);
        }
        this.setOff = fragment;
    }

    public SetOf setOff(Fragment fragment) {
        this.setSetOff(fragment);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SetOf)) {
            return false;
        }
        return id != null && id.equals(((SetOf) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SetOf{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
