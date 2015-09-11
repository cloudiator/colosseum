package controllers;


import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.io.IOException;

import controllers.security.SecuredSessionOrToken;

/**
 * Created by bwpc on 23.10.2014.
 */
public class MolproController extends Controller {

	@Security.Authenticated(SecuredSessionOrToken.class)
    @Transactional
    public Result molpro() throws IOException {
        session("cactos", "1");
        return ok(views.html.molpro.render());
    }
}
