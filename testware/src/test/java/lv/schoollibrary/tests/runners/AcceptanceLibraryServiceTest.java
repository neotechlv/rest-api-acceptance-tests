package lv.schoollibrary.tests.runners;


import com.google.common.io.Resources;

import lv.schoollibrary.tests.config.EnvironmentConfig;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import cucumber.api.CucumberOptions;
import lombok.extern.slf4j.Slf4j;
import lv.neotech.tests.configuration.TestConfigurationProvider;
import lv.neotech.tests.cucumber.ProfiledCucumberRunner;
import lv.neotech.tests.cucumber.TestEnvironmentProfile;
import lv.neotech.tests.utils.ServiceEndpoint;

@Slf4j
@RunWith(ProfiledCucumberRunner.class)
@TestEnvironmentProfile(name = "ci")
@CucumberOptions(
        features = "classpath:features",
        plugin = {"pretty", "io.qameta.allure.cucumberjvm.AllureCucumberJvm"},
        junit = {"--filename-compatible-names"},
        glue = { "lv.schoollibrary.tests.steps", "lv.schoollibrary.tests.hooks" })
public class AcceptanceLibraryServiceTest {

    private static final ServiceEndpoint BOOK_SERVICE_ENDPOINT = new ServiceEndpoint("book-service", 14001);

    private static final ServiceEndpoint SCHOOLKID_SERVICE_ENDPOINT = new ServiceEndpoint("schoolkid-service", 14002);

    private static final ServiceEndpoint ORDER_SERVICE_ENDPOINT = new ServiceEndpoint("order-service", 14003);


    @ClassRule
    public static DockerComposeContainer ECOSYSTEM =
            new DockerComposeContainer(locateInClasspath("docker/ci-docker-compose.yml"))
                    .withExposedService(BOOK_SERVICE_ENDPOINT.getHost(), 1, BOOK_SERVICE_ENDPOINT.getPort(),
                            Wait.forHttp("/actuator/health").forStatusCode(200))
                    .withExposedService(SCHOOLKID_SERVICE_ENDPOINT.getHost(), 1, SCHOOLKID_SERVICE_ENDPOINT.getPort(),
                            Wait.forHttp("/actuator/health").forStatusCode(200))
                    .withExposedService(ORDER_SERVICE_ENDPOINT.getHost(), 1, ORDER_SERVICE_ENDPOINT.getPort(),
                            Wait.forHttp("/actuator/health").forStatusCode(200))
                    .withLocalCompose(true)
                    .withPull(false)    // true if should be pulled from registry
                    .withTailChildContainers(true);

    @BeforeClass
    public static void populateExposedPorts() {
        EnvironmentConfig environmentConfig = TestConfigurationProvider.fromDefaults(EnvironmentConfig.class, false);

        environmentConfig.setBookServicePort(realServicePort(BOOK_SERVICE_ENDPOINT));
        environmentConfig.setSchoolKidServicePort(realServicePort(SCHOOLKID_SERVICE_ENDPOINT));
        environmentConfig.setOrdersServicePort(realServicePort(ORDER_SERVICE_ENDPOINT));
    }

    private static Integer realServicePort(ServiceEndpoint serviceEndpoint) {
        String containerHost = serviceEndpoint.getHost();
        Integer containerPort = serviceEndpoint.getPort();

        Integer servicePort = ECOSYSTEM.getServicePort(containerHost + "_1", containerPort);

        log.info("{} host port is {}", containerHost, servicePort);

        return servicePort;
    }

    private static File locateInClasspath(String pathToFile) {
        URL resource = Resources.getResource(pathToFile);
        try {
            return new File(resource.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
