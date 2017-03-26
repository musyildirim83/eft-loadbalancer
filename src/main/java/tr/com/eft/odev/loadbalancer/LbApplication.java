package tr.com.eft.odev.loadbalancer;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import com.kjetland.ddsl.dropwizard.DdslService;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import tr.com.eft.odev.loadbalancer.api.User;
import tr.com.eft.odev.loadbalancer.auth.LbAuthenticator;
import tr.com.eft.odev.loadbalancer.auth.LbAuthorizer;
import tr.com.eft.odev.loadbalancer.config.LbConfiguration;
import tr.com.eft.odev.loadbalancer.filter.LogFilter;
import tr.com.eft.odev.loadbalancer.health.LbHealthCheck;
import tr.com.eft.odev.loadbalancer.resources.LbResource;

public class LbApplication extends Application<LbConfiguration> {

	public static void main(String[] args) throws Exception {
		new LbApplication().run(args);
	}

	@Override
	public String getName() {
		return "loadbalancer";
	}

	@Override
	public void initialize(Bootstrap<LbConfiguration> bootstrap) {
	}

	@Override
	public void run(LbConfiguration conf, Environment env) throws Exception {
		env.healthChecks().register("loadbalancer", new LbHealthCheck());

		DdslService ddslService = new DdslService(conf.getDdslConfig());
		env.lifecycle().addServerLifecycleListener(ddslService);
		env.jersey().register(new LbResource(conf, ddslService));

		env.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
				.setAuthenticator(new LbAuthenticator()).setAuthorizer(new LbAuthorizer()).buildAuthFilter()));
		env.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
		env.jersey().register(RolesAllowedDynamicFeature.class);

		env.jersey().register(new LogFilter());
	}

}
