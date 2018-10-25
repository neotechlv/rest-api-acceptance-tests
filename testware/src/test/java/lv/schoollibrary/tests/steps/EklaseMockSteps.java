package lv.schoollibrary.tests.steps;

import lv.schoollibrary.tests.EklaseMock;

import org.junit.Before;

import java.util.UUID;

import javax.inject.Inject;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.runtime.java.guice.ScenarioScoped;

@ScenarioScoped
public class EklaseMockSteps {

    @Inject
    private EklaseMock eklaseMock;

    @Inject
    private DataHolder dataHolder;

    @Before
    public void setUp() {
        eklaseMock.resetAllStubs();
    }

    @Given("^schoolkid (\\w+) (\\w+) exists")
    public void verifySuccess(String name, String surname) {
        String foundKidId = UUID.randomUUID().toString();
        eklaseMock.mockSearchSuccess(foundKidId, name, surname);

        dataHolder.addSchoolKid(foundKidId, name, surname);
    }

    @Given("^eclass service failure$")
    public void verifySimulateServerFailure() {
        eklaseMock.simulateFailure();
    }

    @After
    public void tearDown() {
        eklaseMock.verifyNoRequestMismatches();
        eklaseMock.resetAllStubs();
    }

}
