package tk.tutorial.security.requestvuln.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.Authentication;

// Logging & Monitoring
// If not enabled, there is no monitoring of missing logins
// @Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

	private static final Logger LOG = LoggerFactory.getLogger("Spring Security issue");

	@Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
		Authentication auth = (Authentication) e.getAuthentication();
		String credentials = (String) auth.getCredentials();
		String name = (String) auth.getPrincipal();

		LOG.error(String.format("Failed Login with Password: %s - Username: %s", credentials, name));
	}
}
