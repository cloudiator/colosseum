package models;

import models.generic.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by frankgriesinger on 09.12.2016.
 */
@Entity
public class ActionInstance  extends Model {

    @ManyToOne(optional = false) private Action action;
    @Column(unique = false, nullable = false) private String dateStarted;
    @Column(unique = false, nullable = true) private String dateFinished;
    @Column(unique = false, nullable = true) private String dateFailed;

    /**
     * Empty constructor for hibernate.
     */
    protected ActionInstance() {
    }

    public ActionInstance(Action action, String dateStarted, String dateFinished, String dateFailed) {
        this.action = action;
        this.dateStarted = dateStarted;
        this.dateFinished = dateFinished;
        this.dateFailed = dateFailed;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(String dateStarted) {
        this.dateStarted = dateStarted;
    }

    public String getDateFinished() {
        return dateFinished;
    }

    public void setDateFinished(String dateFinished) {
        this.dateFinished = dateFinished;
    }

    public String getDateFailed() {
        return dateFailed;
    }

    public void setDateFailed(String dateFailed) {
        this.dateFailed = dateFailed;
    }
}
