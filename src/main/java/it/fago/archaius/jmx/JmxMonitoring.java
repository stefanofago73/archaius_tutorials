package it.fago.archaius.jmx;

import static it.fago.archaius.util.Utils.anyKeyToStop;
import static it.fago.archaius.util.Utils.enableJmx;
import static it.fago.archaius.util.Utils.logger;

import java.io.IOException;

import org.slf4j.Logger;

import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicPropertyFactory;

/**
 * 
 * @author Stefano
 * 
 */
public class JmxMonitoring {
	//
	private static final String DEFAULT_FAKE_STRING_PROP = "fakeprop::NOT_SETTED";
	//
	private static final String DEFAULT_STRING_VALUE = "";

	public static void main(String[] args) {
		// enable jmx registration...
		enableJmx();
		// setup logger
		Logger logger = logger(JmxMonitoring.class);

		// define the known properties
		try {
			ConfigurationManager
					.loadCascadedPropertiesFromResources("newconfig");
		} catch (IOException e) {
			logger.error("problem loading newconfig.properties : {} ",
					e.toString(), e);
		}

		ConfigurationManager.getConfigInstance();

		logger.info(
				"The String Property: {} ",
				DynamicPropertyFactory.getInstance().getStringProperty(
						"stringprop", DEFAULT_STRING_VALUE));

		logger.info(
				"The Fake String Property: {} (is used the default value)",
				DynamicPropertyFactory.getInstance().getStringProperty(
						"fakeprop", DEFAULT_FAKE_STRING_PROP));

		anyKeyToStop(logger);
	}

}// END