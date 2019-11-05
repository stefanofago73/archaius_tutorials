package it.fago.archaius.customclass;

import static it.fago.archaius.util.Utils.logger;

import org.slf4j.Logger;

/**
 * 
 * @author Stefano
 * 
 */
public class CustomDeployContextFactory {
	//
	private static Logger logger = logger(CustomDeployContextFactory.class);
	//
	private static final CustomDeployContext instance = new CustomDeployContext();

	public static CustomDeployContext getInstance() {
		logger.info("using instance from custom factory...");
		return instance;
	}

}// END