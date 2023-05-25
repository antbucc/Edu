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
 * A SetOf.
 */
@Document(collection = "set_of")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SetOf implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @DBRef
    @Field("fragments")
    @JsonIgnoreProperties(value = { "activity", "abstractActivities", "sequences", "setOfs", "modules", "setofs" }, allowSetters = true)
    private Set<Fragment> fragments = new HashSet<>();

    @DBRef
    @Field("fragmemt")
    @JsonIgnoreProperties(value = { "setOf", "fragment" }, allowSetters = true)
    private Set<SetOfFragment> fragmemts = new HashSet<>();

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
        this.fragments = fragments;
    }

    public SetOf fragments(Set<Fragment> fragments) {
        this.setFragments(fragments);
        return this;
    }

    public SetOf addFragments(Fragment fragment) {
        this.fragments.add(fragment);
        fragment.getSetofs().add(this);
        return this;
    }

    public SetOf removeFragments(Fragment fragment) {
        this.fragments.remove(fragment);
        fragment.getSetofs().remove(this);
        return this;
    }

    public Set<SetOfFragment> getFragmemts() {
        return this.fragmemts;
    }

    public void setFragmemts(Set<SetOfFragment> setOfFragments) {
        if (this.fragmemts != null) {
            this.fragmemts.forEach(i -> i.setSetOf(null));
        }
        if (setOfFragments != null) {
            setOfFragments.forEach(i -> i.setSetOf(this));
        }
        this.fragmemts = setOfFragments;
    }

    public SetOf fragmemts(Set<SetOfFragment> setOfFragments) {
        this.setFragmemts(setOfFragments);
        return this;
    }

    public SetOf addFragmemt(SetOfFragment setOfFragment) {
        this.fragmemts.add(setOfFragment);
        setOfFragment.setSetOf(this);
        return this;
    }

    public SetOf removeFragmemt(SetOfFragment setOfFragment) {
        this.fragmemts.remove(setOfFragment);
        setOfFragment.setSetOf(null);
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
