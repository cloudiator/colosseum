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

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import dtos.LoginDto;
import models.ApiAccessToken;
import models.service.ApiAccessTokenService;
import models.service.FrontendUserService;
import play.data.Form;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import de.uniulm.omi.cloudiator.common.Password;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 19.12.14.
 */
public class SecurityController extends Controller {

    private static final Form<LoginDto> loginForm = Form.form(LoginDto.class);
    private final FrontendUserService frontendUserService;
    private final ApiAccessTokenService apiAccessTokenService;

    @Inject public SecurityController(FrontendUserService frontendUserService,
        ApiAccessTokenService apiAccessTokenService) {
        checkNotNull(frontendUserService);
        checkNotNull(apiAccessTokenService);

        this.frontendUserService = frontendUserService;
        this.apiAccessTokenService = apiAccessTokenService;
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
        ApiAccessToken apiAccessToken =
            new ApiAccessToken(this.frontendUserService.getByMail(filledForm.get().getEmail()),
                Password.getInstance().generateToken());
        this.apiAccessTokenService.save(apiAccessToken);

        ObjectNode result = Json.newObject();
        result.put("createdOn", apiAccessToken.getCreatedOn());
        result.put("expiresAt", apiAccessToken.getExpiresAt());
        result.put("token", apiAccessToken.getToken());
        result.put("userId", apiAccessToken.getFrontendUser().getId());
        return ok(result);
    }

    public Result logout() {
        session().clear();
        flash("success", "You have been successfully logged out.");
        return redirect(routes.SecurityController.login());
    }


}
