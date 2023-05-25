package com.modis.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A SetOfFragment.
 */
@Document(collection = "set_of_fragment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SetOfFragment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("order")
    private Integer order;

    @DBRef
    @Field("setOf")
    @JsonIgnoreProperties(value = { "fragments", "fragments" }, allowSetters = true)
    private SetOf setOf;

    @DBRef
    @Field("fragment")
    @JsonIgnoreProperties(
        value = { "activity", "abstractActivities", "sequences", "setOfs", "sequences", "modules", "setOfs" },
        allowSetters = true
    )
    private Fragment fragment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public SetOfFragment id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getOrder() {
        return this.order;
    }

    public SetOfFragment order(Integer order) {
        this.setOrder(order);
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public SetOf getSetOf() {
        return this.setOf;
    }

    public void setSetOf(SetOf setOf) {
        this.setOf = setOf;
    }

    public SetOfFragment setOf(SetOf setOf) {
        this.setSetOf(setOf);
        return this;
    }

    public Fragment getFragment() {
        return this.fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public SetOfFragment fragment(Fragment fragment) {
        this.setFragment(fragment);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SetOfFragment)) {
            return false;
        }
        return id != null && id.equals(((SetOfFragment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SetOfFragment{" +
            "id=" + getId() +
            ", order=" + getOrder() +
            "}";
    }
}
