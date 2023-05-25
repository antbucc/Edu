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
    @JsonIgnoreProperties(value = { "goals", "fragments" }, allowSetters = true)
    private Set<AbstractActivity> abstractActivities = new HashSet<>();

    @DBRef
    @Field("sequences")
    @DBRef
    @Field("sequence")
    @JsonIgnoreProperties(value = { "fragmemts", "fragments" }, allowSetters = true)
    private Set<Sequence> sequences = new HashSet<>();

    @DBRef
    @Field("setOfs")
    @DBRef
    @Field("setOfs")
    @JsonIgnoreProperties(value = { "fragments", "fragments" }, allowSetters = true)
    private Set<SetOf> setOfs = new HashSet<>();

    @DBRef
    @Field("sequences")
    @DBRef
    @Field("sequence")
    @JsonIgnoreProperties(value = { "sequence", "fragment" }, allowSetters = true)
    private Set<SequenceFragment> sequences = new HashSet<>();

    @DBRef
    @Field("modules")
    @JsonIgnoreProperties(value = { "scenario", "fragments" }, allowSetters = true)
    private Set<Module> modules = new HashSet<>();

    @DBRef
    @Field("setOfs")
    @DBRef
    @Field("setOfs")
    @JsonIgnoreProperties(value = { "fragments", "fragments" }, allowSetters = true)
    private Set<SetOf> setOfs = new HashSet<>();

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

    public Set<Sequence> getSequences() {
        return this.sequences;
    }

    public void setSequences(Set<Sequence> sequences) {
        this.sequences = sequences;
    }

    public Fragment sequences(Set<Sequence> sequences) {
        this.setSequences(sequences);
        return this;
    }

    public Fragment addSequence(Sequence sequence) {
        this.sequences.add(sequence);
        sequence.getFragments().add(this);
        return this;
    }

    public Fragment removeSequence(Sequence sequence) {
        this.sequences.remove(sequence);
        sequence.getFragments().remove(this);
        return this;
    }

    public Set<SetOf> getSetOfs() {
        return this.setOfs;
    }

    public void setSetOfs(Set<SetOf> setOfs) {
        this.setOfs = setOfs;
    }

    public Fragment setOfs(Set<SetOf> setOfs) {
        this.setSetOfs(setOfs);
        return this;
    }

    public Fragment addSetOf(SetOf setOf) {
        this.setOfs.add(setOf);
        setOf.getFragments().add(this);
        return this;
    }

    public Fragment removeSetOf(SetOf setOf) {
        this.setOfs.remove(setOf);
        setOf.getFragments().remove(this);
        return this;
    }

    public Set<SequenceFragment> getSequences() {
        return this.sequences;
    }

    public void setSequences(Set<SequenceFragment> sequenceFragments) {
        if (this.sequences != null) {
            this.sequences.forEach(i -> i.setFragment(null));
        }
        if (sequenceFragments != null) {
            sequenceFragments.forEach(i -> i.setFragment(this));
        }
        this.sequences = sequenceFragments;
    }

    public Fragment sequences(Set<SequenceFragment> sequenceFragments) {
        this.setSequences(sequenceFragments);
        return this;
    }

    public Fragment addSequence(SequenceFragment sequenceFragment) {
        this.sequences.add(sequenceFragment);
        sequenceFragment.setFragment(this);
        return this;
    }

    public Fragment removeSequence(SequenceFragment sequenceFragment) {
        this.sequences.remove(sequenceFragment);
        sequenceFragment.setFragment(null);
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

    public Set<SetOf> getSetOfs() {
        return this.setOfs;
    }

    public void setSetOfs(Set<SetOf> setOfs) {
        if (this.setOfs != null) {
            this.setOfs.forEach(i -> i.removeFragment(this));
        }
        if (setOfs != null) {
            setOfs.forEach(i -> i.addFragment(this));
        }
        this.setOfs = setOfs;
    }

    public Fragment setOfs(Set<SetOf> setOfs) {
        this.setSetOfs(setOfs);
        return this;
    }

    public Fragment addSetOf(SetOf setOf) {
        this.setOfs.add(setOf);
        setOf.getFragments().add(this);
        return this;
    }

    public Fragment removeSetOf(SetOf setOf) {
        this.setOfs.remove(setOf);
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
