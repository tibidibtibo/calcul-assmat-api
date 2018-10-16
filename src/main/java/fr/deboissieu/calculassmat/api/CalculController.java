package fr.deboissieu.calculassmat.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.deboissieu.calculassmat.bl.CalculBlo;
import fr.deboissieu.calculassmat.bl.ValidationBlo;
import fr.deboissieu.calculassmat.configuration.LogCall;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

@RestController
@RequestMapping("/calcul")
@CrossOrigin(origins = "http://localhost:8888")
public class CalculController {

	@Resource
	private CalculBlo calculBlo;

	@Resource
	private ValidationBlo validationBlo;

	@LogCall
	@RequestMapping(produces = "application/json", method = RequestMethod.GET, value = "/{annee}/{mois}/{nomEmploye}")
	public SyntheseGarde calculer(@PathVariable("annee") String annee, @PathVariable("mois") String mois,
			@PathVariable("nomEmploye") String nomEmploye) throws Exception {
		int numeroMois = validationBlo.validerPathParamCalculMoisAnnee(mois);
		int numeroAnnee = validationBlo.validerPathParamCalculMoisAnnee(annee);
		String nomAssMat = validationBlo.validerPathParamNomAssmat(nomEmploye);
		return calculBlo.calculerSyntheseGarde(numeroMois, numeroAnnee, nomAssMat);
	}

	@LogCall
	@RequestMapping(produces = "application/json", method = { RequestMethod.OPTIONS,
			RequestMethod.POST }, value = "/file/{annee}/{mois}/{nomEmploye}")
	public SyntheseGarde calculerFichier(@RequestParam("files") MultipartFile[] files,
			@PathVariable("annee") String annee, @PathVariable("mois") String mois,
			@PathVariable("nomEmploye") String nomEmploye) throws Exception {

		LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		List<String> tempFileNames = new ArrayList<>();
		String tempFileName;
		FileOutputStream fo;

		try {
			for (MultipartFile file : files) {
				tempFileName = "/tmp/" + file.getOriginalFilename();
				tempFileNames.add(tempFileName);
				fo = new FileOutputStream(tempFileName);
				fo.write(file.getBytes());
				fo.close();
				map.add("files", new FileSystemResource(tempFileName));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String fileName : tempFileNames) {
			File f = new File(fileName);
			// TODO : lancer calcul ici
			System.out.println("Fichier OK ? = " + f.exists());
			f.delete();
		}
		//
		// int numeroMois = validationBlo.validerPathParamCalculMoisAnnee(mois);
		// int numeroAnnee = validationBlo.validerPathParamCalculMoisAnnee(annee);
		// String nomAssMat = validationBlo.validerPathParamNomAssmat(nomEmploye);

		return null;
		// return calculBlo.calculerSyntheseGarde(numeroMois, numeroAnnee, nomAssMat);
	}
}
