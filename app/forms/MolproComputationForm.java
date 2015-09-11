package forms;

import models.MolproComputation;
import play.data.validation.Constraints;

/**
 * Created by bwpc on 24.10.2014.
 */
public class MolproComputationForm {


    @Constraints.Required
    public String title;
    @Constraints.Required
    public int numOfComputationCores;
    @Constraints.Required
    public String molproInput;
    public byte[] molproScripts;
    public byte[] output;

    public MolproComputationForm() {

    }

    public MolproComputationForm(String title, int numOfComputationCores, String molproInput, byte[] molproScripts, byte[] output) {
        this.title = title;
        this.numOfComputationCores = numOfComputationCores;
        this.molproInput = molproInput;
        this.molproScripts = molproScripts;
        this.output = output;
    }

    public MolproComputationForm(MolproComputation molproComputation) {
        this.title = molproComputation.title;
        this.numOfComputationCores = molproComputation.numOfComputationCores;
        this.molproInput = molproComputation.molproInput;
        this.molproScripts = molproComputation.molproScripts;
        this.output = molproComputation.output;
    }
}
