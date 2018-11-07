package fr.deboissieu.calculassmat.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.deboissieu.calculassmat.bl.UserBlo;
import fr.deboissieu.calculassmat.configuration.LogCall;
import fr.deboissieu.calculassmat.model.authentication.User;

@RestController
@CrossOrigin(origins = "http://localhost:8888", maxAge = 3600)
public class UserController {

	@Autowired
	private UserBlo userBlo;

	@LogCall
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public List<User> listUser() {
		// FIXME : faire un DTO sans le password ;-)
		return userBlo.findAll();
	}

	@LogCall
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public User getOne(@PathVariable(value = "id") String hexid) {
		// FIXME : faire un DTO sans le password ;-)
		return userBlo.findById(hexid);
	}
}
