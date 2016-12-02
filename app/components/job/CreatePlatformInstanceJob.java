package components.job;

import cloud.colosseum.ColosseumComputeService;
import models.PlatformInstance;
import models.Tenant;
import models.service.ModelService;
import models.service.RemoteModelService;
import play.Logger;
import play.Play;
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


        //TODO: get Platform Instance froom modelService

        //TODO get Platform Environment from via Instance

        //TODO get platformRuntime

        //TODO: get language ( should be Java)
        String platformLanguage = ""

        //TODO get platform from platformInstance to get apiEndpoint
        String platformEndpoint = "";
        String platformName = "";

        //TODO get Platform credentials
        String user = "";
        String secret = "";

        //TODO get gitURL from component
        String gitURL = "";



        try {
            RestClient client = new RestClient(new URL(pulEndpoint));

            LOGGER.debug("Connecting to platform provider: " + platformEndpoint);

            ProviderClient provider;
            CredentialsMap credentials = CredentialsMap.builder()
                    .item(USER, user)
                    .item(PASSWORD, secret)
                    .build();
            provider = client.getProvider(platformName, credentials);

            LOGGER.debug("Deploying application to platform...");

            ApplicationToCreate appToCreate = new ApplicationToCreate(
                    APP_NAME,
                    new URL(gitURL),
                    platformLanguage); //TODO check if ROMAN has implemented that in the PUL, otherwise use IStandaloneCartridge.NAME_JBOSSEWS but this requires a dependecy to Openshift Client
            Application createdApp = provider.createApplication(appToCreate);

            //TODO: fetch endpoint of deployed app and store the platformInstance entity, add the endpoint to the platformInstance which has an endpoint column

        } catch (Throwable throwable) {
            LOGGER.error("Error while creating PUL client!");
            throw new JobException(throwable);
        }

    }
}

