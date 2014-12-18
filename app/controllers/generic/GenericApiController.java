package controllers.generic;

import controllers.Secured;
import dtos.convert.api.ModelDtoConversionService;
import dtos.generic.api.Dto;
import dtos.generic.impl.Links;
import models.generic.Model;
import models.service.api.generic.ModelServiceInterface;
import play.data.Form;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A generic controller for reading model entities and providing
 * them as JSON formatted DTOs.
 * <p>
 * Provides basic CRUD functionality for the model entity.
 *
 * @param <T> The type of the model the controller uses.
 * @param <S> The type of the DTO the controller uses.
 * @author Daniel Baur
 */
@Security.Authenticated(Secured.class)
public abstract class GenericApiController<T extends Model, S extends Dto> extends Controller {

    private final ModelServiceInterface<T> modelService;
    private final ModelDtoConversionService conversionService;
    private final Class<T> type;
    private final Class<S> dtoType;
    private final Form<S> form;

    /**
     * Loads the model identified by the given id from the
     * database.
     *
     * @param id The id of the model. Not null.
     * @return the corresponding model if found, null
     * if not.
     */
    protected T loadEntity(final Long id) {
        return modelService.getById(id);
    }

    /**
     * Loads all entites of the model from
     * the service.
     *
     * @return a list of all model entities.
     */
    protected List<T> loadEntities() {
        return modelService.getAll();
    }

    /**
     * The route for use in the self link.
     *
     * @return a string depicting the self route.
     */
    protected abstract String getSelfRoute(final Long id);

    private Map<Long, String> getSelfRoutes(List<T> models) {
        Map<Long, String> map = new HashMap<>(models.size());
        for (T model : models) {
            map.put(model.getId(), this.getSelfRoute(model.getId()));
        }
        return map;
    }

    /**
     * Helper for generating the not found message.
     *
     * @param id the id that was not found.
     * @return An result containing a error message depicting that the given id was
     * not found (404 status code).
     */
    protected Result notFound(final Long id) {
        return notFound("Could not find entity of type " + this.type.getSimpleName() + " with id " + id);
    }

    /**
     * Constructs a GenericApiController.
     *
     * @param modelService      the model service for retrieving the models.
     * @param conversionService the conversion service for converting models and dtos.
     */
    protected GenericApiController(ModelServiceInterface<T> modelService, ModelDtoConversionService conversionService) {

        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        //noinspection unchecked
        this.type = (Class) pt.getActualTypeArguments()[0];
        //noinspection unchecked
        this.dtoType = (Class) pt.getActualTypeArguments()[1];

        checkNotNull(conversionService);
        this.conversionService = conversionService;
        checkNotNull(modelService);
        this.modelService = modelService;

        this.form = Form.form(this.dtoType);
    }

    /**
     * Returns a json list of all models.
     * <p>
     * Retrieves the models using the model service,
     * converts them to DTOs and returns their json
     * representation.
     *
     * @return A json representation of all entities.
     */
    @Transactional(readOnly = true)
    @BodyParser.Of(BodyParser.Empty.class)
    public Result list() {
        return ok(Json.toJson(this.conversionService.toDtos(this.loadEntities(), this.dtoType, Links.fromIdsAndSelfLinks(this.getSelfRoutes(this.loadEntities())))));
    }

    /**
     * Returns a single json representation of an entity.
     * <p>
     * Retrieves the entity identified by the given id,
     * converts it to its DTO and returns a JSON representation
     * of the DTO.
     * <p>
     * If the entity is not found, a 404 NOT FOUND is instead
     * returned.
     *
     * @param id the id of the entity.
     * @return A JSON representation of the requested entity. 404 if
     * entity does not exist.
     */
    @Transactional(readOnly = true)
    @BodyParser.Of(BodyParser.Empty.class)
    public Result get(final Long id) {
        final T entity = this.loadEntity(id);

        if (entity == null) {
            return this.notFound(id);
        }

        return ok(Json.toJson(this.conversionService.toDto(entity, this.dtoType, Links.fromSelfLink(this.getSelfRoute(id)))));
    }

    /**
     * Creates a entity from the given JSON representation.
     * <p>
     * Converts the JSON representation to a DTO, validates
     * the DTO and then converts it to a model entity.
     * <p>
     * The model entity is then stored in the model service.
     * <p>
     * If the entity contains errors, a 400 BAD REQUEST is raised
     * and the validation errors are returned as JSON.
     * If the request is invalid JSON, a 400 BAD REQUEST is returned.
     *
     * @return OK if the entity was successfully stored in the service.
     * Otherwise 400.
     */
    @Transactional()
    @BodyParser.Of(BodyParser.Json.class)
    public Result create() {

        final Form<S> filledForm = form.bindFromRequest();

        if (filledForm.hasErrors()) {
            return badRequest(filledForm.errorsAsJson());
        }

        final T entity = this.conversionService.toModel(filledForm.get(), this.type);

        this.modelService.save(entity);

        return ok();
    }

    /**
     * Updates a entity within the model service with
     * the information given.
     * <p>
     * Converts the JSON representation to a DTO, validates
     * the DTO and the updates the model information with
     * the DTO.
     * <p>
     * The changes are stored in the model service.
     * <p>
     * If the entity contains errors, a 400 BAD REQUEST is returned with
     * detailed validation errors in JSON format.
     * If the request is invalid JSON, a 400 BAD REQUEST is returned.
     *
     * @param id the id of the resource which should be changed.
     * @return OK, if the entity was successfully stored in the service.
     * Otherwise 400.
     */
    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result update(final Long id) {

        T entity = this.loadEntity(id);

        if (entity == null) {
            return this.notFound(id);
        }

        final Form<S> filledForm = form.bindFromRequest();

        if (filledForm.hasErrors()) {
            return badRequest(filledForm.errorsAsJson());
        }

        entity = this.conversionService.toModel(filledForm.get(), entity, this.type);

        this.modelService.save(entity);

        return ok();
    }

    /**
     * Deletes the entity identified by the given id.
     * <p>
     * Deletes the entity with the given id from the model
     * service.
     * <p>
     * If the entity is not found, a 404 NOT FOUND is returned.
     *
     * @param id the id of the resource which shall be deleted.
     * @return OK if the resource was deleted, 404 if the resource
     * could not be found.
     */
    @Transactional
    @BodyParser.Of(BodyParser.Empty.class)
    public Result delete(final Long id) {
        T entity = this.loadEntity(id);

        if (entity == null) {
            return this.notFound(id);
        }

        this.modelService.delete(entity);

        return ok();
    }

}
