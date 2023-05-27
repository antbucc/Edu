package com.modis.edu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.modis.edu.domain.enumeration.Level;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Module1.
 */
@Document(collection = "module_1")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Module1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("start_date")
    private Instant startDate;

    @Field("end_data")
    private Instant endData;

    @Field("level")
    private Level level;

    @DBRef
    @Field("scenario")
    private Scenario scenario;

    @DBRef
    @Field("fragments")
    @JsonIgnoreProperties(
        value = { "order", "activity", "abstractActivity", "setOf", "sequence", "modules", "setOf1s" },
        allowSetters = true
    )
    private Set<Fragment> fragments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Module1 id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Module1 title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Module1 description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public Module1 startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndData() {
        return this.endData;
    }

    public Module1 endData(Instant endData) {
        this.setEndData(endData);
        return this;
    }

    public void setEndData(Instant endData) {
        this.endData = endData;
    }

    public Level getLevel() {
        return this.level;
    }

    public Module1 level(Level level) {
        this.setLevel(level);
        return this;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Scenario getScenario() {
        return this.scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public Module1 scenario(Scenario scenario) {
        this.setScenario(scenario);
        return this;
    }

    public Set<Fragment> getFragments() {
        return this.fragments;
    }

    public void setFragments(Set<Fragment> fragments) {
        this.fragments = fragments;
    }

    public Module1 fragments(Set<Fragment> fragments) {
        this.setFragments(fragments);
        return this;
    }

    public Module1 addFragment(Fragment fragment) {
        this.fragments.add(fragment);
        return this;
    }

    public Module1 removeFragment(Fragment fragment) {
        this.fragments.remove(fragment);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Module1)) {
            return false;
        }
        return id != null && id.equals(((Module1) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Module1{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endData='" + getEndData() + "'" +
            ", level='" + getLevel() + "'" +
            "}";
    }
}