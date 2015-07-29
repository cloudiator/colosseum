/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package controllers.generic;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.google.inject.TypeLiteral;
import controllers.security.SecuredSessionOrToken;
import dtos.api.Dto;
import dtos.conversion.ModelDtoConversionService;
import dtos.generic.LinkDecoratorDto;
import models.Tenant;
import models.generic.Model;
import models.service.FrontendUserService;
import models.service.ModelService;
import play.data.Form;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Result;
import play.mvc.Security;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A generic controller for reading model entities and providing
 * them as JSON formatted DTOs.
 * <p>
 * Provides basic CRUD functionality for the model entity.
 *
 * @param <T> The type of the model the controller uses.
 * @param <U> The type of the DTO the controller uses for get operations (display)
 * @param <V> The type of the DTO the controller uses for post operations (computeService)
 * @param <W> The type of the DTO the controller uses for put operations (update)
 * @author Daniel Baur
 */
@Security.Authenticated(SecuredSessionOrToken.class)
public abstract class GenericApiController<T extends Model, U extends Dto, V extends Dto, W extends Dto>
    extends AuthenticationController {

    private final ModelService<T> modelService;
    private final ModelDtoConversionService conversionService;
    private final Class<T> modelType;

    private final Class<U> getType;

    private final Form<V> postForm;
    private final Form<W> putForm;

    /**
     * Constructs a GenericApiController.
     *
     * @param modelService      the model service for retrieving the models.
     * @param typeLiteral       a type literal for the model type
     * @param conversionService the conversion service for converting models and dtos.
     * @throws java.lang.NullPointerException if any of the above parameters is null.
     */
    public GenericApiController(FrontendUserService frontendUserService,
        ModelService<Tenant> tenantModelService, ModelService<T> modelService,
        TypeLiteral<T> typeLiteral, ModelDtoConversionService conversionService) {
        super(frontendUserService, tenantModelService);

        checkNotNull(modelService);
        checkNotNull(typeLiteral);
        checkNotNull(conversionService);

        //get the type of the model
        //noinspection unchecked
        this.modelType = (Class<T>) typeLiteral.getRawType();

        //resolve the types of the dtos
        //noinspection unchecked
        this.getType = (Class<U>) new TypeToken<U>(getClass()) {
        }.getRawType();
        //noinspection unchecked
        Class<V> postType = (Class<V>) new TypeToken<V>(getClass()) {
        }.getRawType();
        //noinspection unchecked
        Class<W> putType = (Class<W>) new TypeToken<W>(getClass()) {
        }.getRawType();


        this.conversionService = conversionService;
        this.modelService = modelService;

        this.postForm = Form.form(postType);
        this.putForm = Form.form(putType);
    }

    private T getInstance() {
        try {
            final Constructor<T> declaredConstructor = this.modelType.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads the model identified by the given id from the
     * database.
     *
     * @param id The id of the model. Not null.
     * @return the corresponding model if found, null
     * if not.
     * @throws java.lang.NullPointerException if the given id is null.
     */
    @Nullable private T loadEntity(final Long id) {
        checkNotNull(id);
        T t = modelService.getById(id);
        if (filter().isPresent()) {
            if (filter().get().apply(t)) {
                return t;
            } else {
                return null;
            }
        }
        return t;
    }

    /**
     * Loads all entities of the model from
     * the service.
     *
     * @return a list of all model entities.
     */
    private List<T> loadEntities() {
        List<T> ts = modelService.getAll();
        if (filter().isPresent()) {
            return Lists.newArrayList(Iterables.filter(ts, filter().get()));
        }
        return ts;
    }

    /**
     * Extension Point for a filter used when retrieving entities.
     *
     * @return an optional filter applied when retrieving entities.
     */
    protected Optional<Predicate<T>> filter() {
        return Optional.absent();
    }

    /**
     * The route for use in the self link.
     *
     * @return a string depicting the self route.
     */
    protected abstract String getSelfRoute(final Long id);

    /**
     *
     */
    private Dto convertToDto(T entity) {
        U dto = this.conversionService.toDto(entity, getType);
        return LinkDecoratorDto.decorate(dto, this.getSelfRoute(entity.getId()));
    }

    /**
     * Helper for generating the not found message.
     *
     * @param id the id that was not found.
     * @return An result containing a error message depicting that the given id was
     * not found (404 status code).
     */
    protected Result notFound(final Long id) {
        return notFound(
            "Could not find entity of type " + this.modelType.getSimpleName() + " with id " + id);
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
    @Transactional(readOnly = true) @BodyParser.Of(BodyParser.Empty.class) public Result list() {
        List<T> entities = this.loadEntities();
        List<Dto> dtos = new ArrayList<>(entities.size());
        dtos.addAll(entities.stream().map(this::convertToDto).collect(Collectors.toList()));
        return ok(Json.toJson(dtos));
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
    @Transactional(readOnly = true) @BodyParser.Of(BodyParser.Empty.class) public Result get(
        final Long id) {
        final T entity = this.loadEntity(id);

        if (entity == null) {
            return this.notFound(id);
        }

        return ok(Json.toJson(this.convertToDto(entity)));
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
     * Otherwise 400. Will return the created entity {@link #get(Long)}.
     */
    @Transactional() @BodyParser.Of(BodyParser.Json.class) public Result post() {


        final Form<V> filledForm = postForm.bindFromRequest();

        if (filledForm.hasErrors()) {
            return badRequest(filledForm.errorsAsJson());
        }

        final T entity = this.conversionService.toModel(filledForm.get(), this.getInstance());

        this.modelService.save(entity);

        postPost(entity);

        return get(entity.getId());
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
     * Otherwise 400. Will return the updated entity {@link #get(Long)}.
     */
    @Transactional @BodyParser.Of(BodyParser.Json.class) public Result put(final Long id) {

        T entity = this.loadEntity(id);

        if (entity == null) {
            return this.notFound(id);
        }

        final Form<W> filledForm = putForm.bindFromRequest();

        if (filledForm.hasErrors()) {
            return badRequest(filledForm.errorsAsJson());
        }

        entity = this.conversionService.toModel(filledForm.get(), entity);
        this.modelService.save(entity);

        postPut(entity);

        return get(id);
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
    @Transactional @BodyParser.Of(BodyParser.Empty.class) public Result delete(final Long id) {
        T entity = this.loadEntity(id);

        if (entity == null) {
            return this.notFound(id);
        }

        this.modelService.delete(entity);

        return ok();
    }

    protected void postPost(T entity) {
        // intentionally left empty
    }

    protected void postPut(T entity) {
        //intentionally left empty
    }
}
