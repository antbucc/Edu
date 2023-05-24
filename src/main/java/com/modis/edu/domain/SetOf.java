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
    @Field("setOf")
    @JsonIgnoreProperties(
        value = { "activity", "setOf", "sequence", "abstractActivity", "members", "members", "modules" },
        allowSetters = true
    )
    private Set<Fragment> setOfs = new HashSet<>();

    @DBRef
    private Fragment fragment;

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

    public Set<Fragment> getSetOfs() {
        return this.setOfs;
    }

    public void setSetOfs(Set<Fragment> fragments) {
        if (this.setOfs != null) {
            this.setOfs.forEach(i -> i.setMembers(null));
        }
        if (fragments != null) {
            fragments.forEach(i -> i.setMembers(this));
        }
        this.setOfs = fragments;
    }

    public SetOf setOfs(Set<Fragment> fragments) {
        this.setSetOfs(fragments);
        return this;
    }

    public SetOf addSetOf(Fragment fragment) {
        this.setOfs.add(fragment);
        fragment.setMembers(this);
        return this;
    }

    public SetOf removeSetOf(Fragment fragment) {
        this.setOfs.remove(fragment);
        fragment.setMembers(null);
        return this;
    }

    public Fragment getFragment() {
        return this.fragment;
    }

    public void setFragment(Fragment fragment) {
        if (this.fragment != null) {
            this.fragment.setSetOf(null);
        }
        if (fragment != null) {
            fragment.setSetOf(this);
        }
        this.fragment = fragment;
    }

    public SetOf fragment(Fragment fragment) {
        this.setFragment(fragment);
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
