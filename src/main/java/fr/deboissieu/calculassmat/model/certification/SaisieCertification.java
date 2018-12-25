package fr.deboissieu.calculassmat.model.certification;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaisieCertification implements Serializable {

	private static final long serialVersionUID = -6972352961552266022L;

	@NotNull
	@Size(min = 1)
	private String id;
}
