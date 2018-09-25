package fr.deboissieu.calculassmat.bl;

import java.util.stream.Stream;

import javax.ws.rs.core.Response;

import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;

public interface CalculBlo {

	Response calculerSyntheseGarde(int mois, int numeroAnnee);

	Stream<SaisieJournaliere> streamCalculSynthese(int mois);
}
