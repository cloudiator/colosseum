package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.io.Files;
import com.google.inject.Inject;
import com.typesafe.config.ConfigValue;
import controllers.security.SecuredSessionOrToken;
import play.Play;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by daniel on 28.04.16.
 */
@Security.Authenticated(SecuredSessionOrToken.class) public class UtilityController
    extends Controller {

    @Inject public UtilityController() {

    }

    public Result configuration() {
        final java.util.Set<Map.Entry<String, ConfigValue>> entries =
            Play.application().configuration().getConfig("colosseum").entrySet();
        ObjectNode result = Json.newObject();
        for (Map.Entry<String, ConfigValue> entry : entries) {
            result.put(entry.getKey(), entry.getValue().render());
        }
        return ok(result);
    }

    public Result version() {

        File file = new File("conf/.travis");
        try {
            //read version from file
            String version = Files.readFirstLine(file, Charset.forName("UTF-8"));

            try {
                //validate version
                int validate = Integer.valueOf(version);
                if (validate == 0) {
                    return notFound("Version number not available.");
                }
            } catch (NumberFormatException ignored) {
                return notFound("Illegal version number found");
            }
            return ok(version);
        } catch (IOException ignored) {
            return notFound("Could not find/access build number.");
        }
    }

}
