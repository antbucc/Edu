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

    @DBRef
    @Field("order")
    private Order order;

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
    @Field("module1s")
    @JsonIgnoreProperties(value = { "scenario", "fragments" }, allowSetters = true)
    private Set<Module1> module1s = new HashSet<>();

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

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Fragment order(Order order) {
        this.setOrder(order);
        return this;
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

    public Set<Module1> getModule1s() {
        return this.module1s;
    }

    public void setModule1s(Set<Module1> module1s) {
        if (this.module1s != null) {
            this.module1s.forEach(i -> i.removeFragment(this));
        }
        if (module1s != null) {
            module1s.forEach(i -> i.addFragment(this));
        }
        this.module1s = module1s;
    }

    public Fragment module1s(Set<Module1> module1s) {
        this.setModule1s(module1s);
        return this;
    }

    public Fragment addModule1(Module1 module1) {
        this.module1s.add(module1);
        module1.getFragments().add(this);
        return this;
    }

    public Fragment removeModule1(Module1 module1) {
        this.module1s.remove(module1);
        module1.getFragments().remove(this);
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
            "}";
    }
}
