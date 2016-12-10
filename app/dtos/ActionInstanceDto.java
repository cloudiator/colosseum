package dtos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import dtos.generic.ValidatableDto;
import dtos.validation.validators.ExpressionValidator;
import dtos.validation.validators.ModelIdValidator;
import dtos.validation.validators.NotNullValidator;
import models.*;
import models.service.ModelService;

import java.util.concurrent.TimeUnit;

/**
 * Created by frankgriesinger on 10.12.2016.
 */
public class ActionInstanceDto extends ValidatableDto {
    private Long action;
    private String dateStarted;
    private String dateFinished;
    private String dateFailed;

    public ActionInstanceDto() {
        super();
    }

    @Override public void validation() {
        validator(String.class).validate(this.dateStarted).withValidator(new NotNullValidator());
        validator(Long.class).validate(action, "action")
                .withValidator(new ModelIdValidator<>(ActionInstanceDto.References.actionService.get()));
    }

    public static class References {
        @Inject
        public static Provider<ModelService<Action>> actionService;
    }

    public Long getAction() {
        return action;
    }

    public void setAction(Long action) {
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
