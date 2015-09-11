package forms;

import play.data.validation.Constraints;

/**
 * Created by bwpc on 20.11.2014.
 */
public class MolproRunConfigurationForm {

    @Constraints.Required
    public long idCloudHardware;
    @Constraints.Required
    public long idMolproComputation;

    public MolproRunConfigurationForm(){

    }

    public MolproRunConfigurationForm(long idMolproComputation, long idCloudHardware){
        this.idMolproComputation = idMolproComputation;
        this.idCloudHardware = idCloudHardware;
    }
}
