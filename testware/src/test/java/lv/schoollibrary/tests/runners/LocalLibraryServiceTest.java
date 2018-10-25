package lv.schoollibrary.tests.runners;


import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import lv.neotech.tests.cucumber.ProfiledCucumberRunner;
import lv.neotech.tests.cucumber.TestEnvironmentProfile;

@RunWith(ProfiledCucumberRunner.class)
@TestEnvironmentProfile(name = "local")
@CucumberOptions(
        features = "classpath:features",
        plugin = {"pretty", "io.qameta.allure.cucumberjvm.AllureCucumberJvm"},
        junit = {"--filename-compatible-names"},
        glue = { "lv.schoollibrary.tests.steps", "lv.schoollibrary.tests.hooks"})
public class LocalLibraryServiceTest {

}
