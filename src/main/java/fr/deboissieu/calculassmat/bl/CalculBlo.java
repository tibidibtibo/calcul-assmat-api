package fr.deboissieu.calculassmat.bl;

import java.util.stream.Stream;

import javax.ws.rs.core.Response;

import fr.deboissieu.calculassmat.model.SaisieJournaliere;

public interface CalculBlo {

	Response calculerSyntheseGarde(int mois);

	Stream<SaisieJournaliere> streamCalculSynthese(int mois);
}
