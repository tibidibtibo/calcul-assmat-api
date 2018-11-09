package fr.deboissieu.calculassmat.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.deboissieu.calculassmat.bl.UserBlo;
import fr.deboissieu.calculassmat.configuration.JwtTokenUtil;
import fr.deboissieu.calculassmat.model.authentication.AuthToken;
import fr.deboissieu.calculassmat.model.authentication.LoginUser;
import fr.deboissieu.calculassmat.model.authentication.ServerAlive;
import fr.deboissieu.calculassmat.model.authentication.User;

@RestController
@RequestMapping("/token")
@CrossOrigin(origins = "http://localhost:8888", maxAge = 3600)
public class AuthenticationController {

	@Autowired
	private UserBlo userBlo;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@RequestMapping(value = "/generate-token", method = RequestMethod.POST)
	public ResponseEntity<?> register(@RequestBody LoginUser loginUser) throws AuthenticationException {

		final Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginUser.getUsername(),
						loginUser.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final User user = userBlo.findOne(loginUser.getUsername());
		final String token = jwtTokenUtil.generateToken(user);
		return ResponseEntity.ok(new AuthToken(token));
	}

	@RequestMapping(method = { RequestMethod.GET }, value = "/alive")
	public ServerAlive isServerAlive() {
		return new ServerAlive();
	}
}
