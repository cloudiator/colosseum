package controllers;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

import com.google.inject.Inject;

import controllers.security.SecuredSessionOrToken;
import forms.MolproComputationForm;
import models.Application;
import models.ApplicationComponent;
import models.Cloud;
import models.Component;
import models.Hardware;
import models.Image;
import models.LifecycleComponent;
import models.Location;
import models.MolproComputation;
import models.VirtualMachineTemplate;
import models.service.ModelService;
import play.Logger;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import play.mvc.Security;


/**
 * Controller for the Molpro  computations.
 *
 * @author Daniel Seybold
 *
 */
@Security.Authenticated(SecuredSessionOrToken.class)
public class MolproComputationController extends Controller {

    /**
     * A form for the molproComputation model.
     */
    static Form<MolproComputationForm> molproComputationFormForm = Form.form(MolproComputationForm.class);

	
	private final ModelService<Cloud> cloudModelService;
	private final ModelService<Application> applicationModelService;
	private final ModelService<Image> imageModelService;
	private final ModelService<Hardware> hardwareModelService;
	private final ModelService<LifecycleComponent> lifecycleComponentModelService;
	private final ModelService<VirtualMachineTemplate> virtualMachineTemplateModelService;
	private final ModelService<ApplicationComponent> applicationComponentModelService;
	private final ModelService<Location> locationModelService;
	
	
	
	@Inject
	public MolproComputationController(
			ModelService<Cloud> cloudModelService, 
			ModelService<Application> applicationModelService, 
			ModelService<Image> imageModelService, 
			ModelService<Hardware> hardwareModelService, 
			ModelService<LifecycleComponent> lifecycleComponentModelService, 
			ModelService<VirtualMachineTemplate> virtualMachineTemplateModelService,
			ModelService<ApplicationComponent> applicationComponentModelService, 
			ModelService<Location> locationModelService){
		this.cloudModelService = cloudModelService;
		this.applicationModelService = applicationModelService;
		this.imageModelService = imageModelService;
		this.hardwareModelService = hardwareModelService;
		this.lifecycleComponentModelService = lifecycleComponentModelService;
		this.virtualMachineTemplateModelService = virtualMachineTemplateModelService;
		this.applicationComponentModelService = applicationComponentModelService;
		this.locationModelService = locationModelService;

	}

    @Transactional(readOnly = true)
    public Result index() {
        session("menuitemActive", "Computation");
        return ok(views.html.molproComputation.index.render(MolproComputation.findAll()));
    }

    /**
     * New action for a computation object.
     *
     * Maybe.....Renders a form for molproComputation objects.
     *
     * @return the rendered new view.
     */
    public Result newComputation() {

        return ok(views.html.molproComputation.newComputation.render(molproComputationFormForm));
    }

    /**
     * Validates the submitted new form and creates the related molprocomputation object.
     *
     * If validation fails, the new form is shown again.
     *
     * @return the rendered view
     */
    @Transactional
    public Result create() {

        Form<MolproComputationForm> filledForm = molproComputationFormForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            return badRequest(views.html.molproComputation.newComputation.render(filledForm));
            
//        }else if(!compatibleHardware(filledForm.get().numOfComputationCores)){
//        	
//            String errorMessage = "No matching hardware found. Availabale cores are: ";
//            flash("error", errorMessage);
//            return badRequest(views.html.molproComputation.newComputation.render(filledForm));
            
        }else {
            MolproComputation molproComputation = new MolproComputation();
            molproComputation.numOfComputationCores = filledForm.get().numOfComputationCores;
            molproComputation.title = filledForm.get().title;
            molproComputation.molproInput = filledForm.get().molproInput;
            molproComputation.started = false;

            MultipartFormData body = request().body().asMultipartFormData();
            FilePart zip = body.getFile("molproScripts");
            if(zip != null){
                try{
                    molproComputation.molproScripts = FileUtils.readFileToByteArray(zip.getFile());
                }catch (IOException e){
                    flash("error", "Error reading file");
                    return badRequest(views.html.molproComputation.newComputation.render(filledForm));
                }
            }

            //create and add the MolproApplication
            molproComputation.application = createMolproApplication(molproComputation);
            //extract Molpro command
            molproComputation.command = extractMolproCommand(filledForm.get().molproInput);
            //extract the required RAM of Molpro
            molproComputation.memory = extractRequiredMemory(filledForm.get().molproInput);
            //save the whole Model
            molproComputation.save();
            flash("success", "The computation was successfully created.");
            return redirect(routes.MolproComputationController.show(molproComputation.getId()));
        }

    }

//    private boolean compatibleHardware(int cpuCores){
//        Cloud omiCloud = Cloud.findByCloudifyName(MolproComputation.MOLPROTESTBED);
//        List<CloudHardware> cloudHardwares = CloudHardware.findByCloudAndCpuCores(omiCloud,cpuCores);
//
//        if(cloudHardwares == null || cloudHardwares.size() == 0){
//            return false;
//        }else{
//            return true;
//        }
//
//    }

    private Application createMolproApplication(MolproComputation molproComputation){
        Application molproApplication = new Application("molpro_"+molproComputation.title);
        applicationModelService.save(molproApplication);

        Component molproComponent = createMolproComponent(molproApplication, molproComputation, "molpro_"+molproComputation.title);
        VirtualMachineTemplate vmt = createMolproVirtualMachineTemplate(molproComponent, molproComputation.numOfComputationCores);
        createMolproApplicationComponent(molproApplication, molproComponent, vmt);

        return molproApplication;
    }

    private Component createMolproComponent(Application application, MolproComputation molproComputation, String name){
    	
    	String init = "";
		String preInstall = buildDownloadCommand(application, molproComputation);
        String install = "";
        String postInstall = ""; 
        String preStart = "";
        String start = "cd molpro/cactos && ./execute.sh";
        String startDetection = ""; 
        String stopDetection = "";
        String postStart = "";
        String preStop = "";
        String stop = "";
        String postStop = "";
        String shutdown = "";
    	
        LifecycleComponent molproComponent = new LifecycleComponent(
        		name,
        		init, 
        		preInstall,
                install, 
                postInstall, 
                preStart,
                start, 
                startDetection, 
                stopDetection,
                postStart, 
                preStop, 
                stop,
                postStop, 
                shutdown        		
        		);
        lifecycleComponentModelService.save(molproComponent);
        return molproComponent;
    }

    private String buildDownloadCommand(Application application, MolproComputation molproComputation){
        String command = "";
        //download number of cores for computation
        command += "wget " + MolproCommunicationController.buildMolproExecwareEndpoint("getComputationCores",application) +" -O /tmp/molpro.env ";
        //download molpro input file
        command += "&& wget " + MolproCommunicationController.buildMolproExecwareEndpoint("getInputFileAsString", application) +" -O /tmp/molpro.inp ";
        //download additional molpro scripts if necessary
        if(molproComputation.molproScripts != null){
            command += "&& wget " + MolproCommunicationController.buildMolproExecwareEndpoint("getMolproScriptsCompressed", application) + " -O /tmp/molpro.inp-arc.tgz";
        }
        return command;
    }

    private void createMolproApplicationComponent(Application application, Component component, VirtualMachineTemplate virtualMachineTemplate){
        ApplicationComponent applicationComponent = new ApplicationComponent(application, component, virtualMachineTemplate);
        applicationComponentModelService.save(applicationComponent);
    }

    private VirtualMachineTemplate createMolproVirtualMachineTemplate(Component component, int numberOfComputationCores){
    	Cloud cloud = cloudModelService.getAll().get(0);
    	
    	Image image = findMolproImage();
    	Location location = locationModelService.getAll().get(0);
    	Hardware hardware = findHardwareByCpuCores(cloud, numberOfComputationCores);
		VirtualMachineTemplate vmt = new VirtualMachineTemplate(cloud, image, location, hardware);
		virtualMachineTemplateModelService.save(vmt);
		return vmt;
    }

    private Image findMolproImage(){
    	for(Image image : imageModelService.getAll()){
    		if(image.getName().contains("Centos")){
    			return image;
    		}
    	}
    	throw new IllegalStateException("No molpro image found!");
    }
    
    private Hardware findHardwareByCpuCores(Cloud cloud, int numberOfComputationCores){
    	for(Hardware hardware : hardwareModelService.getAll()){
    		if(!hardware.getCloud().equals(cloud)){
    			continue;
    		}
    		int hwCores = hardware.getHardwareOffer().getNumberOfCores();
    		if(hwCores >= numberOfComputationCores){
    			return hardware;
    		}
    	}
    	throw new IllegalStateException("No valid hardware found!");
    }
    
    /**
     * The show action.
     *
     * Displays information about the given MolproComputation.
     *
     * @param id the id of the MolproComputation.
     *
     * @return the rendered view.
     */
    @Transactional(readOnly = true)
    public Result show(Long id) {
        MolproComputation molproComputation = MolproComputation.findById(id);
        if (molproComputation == null) {
            return notFound("Could not load MolproComputation with id=" + id);
        }
        return ok(views.html.molproComputation.show.render(molproComputation));
    }

    /**
     * The download output action.
     *
     * Provides the stored output of a MolproComputation as download.
     *
     * @param id the id of the MolproComputation.
     *
     * @return the file download as tgz.
     */
    @Transactional(readOnly = true)
    public Result downloadOutput(Long id) {

        MolproComputation molproComputation = MolproComputation.findById(id);

        if (molproComputation == null) {
            return notFound("Could not load MolproComputation with id=" + id);
        }

        String tmpPath = "/tmp";
        String fileName = "molpro_computation_output_" + id + ".tgz";
        String filePath = tmpPath + File.separator + fileName;
        File file = new File(filePath);
        try {

            FileUtils.writeByteArrayToFile(file, molproComputation.output);
            return ok(file);
        } catch (IOException e) {
            Logger.error("Could not write temporary file for download.", e);
            return internalServerError("Could not prepare file for download.");
        }

    }

    /**
     * The download molproScripts  action.
     *
     * Provides the stored additional Molpro scripts of a MolproComputation as download.
     *
     * @param id the id of the MolproComputation.
     *
     * @return the file download as tgz.
     */
    @Transactional(readOnly = true)
    public Result downloadMolproScripts(Long id) {

        MolproComputation molproComputation = MolproComputation.findById(id);

        if (molproComputation == null) {
            return notFound("Could not load MolproComputation with id=" + id);
        }

        String tmpPath = "/tmp";
        String fileName = "molpro_scripts_output_" + id + ".tgz";
        String filePath = tmpPath + File.separator + fileName;
        File file = new File(filePath);
        try {

            FileUtils.writeByteArrayToFile(file, molproComputation.molproScripts);
            return ok(file);
        } catch (IOException e) {
            Logger.error("Could not write temporary file for download.", e);
            return internalServerError("Could not prepare file for download.");
        }

    }

    private String extractMolproCommand(String input){
        String result = "unknown";

        if(input == null){
            return result;
        }

        if(input.contains("df-lccsd")){
            result = "df-lccsd";
        }else if(input.contains("dft")){
            result = "dft";
        }

        Logger.info("Extracted Molpro command: " + result);
        return result;
    }

    private Long extractRequiredMemory(String input){
        Long result = 0L;

        if(input == null){
            return  result;
        }

        Scanner scanner = new Scanner(input);
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            if(line.contains("memory")){
                String [] memoryData = line.split(",");
                if(memoryData.length != 3){
                    Logger.warn("Unspecidied memory line (more than 3 items)");
                    scanner.close();
                    return result;
                }else if(!memoryData[2].equals("m")){
                    Logger.warn("Unspecidied memory unit (expected m): " + memoryData[2]);
                    scanner.close();
                    return result;
                }else{
                    //calculate the memory amount
                    result = 8L * 1024L * 1024L * Long.valueOf(memoryData[1]);
                    scanner.close();
                    return result;
                }
            }
        }
        scanner.close();
        return  result;
    }


}

