package com.modis.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A FragmentOrder.
 */
@Document(collection = "fragment_order")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FragmentOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("order")
    private Integer order;

    @DBRef
    @Field("fragment")
    @JsonIgnoreProperties(value = { "activity", "abstractActivity", "setOf", "sequence", "modules" }, allowSetters = true)
    private Fragment fragment;

    @DBRef
    @Field("set")
    @JsonIgnoreProperties(value = { "fragments" }, allowSetters = true)
    private FragmentSet set;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public FragmentOrder id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getOrder() {
        return this.order;
    }

    public FragmentOrder order(Integer order) {
        this.setOrder(order);
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Fragment getFragment() {
        return this.fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public FragmentOrder fragment(Fragment fragment) {
        this.setFragment(fragment);
        return this;
    }

    public FragmentSet getSet() {
        return this.set;
    }

    public void setSet(FragmentSet fragmentSet) {
        this.set = fragmentSet;
    }

    public FragmentOrder set(FragmentSet fragmentSet) {
        this.setSet(fragmentSet);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FragmentOrder)) {
            return false;
        }
        return id != null && id.equals(((FragmentOrder) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FragmentOrder{" +
            "id=" + getId() +
            ", order=" + getOrder() +
            "}";
    }
}
