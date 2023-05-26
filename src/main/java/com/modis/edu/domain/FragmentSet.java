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
 * A FragmentSet.
 */
@Document(collection = "fragment_set")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FragmentSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @DBRef
    @Field("fragments")
    @JsonIgnoreProperties(value = { "fragment", "set" }, allowSetters = true)
    private Set<FragmentOrder> fragments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public FragmentSet id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<FragmentOrder> getFragments() {
        return this.fragments;
    }

    public void setFragments(Set<FragmentOrder> fragmentOrders) {
        if (this.fragments != null) {
            this.fragments.forEach(i -> i.setSet(null));
        }
        if (fragmentOrders != null) {
            fragmentOrders.forEach(i -> i.setSet(this));
        }
        this.fragments = fragmentOrders;
    }

    public FragmentSet fragments(Set<FragmentOrder> fragmentOrders) {
        this.setFragments(fragmentOrders);
        return this;
    }

    public FragmentSet addFragments(FragmentOrder fragmentOrder) {
        this.fragments.add(fragmentOrder);
        fragmentOrder.setSet(this);
        return this;
    }

    public FragmentSet removeFragments(FragmentOrder fragmentOrder) {
        this.fragments.remove(fragmentOrder);
        fragmentOrder.setSet(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FragmentSet)) {
            return false;
        }
        return id != null && id.equals(((FragmentSet) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FragmentSet{" +
            "id=" + getId() +
            "}";
    }
}
