package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import com.google.inject.Inject;

import models.Application;
import models.MolproComputation;
import models.service.ModelService;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

/**
 * Represents the endpoint for the molpro instance.
 *
 * @author Daniel Seybold
 *
 */
public class MolproCommunicationController extends Controller {

	private final ModelService<Application> applicationModelService;
	
	@Inject
	public MolproCommunicationController(ModelService<Application> applicationModelService){		
		this.applicationModelService = applicationModelService;
	}
	

    /**
     * Builds a String of the necessary Molpro environement variables
     *
     * @param idApplication
     * @return  Composed String of the Molpro environement variables
     */
    @Transactional(readOnly = true)
    public Result getMolproEnvironementVariables(long idApplication){
        Application application = applicationModelService.getById(idApplication);
        MolproComputation computation = MolproComputation.findByApplication(application);

        String molproEnvironement = "";
        molproEnvironement += "MOLPRO_RUNS=1 \n";
        molproEnvironement += "MOLPRO_CORES=" + computation.numOfComputationCores + " \n";
        molproEnvironement += "MOLPRO_OUTPUT_SINK=" + buildMolproExecwareEndpoint("saveMolproOutputCompressed", application);

        return  ok(molproEnvironement);
    }

    /**
     * Gets the molpro input of the associated application and returns it
     *
     * @param idApplication
     * @return the molpro input file as a String
     */
    @Transactional(readOnly = true)
    public Result getInputFileAsString(long idApplication){


    	Application application = applicationModelService.getById(idApplication);
        MolproComputation computation = MolproComputation.findByApplication(application);
        return  ok(computation.molproInput);
    }

    /**
     * Gets the additional Molpro scripts of the associated applications and returns them
     *
     * @param idApplication
     * @return additional molpro scripts as .tgz file
     */
    @Transactional(readOnly = true)
    public Result getMolproScriptsCompressed(long idApplication){

    	Application application = applicationModelService.getById(idApplication);
        MolproComputation computation = MolproComputation.findByApplication(application);

        response().setContentType("application/x-compressed");
        return  ok(computation.molproScripts);//.as("application/x-compressed");
    }

    /**
     * handles post request with the attached Molpro output as .tgz file
     * stores the output in molprocomputation for the associated application
     *
     * After saving the data the termination of the molpro VM is invoked.
     *
     * @param idApplication
     * @return ok/badRequest
     */
    @Transactional
    @BodyParser.Of(value = BodyParser.MultipartFormData.class, maxLength = 1000000 * 1024)
    public Result saveMolproOutputCompressed(long idApplication){

    	Application application = applicationModelService.getById(idApplication);
        MolproComputation computation = MolproComputation.findByApplication(application);

        Http.MultipartFormData body = request().body().asMultipartFormData();

        if(body == null){
            return badRequest("body is empty");
        }
        Http.MultipartFormData.FilePart zip = body.getFile("molpro.out.tgz");
        if(zip != null){
            try{
                computation.output = FileUtils.readFileToByteArray(zip.getFile());
            }catch (IOException e){
                return badRequest("Can not read molpro.out.tgz in request!");
            }
        }else {
            return badRequest("no molpro.out.tgz found in request!");
        }

        computation.save();
        Logger.info("molpro.out.tgz saved!");

        Logger.info("Invoking shutdown of molpro VM.");
        terminateInstance(application);

        return  ok("molpro.out.tgz saved!");
    }

    public  static String buildMolproExecwareEndpoint(String action, Application application){
        String apiEndpoint = "http://";
        //todo: switch between vm and regular machine: openstack: get the external ip  by magic: http://169.254.169.254/2009-04-04/meta-data/public-ipv4

        //no vm solution
        /*
        try {
            apiEndpoint += InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            Logger.error(e.toString());
        }
        apiEndpoint += ":9000/molproCommunication/"+action+"/"+application.id;
        return apiEndpoint;
        */

        //openstack vm solution
        return apiEndpoint + getOpenStackExternalIp() + ":9000/molproCommunication/"+action+"/"+application.getId();


    }


    private static String getOpenStackExternalIp(){

        String externalIp = "";
        String wgetResult;

        URL url;
        InputStream inputStream;
        BufferedReader bufferedReader;

        try
        {
            url = new URL("http://169.254.169.254/2009-04-04/meta-data/public-ipv4");
            inputStream = url.openStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((wgetResult = bufferedReader.readLine()) != null){
                Logger.info("Received external molproGui ip is: " + wgetResult);
                externalIp += wgetResult;
            }

            inputStream.close();

            //return static ip when URL returns no IP
            if(externalIp == null || externalIp.isEmpty()){
                Logger.info("No external IP of MolproGui received. Sweitching to static ip 134.60.64.27");
                externalIp = "134.60.64.27";
            }

            return externalIp;
        }
        catch (MalformedURLException mue)
        {
            Logger.error(mue.toString());

        }
        catch (IOException ioe)
        {
            Logger.error(ioe.toString());

        }

        return externalIp;
    }

    /**
     * Invokes the termination of the MolproComputation VM instance
     *
     * @param application
     * @return the molpro input file as a String
     */
    @Transactional(readOnly = true)
    private static void terminateInstance(Application application){
//        //create an installation job.
//        UninstallationJob job = new UninstallationJob();
//        job.resourceId = application.id;  //installation.id;
//        job.setExecutor(CloudifyCli.class);
//        //job.dependsOn = createdBootstrapJobs;
//        job.save();
//        JobQueue.getInstance().placeJob(job.id);
//
//        Logger.info("Shutting down molpro vm invoked!");
    }
}
