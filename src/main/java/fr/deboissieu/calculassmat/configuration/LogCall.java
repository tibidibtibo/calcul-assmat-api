package fr.deboissieu.calculassmat.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Component
public @interface LogCall {

	String loggerName() default "fr.deboissieu.calculassmat";

	/**
	 * Une valeur de {@link LoggerLevel}. Valeur par defaut :
	 * {@link LoggerLevel#DEBUG}
	 *
	 * @return le niveau de log
	 */
	LoggerLevel level() default LoggerLevel.DEBUG;

	/**
	 * Niveaux de log possibles
	 *
	 * @author Romain GERVAIS
	 *
	 */
	public enum LoggerLevel {
		TRACE, DEBUG, INFO, WARN, ERROR, FATAL
	}

}
