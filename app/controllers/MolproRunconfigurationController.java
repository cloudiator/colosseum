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
	
	@Inject
	public MolproRunconfigurationController(ModelService<Cloud> cloudModelService){		
		this.cloudModelService = cloudModelService;
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
//            Hardware cloudHardware = CloudHardware.findById(filledForm.get().idCloudHardware);
//
//            molproComputation.application.applicationComponents.get(0).component.componentConfiguration.hardware = cloudHardware.hardware;
//            molproComputation.save();

            flash("success", "The run configuration of th computation was successfully changed.");
            return redirect(routes.MolproRunconfigurationController.show(molproComputation.getId()));
        }

    }


    private Map<String, String> getMatchingHardware(int numberOfCores, Cloud cloud) {
        LinkedHashMap<String, String> options = new LinkedHashMap<String, String>();
//        List<CloudHardware> cloudHardwareList =  CloudHardware.findByCloudAndCpuCores(cloud, numberOfCores);
//        for (CloudHardware cloudHardware : cloudHardwareList) {
//            String idCloudHardware = cloudHardware.id.toString();
//            String description = "Cores: " + cloudHardware.hardware.numberOfCpu + " Memory: " + cloudHardware.hardware.mbOfRam + "MB Disk:" + cloudHardware.hardware.localDiskSpace + "GB";
//            options.put(idCloudHardware, description);
//        }
        return options;
    }

}

