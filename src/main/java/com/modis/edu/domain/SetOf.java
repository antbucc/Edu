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

    @Field("at_least")
    private Integer atLeast;

    @Field("no_less")
    private Integer noLess;

    @DBRef
    @Field("fragments")
    @JsonIgnoreProperties(value = { "activity", "abstractActivity", "setOf", "sequence", "module", "setOf1s" }, allowSetters = true)
    private Set<Fragment> fragments = new HashSet<>();

    @DBRef
    private Fragment fragment1;

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

    public Integer getAtLeast() {
        return this.atLeast;
    }

    public SetOf atLeast(Integer atLeast) {
        this.setAtLeast(atLeast);
        return this;
    }

    public void setAtLeast(Integer atLeast) {
        this.atLeast = atLeast;
    }

    public Integer getNoLess() {
        return this.noLess;
    }

    public SetOf noLess(Integer noLess) {
        this.setNoLess(noLess);
        return this;
    }

    public void setNoLess(Integer noLess) {
        this.noLess = noLess;
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
        fragment.getSetOf1s().add(this);
        return this;
    }

    public SetOf removeFragments(Fragment fragment) {
        this.fragments.remove(fragment);
        fragment.getSetOf1s().remove(this);
        return this;
    }

    public Fragment getFragment1() {
        return this.fragment1;
    }

    public void setFragment1(Fragment fragment) {
        if (this.fragment1 != null) {
            this.fragment1.setSetOf(null);
        }
        if (fragment != null) {
            fragment.setSetOf(this);
        }
        this.fragment1 = fragment;
    }

    public SetOf fragment1(Fragment fragment) {
        this.setFragment1(fragment);
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
            ", atLeast=" + getAtLeast() +
            ", noLess=" + getNoLess() +
            "}";
    }
}
