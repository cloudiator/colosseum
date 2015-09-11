package controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;

import controllers.security.SecuredSessionOrToken;
import de.uniulm.omi.cloudiator.colosseum.client.entities.CloudHardware;
import forms.MolproRunConfigurationForm;
import models.Cloud;
import models.Hardware;
import models.MolproComputation;
import models.service.ModelService;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;


/**
 * Controller for the Molpro  computations.
 *
 * @author Daniel Seybold
 *
 */
@Security.Authenticated(SecuredSessionOrToken.class)
public class MolproRunconfigurationController extends Controller {

    /**
     * A form for the molproComputation model.
     */
    static Form<MolproRunConfigurationForm> molproComputationForm = Form.form(MolproRunConfigurationForm.class);

	private final ModelService<Cloud> cloudModelService;
	private final ModelService<Hardware> hardwareModelService;
	
	@Inject
	public MolproRunconfigurationController(
			ModelService<Cloud> cloudModelService,
			ModelService<Hardware> hardwareModelService
			){		
		this.cloudModelService = cloudModelService;
		this.hardwareModelService = hardwareModelService;
	}    
    
    @Transactional(readOnly = true)
    public Result index() {
        session("menuitemActive", "RunConfiguration");
        return ok(views.html.molproRunconfiguration.index.render(MolproComputation.findAll()));
    }


    @Transactional
    public Result show(Long id){
        MolproComputation molproComputation = MolproComputation.findById(id);
        Cloud cloud = molproComputation.application.getApplicationComponents().get(0).getVirtualMachineTemplate().getCloud();
        Map<String, String> matchingHardware = getMatchingHardware(molproComputation.numOfComputationCores, cloud);
        return ok(views.html.molproRunconfiguration.show.render(molproComputation, matchingHardware, molproComputationForm));
    }

    @Transactional
    public Result changeConfiguration(){
        Form<MolproRunConfigurationForm> filledForm = molproComputationForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            return badRequest("An error occured, missing post parameters!");
        }else{
            MolproComputation molproComputation = MolproComputation.findById(filledForm.get().idMolproComputation);
            Hardware cloudHardware = hardwareModelService.getById(filledForm.get().idCloudHardware);
            molproComputation.application.getApplicationComponents().get(0).getVirtualMachineTemplate().setHardware(cloudHardware);
            molproComputation.save();

            flash("success", "The run configuration of th computation was successfully changed.");
            return redirect(routes.MolproRunconfigurationController.show(molproComputation.getId()));
        }

    }


    private Map<String, String> getMatchingHardware(int numberOfCores, Cloud cloud) {
        LinkedHashMap<String, String> options = new LinkedHashMap<String, String>();
        for (Hardware hardware : hardwareModelService.getAll()){
        	if(!hardware.getCloud().equals(cloud))
        		continue;
        	String idHardware = hardware.getId().toString();
    		String description = "Cores: " + hardware.getHardwareOffer().getNumberOfCores() + " Memory: " + hardware.getHardwareOffer().getMbOfRam() + "MB Disk:" + hardware.getHardwareOffer().getLocalDiskSpace() + "GB";
    		options.put(idHardware, description);
        }
        return options;
    }

}

