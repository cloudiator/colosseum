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

package controllers;

import com.google.inject.Inject;

import com.fasterxml.jackson.databind.node.ObjectNode;

import components.auth.Token;
import components.auth.TokenService;
import dtos.LoginDto;
import models.service.FrontendUserService;
import play.data.Form;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 19.12.14.
 */
public class SecurityController extends Controller {

    private static final Form<LoginDto> loginForm = Form.form(LoginDto.class);
    private final FrontendUserService frontendUserService;
    private final TokenService tokenService;

    @Inject public SecurityController(FrontendUserService frontendUserService,
        TokenService tokenService) {

        checkNotNull(frontendUserService);
        checkNotNull(tokenService);

        this.frontendUserService = frontendUserService;
        this.tokenService = tokenService;
    }

    public Result login() {
        return ok(views.html.login.render(loginForm));
    }

    @Transactional(readOnly = true) public Result authenticate() {
        Form<LoginDto> filledForm = loginForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            return badRequest(views.html.login.render(filledForm));
        } else {
            session().clear();
            session("email", filledForm.get().getEmail());
            session("tenant", filledForm.get().getTenant());
            return redirect(routes.CloudController.list());
        }
    }

    @Transactional @BodyParser.Of(BodyParser.Json.class) public Result authenticateApi() {
        final Form<LoginDto> filledForm = loginForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            return badRequest(filledForm.errorsAsJson());
        }
        //generate a new token
        Token token = tokenService
            .newToken(this.frontendUserService.getByMail(filledForm.get().getEmail()));

        ObjectNode result = Json.newObject();
        result.put("createdOn", token.createdOn());
        result.put("expiresAt", token.expiresAt());
        result.put("token", token.token());
        result.put("userId", token.userId());
        return ok(result);
    }

    public Result logout() {
        session().clear();
        flash("success", "You have been successfully logged out.");
        return redirect(routes.SecurityController.login());
    }


}
