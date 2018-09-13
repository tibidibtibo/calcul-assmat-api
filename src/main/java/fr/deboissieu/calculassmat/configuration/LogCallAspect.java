package fr.deboissieu.calculassmat.configuration;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 
 * @author tdeboissieu
 *
 */
@Aspect
@Component
public class LogCallAspect {

	/**
	 * Intercepte les méthodes public annotées de @LogCall et trace les débuts/fins.
	 * Pas possible d'interpecter les méthodes privées.
	 * 
	 * @param pjp
	 *            le joint point
	 * @param logCall
	 *            l'annotation interceptée
	 * @return le résultat de la fonction intercepté
	 * @throws Throwable
	 */
	@Around("@annotation(logCall)")
	public Object executeInApplicationContext(final ProceedingJoinPoint pjp, final LogCall logCall) throws Throwable {
		Logger logger = LogManager.getLogger(logCall.loggerName());
		Level level = Level.valueOf(logCall.level().name());

		Object proceed;
		if (logger.isEnabled(level)) {
			String className = pjp.getTarget().getClass().getSimpleName();
			String methodName = pjp.getSignature().getName();

			logger.log(level, "DEBUT {}.{}({})", className, methodName, pjp.getArgs());

			long start = System.currentTimeMillis();
			proceed = pjp.proceed();
			long end = System.currentTimeMillis();

			logger.log(level, "FIN {}.{}()[{}ms]", className, methodName, end - start);
		} else {
			proceed = pjp.proceed();
		}

		return proceed;
	}
}
