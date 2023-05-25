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
 * A SequenceFragment.
 */
@Document(collection = "sequence_fragment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SequenceFragment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("order")
    private Integer order;

    @DBRef
    @Field("sequences")
    @JsonIgnoreProperties(value = { "fragment", "fragments" }, allowSetters = true)
    private Set<Sequence> sequences = new HashSet<>();

    @DBRef
    @Field("fragments")
    @JsonIgnoreProperties(value = { "activity", "abstractActivity", "sequence", "setOf", "modules", "sequences" }, allowSetters = true)
    private Set<Fragment> fragments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public SequenceFragment id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getOrder() {
        return this.order;
    }

    public SequenceFragment order(Integer order) {
        this.setOrder(order);
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Set<Sequence> getSequences() {
        return this.sequences;
    }

    public void setSequences(Set<Sequence> sequences) {
        this.sequences = sequences;
    }

    public SequenceFragment sequences(Set<Sequence> sequences) {
        this.setSequences(sequences);
        return this;
    }

    public SequenceFragment addSequence(Sequence sequence) {
        this.sequences.add(sequence);
        sequence.getFragments().add(this);
        return this;
    }

    public SequenceFragment removeSequence(Sequence sequence) {
        this.sequences.remove(sequence);
        sequence.getFragments().remove(this);
        return this;
    }

    public Set<Fragment> getFragments() {
        return this.fragments;
    }

    public void setFragments(Set<Fragment> fragments) {
        this.fragments = fragments;
    }

    public SequenceFragment fragments(Set<Fragment> fragments) {
        this.setFragments(fragments);
        return this;
    }

    public SequenceFragment addFragment(Fragment fragment) {
        this.fragments.add(fragment);
        fragment.getSequences().add(this);
        return this;
    }

    public SequenceFragment removeFragment(Fragment fragment) {
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
        if (!(o instanceof SequenceFragment)) {
            return false;
        }
        return id != null && id.equals(((SequenceFragment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SequenceFragment{" +
            "id=" + getId() +
            ", order=" + getOrder() +
            "}";
    }
}
