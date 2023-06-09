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
 * A Fragment.
 */
@Document(collection = "fragment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Fragment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("title")
    private String title;

    @NotNull
    @Field("order")
    private Integer order;

    @DBRef
    @Field("activity")
    private Activity activity;

    @DBRef
    @Field("abstractActivity")
    private AbstractActivity abstractActivity;

    @DBRef
    @Field("setOf")
    private SetOf setOf;

    @DBRef
    @Field("sequence")
    @JsonIgnoreProperties(value = { "orders", "fragments" }, allowSetters = true)
    private Sequence sequence;

    @DBRef
    @Field("module")
    @JsonIgnoreProperties(value = { "fragments", "scenario" }, allowSetters = true)
    private Module module;

    @DBRef
    @Field("setOf1s")
    @JsonIgnoreProperties(value = { "fragments", "fragment1" }, allowSetters = true)
    private Set<SetOf> setOf1s = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Fragment id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Fragment title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrder() {
        return this.order;
    }

    public Fragment order(Integer order) {
        this.setOrder(order);
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Fragment activity(Activity activity) {
        this.setActivity(activity);
        return this;
    }

    public AbstractActivity getAbstractActivity() {
        return this.abstractActivity;
    }

    public void setAbstractActivity(AbstractActivity abstractActivity) {
        this.abstractActivity = abstractActivity;
    }

    public Fragment abstractActivity(AbstractActivity abstractActivity) {
        this.setAbstractActivity(abstractActivity);
        return this;
    }

    public SetOf getSetOf() {
        return this.setOf;
    }

    public void setSetOf(SetOf setOf) {
        this.setOf = setOf;
    }

    public Fragment setOf(SetOf setOf) {
        this.setSetOf(setOf);
        return this;
    }

    public Sequence getSequence() {
        return this.sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public Fragment sequence(Sequence sequence) {
        this.setSequence(sequence);
        return this;
    }

    public Module getModule() {
        return this.module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Fragment module(Module module) {
        this.setModule(module);
        return this;
    }

    public Set<SetOf> getSetOf1s() {
        return this.setOf1s;
    }

    public void setSetOf1s(Set<SetOf> setOfs) {
        if (this.setOf1s != null) {
            this.setOf1s.forEach(i -> i.removeFragments(this));
        }
        if (setOfs != null) {
            setOfs.forEach(i -> i.addFragments(this));
        }
        this.setOf1s = setOfs;
    }

    public Fragment setOf1s(Set<SetOf> setOfs) {
        this.setSetOf1s(setOfs);
        return this;
    }

    public Fragment addSetOf1(SetOf setOf) {
        this.setOf1s.add(setOf);
        setOf.getFragments().add(this);
        return this;
    }

    public Fragment removeSetOf1(SetOf setOf) {
        this.setOf1s.remove(setOf);
        setOf.getFragments().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fragment)) {
            return false;
        }
        return id != null && id.equals(((Fragment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fragment{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", order=" + getOrder() +
            "}";
    }
}
