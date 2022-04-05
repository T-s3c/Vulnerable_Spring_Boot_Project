package tk.tutorial.security.requestvuln.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

	private static final Logger LOG = LoggerFactory.getLogger("Spring Security issue");

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		Authentication auth = event.getAuthentication();
		if (auth.getAuthorities().iterator().next().getAuthority().equals("ROLE_ADMIN")) {
			LOG.info(String.format("Successful admin login from: %s", auth.getName()));
		} else
		{
			LOG.info(String.format("Successful user login from: %s", auth.getName()));
		}
	}


}
