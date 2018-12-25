package fr.deboissieu.calculassmat.model.certification;

import java.io.Serializable;
import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificationRequest implements Serializable {

	private static final long serialVersionUID = -6706713179488962010L;

	@NotNull
	@Size(min = 1)
	@Valid
	@JsonProperty("saisies")
	Collection<SaisieCertification> saisies;
}
