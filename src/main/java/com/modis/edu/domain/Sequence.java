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
    @Field("fragmemt")
    @JsonIgnoreProperties(value = { "sequence", "fragment" }, allowSetters = true)
    private Set<SequenceFragment> fragmemts = new HashSet<>();

    @DBRef
    @Field("fragments")
    @JsonIgnoreProperties(
        value = { "activity", "abstractActivities", "sequences", "setOfs", "sequences", "modules", "setOfs" },
        allowSetters = true
    )
    private Set<Fragment> fragments = new HashSet<>();

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

    public Set<SequenceFragment> getFragmemts() {
        return this.fragmemts;
    }

    public void setFragmemts(Set<SequenceFragment> sequenceFragments) {
        if (this.fragmemts != null) {
            this.fragmemts.forEach(i -> i.setSequence(null));
        }
        if (sequenceFragments != null) {
            sequenceFragments.forEach(i -> i.setSequence(this));
        }
        this.fragmemts = sequenceFragments;
    }

    public Sequence fragmemts(Set<SequenceFragment> sequenceFragments) {
        this.setFragmemts(sequenceFragments);
        return this;
    }

    public Sequence addFragmemt(SequenceFragment sequenceFragment) {
        this.fragmemts.add(sequenceFragment);
        sequenceFragment.setSequence(this);
        return this;
    }

    public Sequence removeFragmemt(SequenceFragment sequenceFragment) {
        this.fragmemts.remove(sequenceFragment);
        sequenceFragment.setSequence(null);
        return this;
    }

    public Set<Fragment> getFragments() {
        return this.fragments;
    }

    public void setFragments(Set<Fragment> fragments) {
        if (this.fragments != null) {
            this.fragments.forEach(i -> i.removeSequence(this));
        }
        if (fragments != null) {
            fragments.forEach(i -> i.addSequence(this));
        }
        this.fragments = fragments;
    }

    public Sequence fragments(Set<Fragment> fragments) {
        this.setFragments(fragments);
        return this;
    }

    public Sequence addFragment(Fragment fragment) {
        this.fragments.add(fragment);
        fragment.getSequences().add(this);
        return this;
    }

    public Sequence removeFragment(Fragment fragment) {
        this.fragments.remove(fragment);
        fragment.getSequences().remove(this);
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
