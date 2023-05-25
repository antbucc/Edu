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
 * A Fragment.
 */
@Document(collection = "fragment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Fragment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @DBRef
    @Field("activity")
    private Activity activity;

    @DBRef
    @Field("abstractActivities")
    @JsonIgnoreProperties(value = { "fragments" }, allowSetters = true)
    private Set<AbstractActivity> abstractActivities = new HashSet<>();

    @DBRef
    @Field("modules")
    @JsonIgnoreProperties(value = { "scenario", "fragments" }, allowSetters = true)
    private Set<Module> modules = new HashSet<>();

    @DBRef
    @Field("setofs")
    @JsonIgnoreProperties(value = { "fragments" }, allowSetters = true)
    private Set<SetOf> setofs = new HashSet<>();

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

    public Set<AbstractActivity> getAbstractActivities() {
        return this.abstractActivities;
    }

    public void setAbstractActivities(Set<AbstractActivity> abstractActivities) {
        this.abstractActivities = abstractActivities;
    }

    public Fragment abstractActivities(Set<AbstractActivity> abstractActivities) {
        this.setAbstractActivities(abstractActivities);
        return this;
    }

    public Fragment addAbstractActivity(AbstractActivity abstractActivity) {
        this.abstractActivities.add(abstractActivity);
        abstractActivity.getFragments().add(this);
        return this;
    }

    public Fragment removeAbstractActivity(AbstractActivity abstractActivity) {
        this.abstractActivities.remove(abstractActivity);
        abstractActivity.getFragments().remove(this);
        return this;
    }

    public Set<Module> getModules() {
        return this.modules;
    }

    public void setModules(Set<Module> modules) {
        if (this.modules != null) {
            this.modules.forEach(i -> i.removeFragment(this));
        }
        if (modules != null) {
            modules.forEach(i -> i.addFragment(this));
        }
        this.modules = modules;
    }

    public Fragment modules(Set<Module> modules) {
        this.setModules(modules);
        return this;
    }

    public Fragment addModule(Module module) {
        this.modules.add(module);
        module.getFragments().add(this);
        return this;
    }

    public Fragment removeModule(Module module) {
        this.modules.remove(module);
        module.getFragments().remove(this);
        return this;
    }

    public Set<SetOf> getSetofs() {
        return this.setofs;
    }

    public void setSetofs(Set<SetOf> setOfs) {
        if (this.setofs != null) {
            this.setofs.forEach(i -> i.removeFragments(this));
        }
        if (setOfs != null) {
            setOfs.forEach(i -> i.addFragments(this));
        }
        this.setofs = setOfs;
    }

    public Fragment setofs(Set<SetOf> setOfs) {
        this.setSetofs(setOfs);
        return this;
    }

    public Fragment addSetof(SetOf setOf) {
        this.setofs.add(setOf);
        setOf.getFragments().add(this);
        return this;
    }

    public Fragment removeSetof(SetOf setOf) {
        this.setofs.remove(setOf);
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
