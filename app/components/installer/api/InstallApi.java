package components.installer.api;

/**
 * Created by Daniel Seybold on 19.05.2015.
 */
public interface InstallApi {

    /**
     * Place all download commands in the download queue
     */
    public void initSources();

    /**
     * download all sources in parallel
     */
    public void downloadSources();

    /**
     * extract Java archive, set Java environement
     */
    public void installJava();

    /**
     * create the visor configuration file and start visor
     */
    public void installVisor();

    /**
     * extract and start kairosDB
     */
    public void installKairosDb();

    /**
     * download, setup and start the LifecycleAgent (Unix:Docker, Windows: not yet decided)
     */
    public void installLifecycleAgent();

    /**
     * Download and install all necessary software for cloudiator (java, visor, lifecycle agent)
     */
    public void installAll();



}

