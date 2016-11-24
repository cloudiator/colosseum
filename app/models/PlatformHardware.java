package models;

import models.generic.Model;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by Daniel Seybold on 24.11.2016.
 */
@Entity  public class PlatformHardware extends Model {

    @Column(nullable = true, updatable = false) private Integer numberOfCores;

    @Column(nullable = true, updatable = false) private Long mbOfRam;

    @Nullable
    private Float localDiskSpace;

    protected PlatformHardware() {
    }

    public PlatformHardware(@Nullable Integer numberOfCores, @Nullable Long mbOfRam, @Nullable Float localDiskSpace) {
        this.numberOfCores = numberOfCores;
        this.mbOfRam = mbOfRam;
        this.localDiskSpace = localDiskSpace;
    }

    @Nullable
    public Integer getNumberOfCores() {
        return numberOfCores;
    }

    public void setNumberOfCores(@Nullable Integer numberOfCores) {
        this.numberOfCores = numberOfCores;
    }

    @Nullable
    public Long getMbOfRam() {
        return mbOfRam;
    }


    public void setMbOfRam(@Nullable Long mbOfRam) {
        this.mbOfRam = mbOfRam;
    }

    @Nullable
    public Float getLocalDiskSpace() {
        return localDiskSpace;
    }

    public void setLocalDiskSpace(@Nullable Float localDiskSpace) {
        this.localDiskSpace = localDiskSpace;
    }
}
