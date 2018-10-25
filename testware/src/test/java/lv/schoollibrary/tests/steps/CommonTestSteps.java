package lv.schoollibrary.tests.steps;

import lv.schoollibrary.tests.config.EnvironmentConfig;

import javax.inject.Inject;

import cucumber.api.java.en.Given;
import cucumber.runtime.java.guice.ScenarioScoped;
import lv.neotech.tests.utils.AppStartupWait;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;

@ScenarioScoped
public class CommonTestSteps {

    @Inject
    private EnvironmentConfig environmentConfig;


    @Given("^School library service is up and running")
    public void waitUntilAccessible() {
        waitForActuatorToRespond("Book service", environmentConfig.bookServiceHost(),
                environmentConfig.bookServicePort());
        waitForActuatorToRespond("Schoolkid service", environmentConfig.schoolkidServiceHost(),
                environmentConfig.schoolkidServicePort());
        waitForActuatorToRespond("Orders service", environmentConfig.ordersServiceHost(),
                environmentConfig.ordersServicePort());
    }

    private void waitForActuatorToRespond(String appName, String host, Integer port) {
        checkArgument(!isBlank(host), "Host is undefined");
        checkArgument(port != null, "Port is undefined");
        new AppStartupWait()
                .withAppName(appName)
                .withAppUrlToPoll(format("http://%s:%d/actuator/health", host, port))
                .withPollMethod(AppStartupWait.RequestMethod.GET)
                .withPollTimeoutInMillis(180_000)
                .withExpectedHttpStatus(200)
                .waitUntilStarted();
    }

}
