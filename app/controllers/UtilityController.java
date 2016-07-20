package controllers;

import com.google.common.io.Files;
import com.google.inject.Inject;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.typesafe.config.ConfigValue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import controllers.security.SecuredSessionOrToken;
import play.Play;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

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

        Map<String, String> files = new HashMap<>(2);
        files.put("travis", "conf/.travis");
        files.put("jenkins", "conf/.jenkins");

        ObjectNode result = Json.newObject();

        for (Map.Entry<String, String> entry : files.entrySet()) {
            final Optional<Integer> version = VersionFileReader.of(entry.getValue()).version();
            result.put(entry.getKey(), version.orElse(null));
        }

        return ok(result);
    }

    private static class VersionFileReader {

        private final File file;

        private VersionFileReader(String path) {
            this.file = new File(path);
        }

        public static VersionFileReader of(String path) {
            return new VersionFileReader(path);
        }

        public Optional<Integer> version() {
            String version;
            try {
                version = Files.readFirstLine(file, Charset.forName("UTF-8"));
            } catch (IOException e) {
                return Optional.empty();
            }
            return toInt(version);
        }

        private Optional<Integer> toInt(String version) {
            try {
                //validate version
                int validate = Integer.valueOf(version);
                if (validate == 0) {
                    return Optional.empty();
                }
                return Optional.of(validate);
            } catch (NumberFormatException nfe) {
                return Optional.empty();
            }
        }
    }

}
