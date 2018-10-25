package fr.deboissieu.calculassmat.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parametrage")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ParametrageController {

}
