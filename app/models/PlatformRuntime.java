package models;

import models.generic.Model;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Daniel Seybold on 24.11.2016.
 */
@Entity public class PlatformRuntime extends Model {

    @Column(nullable = false, updatable = false) private String language;

    @Column(updatable = false) private String type;


    private Float version;

    protected PlatformRuntime() {
    }

    public PlatformRuntime(String language, @Nullable String type, Float version) {

        checkNotNull(language);
        checkNotNull(version);

        this.language = language;
        this.type = type;
        this.version = version;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getVersion() {
        return version;
    }

    public void setVersion(Float version) {
        this.version = version;
    }
}
