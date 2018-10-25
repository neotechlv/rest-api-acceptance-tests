package lv.schoollibrary.tests.utils;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;

import com.mycila.guice.ext.closeable.CloseableModule;
import com.mycila.guice.ext.jsr250.Jsr250Module;
import lv.schoollibrary.tests.config.EnvironmentConfig;

import cucumber.api.guice.CucumberModules;
import cucumber.runtime.java.guice.InjectorSource;
import lv.neotech.tests.guice.CommonsConfigGuiceModule;

public class GuiceInjectorSource implements InjectorSource {

    public static ThreadLocal<Injector> INJECTOR = new ThreadLocal<>();

    @Override
    public Injector getInjector() {
        Injector injector = Guice.createInjector(Stage.PRODUCTION, CucumberModules.SCENARIO, new CloseableModule(),
                new Jsr250Module(), new CommonsConfigGuiceModule<>(EnvironmentConfig.class));
        INJECTOR.set(injector);
        return injector;
    }

}
