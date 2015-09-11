package models;

import javax.persistence.*;

import forms.MolproComputationForm;
import play.data.validation.Constraints.Required;
import play.db.jpa.JPA;

import java.util.List;

import models.generic.*;


/**
 *
 * Represents the a computation of a molpro benchmark folder contained in the cloud directory.
 *
 */
@NamedQueries({
        @NamedQuery(name = "MolproComputation.findAll", query = "from MolproComputation mc order by id asc"),
        @NamedQuery(name = "MolproComputation.findByApplication", query = "from MolproComputation mc where application  = :application"),
        @NamedQuery(name = "MolproComputation.findNotStarted", query = "from MolproComputation mc where started  = 0")
})
@Entity
public class MolproComputation  extends Model{

    /**
     * helper variables for switching between omistack and omistack_small
     */
    public static final String MOLPROTESTBED = "omistack";
    //public static final String MOLPROTESTBED = "clusterlab";


    /**
     * Serial version
     */
    private static final long serialVersionUID = 1L;

    /**
     * number of cores used by molpro
     */
    @Required
    public String title;

    /**
     * number of cores used by molpro
     */
    @Required
    public int numOfComputationCores;

    /**
     * molpro input file as string
     */
    @Required
    @Lob
    public String molproInput;

    /**
     * zip file of additional molpro scripts if required
     */
    @Lob
    public byte[] molproScripts;

    /**
     * zip file which contains the output of the computation
     */
    @Lob
    public byte[] output;

    /**
     * flag which indicates if the computation is already started
     */
    public boolean started;

    /**
     * the Molpro computation command, LCCSD has higher priority than dft
     */
    public String command;

    /**
     * the amount of RAM used by the Molpro Computation (not included OS and file caching)
     */
    public Long memory;

    /**
     * The cloud the uploads belong to.
     */
    @OneToOne
    @Required
    public Application application;


    public MolproComputation(){

    }

    public MolproComputation(String title, int numOfComputationCores, String molproInput, byte[] molproScripts, byte[] output, Application application, boolean started, String command, Long memory){
        this.title = title;
        this.numOfComputationCores = numOfComputationCores;
        this.molproInput = molproInput;
        this.molproScripts = molproScripts;
        this.output = output;
        this.application = application;
        this.started = started;
        this.command = command;
        this.memory = memory;
    }

    public MolproComputation(MolproComputationForm molproComputationForm) {

        this.setForm(molproComputationForm);
    }



    /**
     * Returns all molprocomputation entities stored in the database.
     *
     * @return list of all molprocomputation entities.
     */
    @SuppressWarnings("unchecked")
    public static List<MolproComputation> findAll() {
        return em().createNamedQuery("MolproComputation.findAll").getResultList();
    }

    /**
     * Returns a molprocomputation entity identified by an Application.
     *
     * @return a molprocomputation entity.
     */
    @SuppressWarnings("unchecked")
    public static MolproComputation findByApplication(Application application) {
        return (MolproComputation) em()
                .createNamedQuery("MolproComputation.findByApplication")
                .setParameter("application", application)
                .getSingleResult();
    }


    public MolproComputation setForm(MolproComputationForm form){
        this.title = form.title;
        this.numOfComputationCores = form.numOfComputationCores;
        this.molproInput = form.molproInput;
        this.molproScripts = form.molproScripts;
        return this;
    }

    /**
     * Retrieves the MolproComputation with the given id.
     *
     * @param id the id of the MolproComputation
     *
     * @return the MolproComputation with the given id.
     */
    public static MolproComputation findById(Long id) {
        return em().find(MolproComputation.class, id);
    }

    /**
     * Retrieves all MolproComputations which are not started, started = 0
     * @return list of not started molprocomputation entities.
     */
    public static List<MolproComputation> findNotStarted(){
        return em().createNamedQuery("MolproComputation.findNotStarted").getResultList();
    }

    
	public void save() {
		em().persist(this);
	    this.flush();
	    this.refresh();
	}
	
	public void merge() {
		em().merge(this);
	}
	
//	public void update(Long id) {
//		this.id = id;
//		em().merge(this);
//	}
	
	public void flush() {
	    em().flush();
	}
	
	public void refresh() {
	    em().refresh(this);
	}
	
	public void delete() {
		em().remove(this);
	}
	
	public static EntityManager em() {
		return JPA.em();
	}      
    
    
    
}
