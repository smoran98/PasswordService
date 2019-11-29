package ie.gmit.ds;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;

public class App extends Application<Configuration> {

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {

        final UserAccountApiResource resource = new UserAccountApiResource();

        //HealthCheck criteria
        final UserHealthCheck healthCheck = new UserHealthCheck();
        environment.healthChecks().register("example", healthCheck);

        environment.jersey().register(resource);
    }
}