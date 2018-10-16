package fr.deboissieu.calculassmat.api;

import java.security.Principal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.deboissieu.calculassmat.configuration.LogCall;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:8888")
public class AuthenticationController {

	@LogCall
	@RequestMapping(method = { RequestMethod.OPTIONS, RequestMethod.POST }, value = "/user")
	public Principal user(Principal user) {
		return user;
	}

}
