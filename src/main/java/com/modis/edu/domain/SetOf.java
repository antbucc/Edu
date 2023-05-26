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
    private Set<Fragment> fragments = new HashSet<>();

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

    public Set<Fragment> getFragments() {
        return this.fragments;
    }

    public void setFragments(Set<Fragment> fragments) {
        if (this.fragments != null) {
            this.fragments.forEach(i -> i.setSetOf(null));
        }
        if (fragments != null) {
            fragments.forEach(i -> i.setSetOf(this));
        }
        this.fragments = fragments;
    }

    public SetOf fragments(Set<Fragment> fragments) {
        this.setFragments(fragments);
        return this;
    }

    public SetOf addFragment(Fragment fragment) {
        this.fragments.add(fragment);
        fragment.setSetOf(this);
        return this;
    }

    public SetOf removeFragment(Fragment fragment) {
        this.fragments.remove(fragment);
        fragment.setSetOf(null);
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
