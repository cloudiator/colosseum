package controllers;

import com.google.common.io.Files;
import com.google.inject.Inject;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by daniel on 28.04.16.
 */
public class UtilityController extends Controller {

    @Inject public UtilityController() {

    }

    public Result version() {

        File file = new File(".travis");
        try {
            //read version from file
            String version = Files.readFirstLine(file, Charset.forName("UTF-8"));

            try {
                //validate version
                int validate = Integer.valueOf(version);
                if(validate == 0) {
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
