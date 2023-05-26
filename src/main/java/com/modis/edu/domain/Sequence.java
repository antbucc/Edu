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
    @Field("fragments")
    @JsonIgnoreProperties(value = { "fragment", "sequence" }, allowSetters = true)
    private Set<Order> fragments = new HashSet<>();

    @DBRef
    private Fragment fragment;

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

    public Set<Order> getFragments() {
        return this.fragments;
    }

    public void setFragments(Set<Order> orders) {
        if (this.fragments != null) {
            this.fragments.forEach(i -> i.setSequence(null));
        }
        if (orders != null) {
            orders.forEach(i -> i.setSequence(this));
        }
        this.fragments = orders;
    }

    public Sequence fragments(Set<Order> orders) {
        this.setFragments(orders);
        return this;
    }

    public Sequence addFragments(Order order) {
        this.fragments.add(order);
        order.setSequence(this);
        return this;
    }

    public Sequence removeFragments(Order order) {
        this.fragments.remove(order);
        order.setSequence(null);
        return this;
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
