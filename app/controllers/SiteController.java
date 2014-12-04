/*
 * Copyright (c) 2014 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package controllers;

import dtos.LoginDto;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.io.IOException;

/**
 * Site controller.
 *
 * Provides basic actions for the side like login, logout and the main index page.
 *
 * @author Daniel Baur
 */
public class SiteController extends Controller {

    private final static Form<LoginDto> loginForm = Form.form(LoginDto.class);

    public SiteController() {

    }

    @Security.Authenticated(Secured.class)
    public Result index() {
        return ok(views.html.site.index.render());
    }

    public Result login() {
        return ok(views.html.site.login.render(loginForm));
    }

    @Transactional(readOnly = true)
    public Result authenticate() {
        Form<LoginDto> filledForm = loginForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            return badRequest(views.html.site.login.render(filledForm));
        } else {
            session().clear();
            session("email", filledForm.get().email);
            return redirect(routes.SiteController.index());
        }
    }

    public Result logout() {
        session().clear();
        flash("success", "You have been successfully logged out.");
        return redirect(routes.SiteController.login());
    }

    @Security.Authenticated(Secured.class)
    public Result manage() {
        session().remove("menuitemActive");
        return ok(views.html.site.management.render());
    }

    public Result help() {
        session().remove("menuitemActive");
        return ok(views.html.site.help.render());
    }
}
