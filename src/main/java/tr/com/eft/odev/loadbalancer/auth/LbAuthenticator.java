package tr.com.eft.odev.loadbalancer.auth;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import tr.com.eft.odev.loadbalancer.api.User;

public class LbAuthenticator implements Authenticator<BasicCredentials, User> {

	private static final Set<String> questRoles = ImmutableSet.of();
	private static final Set<String> memberRoles = ImmutableSet.of("MEMBER");
	private static final Set<String> adminRoles = ImmutableSet.of("ADMIN", "MEMBER");

	private static final Map<String, Set<String>> VALID_USERS = ImmutableMap.of("guest", questRoles, "mustafa",
			memberRoles, "admin", adminRoles);

	@Override
	public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
		if (VALID_USERS.containsKey(credentials.getUsername()) && "secret".equals(credentials.getPassword())) {
			return Optional.of(new User(credentials.getUsername(), VALID_USERS.get(credentials.getUsername())));
		}
		return Optional.empty();
	}

}
