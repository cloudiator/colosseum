package components.job;

import eu.atos.paas.client.RestClient;
import eu.atos.paas.data.Application;
import eu.atos.paas.data.ApplicationToCreate;
import eu.atos.paas.data.CredentialsMap;

import cloud.colosseum.ColosseumComputeService;
import models.PlatformComponent;
import models.PlatformCredential;
import models.PlatformEnvironment;
import models.PlatformInstance;
import models.PlatformRuntime;
import models.Tenant;
import models.service.ModelService;
import models.service.RemoteModelService;
import play.Logger;
import play.Play;
import play.db.jpa.JPAApi;
import util.logging.Loggers;

import java.net.URL;

/**
 * Created by Daniel Seybold on 28.11.2016.
 */
public class CreatePlatformInstanceJob extends AbstractRemoteResourceJob<PlatformInstance> {

    private Logger.ALogger LOGGER = Loggers.of(Loggers.PLATFORM_JOB);

    public CreatePlatformInstanceJob(JPAApi jpaApi, PlatformInstance platformInstance, RemoteModelService<PlatformInstance> modelService,
                                     ModelService<Tenant> tenantModelService, ColosseumComputeService colosseumComputeService, Tenant tenant) {
        super(jpaApi, platformInstance, modelService, tenantModelService, colosseumComputeService, tenant);
    }

    @Override
    public boolean canStart() throws JobException {
        return true;
    }

    @Override
    protected void doWork(ModelService<PlatformInstance> modelService, ColosseumComputeService computeService) throws JobException {

        LOGGER.debug("Starting Platform deployment...");

        String pulEndpoint = Play.application().configuration()
                .getString("colosseum.paas.service.endpoint");

        LOGGER.debug("Getting PUL client from endpoint: " + pulEndpoint);


        PlatformInstance platformInstance = this.getT();

        PlatformEnvironment platformEnvironment = platformInstance.getPlatformEnvironment();

        PlatformRuntime platformRuntime = platformEnvironment.getPlatformRuntime();

        String platformLanguage = platformRuntime.getLanguage();

        String platformEndpoint = platformInstance.getPlatformEnvironment().getPlatform().getEndpoint().get(); //TODO check for empty
        String platformName = platformInstance.getPlatformEnvironment().getPlatform().getName();

        PlatformCredential platformCredential = platformInstance.getPlatformEnvironment().getPlatform().getPlatformCredentials().get(0);//TODO which one to choose and check for null
        String user = platformCredential.getUser();
        String secret = platformCredential.getSecret();

        PlatformComponent component = (PlatformComponent) platformInstance.getApplicationComponent().getComponent();  //TODO dangerous cast
        String gitURL = component.getGitUrl(); //TODO what if artifact is set?
        String applicationName = component.getName();



        try {
            RestClient client = new RestClient(new URL(pulEndpoint));

            LOGGER.debug("Connecting to platform provider: " + platformEndpoint);

            RestClient.ProviderClient provider;
            CredentialsMap credentials = CredentialsMap.builder()
                    .item("USER", user)
                    .item("PASSWORD", secret)
                    .build();
            provider = client.getProvider(platformName, credentials);

            LOGGER.debug("Deploying application to platform...");

            ApplicationToCreate appToCreate = new ApplicationToCreate(
                    applicationName, // TODO name from component?
                    new URL(gitURL),
                    platformLanguage); //TODO check if ROMAN has implemented that in the PUL, otherwise use IStandaloneCartridge.NAME_JBOSSEWS but this requires a dependecy to Openshift Client
            Application createdApp = provider.createApplication(appToCreate);

            platformInstance.setEndpoint(createdApp.getUrl().toString()); // TODO is this all after the application is deployed?

            modelService.save(platformInstance);

        } catch (Throwable throwable) {
            LOGGER.error("Error while creating PUL client!");
            throw new JobException(throwable);
        }

    }
}

