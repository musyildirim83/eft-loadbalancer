package tr.com.eft.odev.loadbalancer.auth;

import io.dropwizard.auth.Authorizer;
import tr.com.eft.odev.loadbalancer.api.User;

public class LbAuthorizer implements Authorizer<User> {

	@Override
	public boolean authorize(User user, String role) {
		return user.getRoles() != null && user.getRoles().contains(role);
	}
}
