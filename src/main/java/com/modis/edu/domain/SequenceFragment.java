package com.modis.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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
    @Field("sequence")
    @JsonIgnoreProperties(value = { "fragments" }, allowSetters = true)
    private Sequence sequence;

    @DBRef
    @Field("fragment")
    @JsonIgnoreProperties(value = { "activity", "abstractActivity", "sequences", "modules", "setOfs" }, allowSetters = true)
    private Fragment fragment;

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

    public Sequence getSequence() {
        return this.sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public SequenceFragment sequence(Sequence sequence) {
        this.setSequence(sequence);
        return this;
    }

    public Fragment getFragment() {
        return this.fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public SequenceFragment fragment(Fragment fragment) {
        this.setFragment(fragment);
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
