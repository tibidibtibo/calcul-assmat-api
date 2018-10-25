package fr.deboissieu.calculassmat.model.authentication;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerAlive implements Serializable {

	private static final long serialVersionUID = 1554167495060615935L;

	private boolean alive = true;
}
