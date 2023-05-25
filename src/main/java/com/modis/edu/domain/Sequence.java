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
 * A Sequence.
 */
@Document(collection = "sequence")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sequence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("title")
    private String title;

    @DBRef
    @DBRef
    @Field("fragments")
    private Fragment fragment;

    @DBRef
    @DBRef
    @Field("fragments")
    @JsonIgnoreProperties(value = { "sequences", "fragments" }, allowSetters = true)
    private Set<SequenceFragment> fragments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Sequence id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Sequence title(String title) {
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
        if (this.fragment != null) {
            this.fragment.setSequence(null);
        }
        if (fragment != null) {
            fragment.setSequence(this);
        }
        this.fragment = fragment;
    }

    public Sequence fragment(Fragment fragment) {
        this.setFragment(fragment);
        return this;
    }

    public Set<SequenceFragment> getFragments() {
        return this.fragments;
    }

    public void setFragments(Set<SequenceFragment> sequenceFragments) {
        if (this.fragments != null) {
            this.fragments.forEach(i -> i.removeSequence(this));
        }
        if (sequenceFragments != null) {
            sequenceFragments.forEach(i -> i.addSequence(this));
        }
        this.fragments = sequenceFragments;
    }

    public Sequence fragments(Set<SequenceFragment> sequenceFragments) {
        this.setFragments(sequenceFragments);
        return this;
    }

    public Sequence addFragment(SequenceFragment sequenceFragment) {
        this.fragments.add(sequenceFragment);
        sequenceFragment.getSequences().add(this);
        return this;
    }

    public Sequence removeFragment(SequenceFragment sequenceFragment) {
        this.fragments.remove(sequenceFragment);
        sequenceFragment.getSequences().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sequence)) {
            return false;
        }
        return id != null && id.equals(((Sequence) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sequence{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
