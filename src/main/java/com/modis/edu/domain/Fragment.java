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
    @Field("abstractActivity")
    private AbstractActivity abstractActivity;

    @DBRef
    @Field("sequence")
    @DBRef
    @Field("sequences")
    private Sequence sequence;

    @DBRef
    @Field("modules")
    @JsonIgnoreProperties(value = { "scenario", "fragments" }, allowSetters = true)
    private Set<Module> modules = new HashSet<>();

    @DBRef
    @Field("setOfs")
    @JsonIgnoreProperties(value = { "fragments" }, allowSetters = true)
    private Set<SetOf> setOfs = new HashSet<>();

    @DBRef
    @Field("sequence")
    @DBRef
    @Field("sequences")
    @JsonIgnoreProperties(value = { "sequences", "fragments" }, allowSetters = true)
    private Set<SequenceFragment> sequences = new HashSet<>();

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

    public Set<SequenceFragment> getSequences() {
        return this.sequences;
    }

    public void setSequences(Set<SequenceFragment> sequenceFragments) {
        if (this.sequences != null) {
            this.sequences.forEach(i -> i.removeFragment(this));
        }
        if (sequenceFragments != null) {
            sequenceFragments.forEach(i -> i.addFragment(this));
        }
        this.sequences = sequenceFragments;
    }

    public Fragment sequences(Set<SequenceFragment> sequenceFragments) {
        this.setSequences(sequenceFragments);
        return this;
    }

    public Fragment addSequence(SequenceFragment sequenceFragment) {
        this.sequences.add(sequenceFragment);
        sequenceFragment.getFragments().add(this);
        return this;
    }

    public Fragment removeSequence(SequenceFragment sequenceFragment) {
        this.sequences.remove(sequenceFragment);
        sequenceFragment.getFragments().remove(this);
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
