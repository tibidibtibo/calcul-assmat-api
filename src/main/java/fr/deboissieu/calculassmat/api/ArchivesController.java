package fr.deboissieu.calculassmat.api;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.deboissieu.calculassmat.bl.ArchivesBlo;
import fr.deboissieu.calculassmat.configuration.LogCall;
import fr.deboissieu.calculassmat.model.archives.Archive;

@RestController
@RequestMapping("/archives")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ArchivesController {

	@Resource
	ArchivesBlo archivesBlo;

	@LogCall
	@RequestMapping(produces = "application/json", method = { RequestMethod.GET }, value = "/all")
	public List<Archive> getArchives() {
		return archivesBlo.getArchives();
	}
}
