package fr.deboissieu.calculassmat.api;

import java.security.Principal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.deboissieu.calculassmat.configuration.LogCall;
import fr.deboissieu.calculassmat.model.authentication.ServerAlive;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {

	@LogCall
	@RequestMapping(method = { RequestMethod.OPTIONS, RequestMethod.GET }, value = "/user")
	public Principal user(Principal user) {
		return user;
	}

	@LogCall
	@RequestMapping(method = { RequestMethod.OPTIONS, RequestMethod.GET }, value = "/alive")
	public ServerAlive isServerAlive() {
		return new ServerAlive();
	}
}
