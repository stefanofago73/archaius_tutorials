package it.fago.archaius.jmx;

import static it.fago.archaius.util.Utils.enableJmx;
import static it.fago.archaius.util.Utils.logger;
import static it.fago.archaius.util.Utils.setupForExternalUrl;
import static it.fago.archaius.util.Utils.systemPolling;

import org.slf4j.Logger;

import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicPropertyFactory;

/**
 * 
 * @author Stefano
 *
 */
public class JmxMonitoringWithExternal {
	//
	private static final String DEFAULT_STRING_VALUE = "";
	//
	private static final String DEFAULT_FAKE_STRING_PROP = "fakeprop::NOT_SETTED";

	public static void main(String[] args) throws Exception {
		// Property used to enable or disable JMX publishing
		enableJmx();
		// When You use "external" url, the default imply starting some polling
		// What follow are the parameter as System properties to set timing
		//
		systemPolling(1000, 10000);

		// use an external URL
		setupForExternalUrl();

		// setup logger
		final Logger logger = logger(JmxMonitoringWithExternal.class);

		ConfigurationManager.getConfigInstance();

		while (true) {
			logger.info(
					"The String Property: {} ",
					DynamicPropertyFactory.getInstance().getStringProperty(
							"stringprop", DEFAULT_STRING_VALUE));

			logger.info(
					"The Fake String Property: {} (is used the default value)",
					DynamicPropertyFactory.getInstance().getStringProperty(
							"fakeprop", DEFAULT_FAKE_STRING_PROP));
			Thread.sleep(2000);
		}
	}

}// END