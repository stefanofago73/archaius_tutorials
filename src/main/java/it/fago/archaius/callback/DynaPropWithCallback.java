package it.fago.archaius.callback;

import static it.fago.archaius.util.Utils.anyKeyToStop;
import static it.fago.archaius.util.Utils.logger;
import static it.fago.archaius.util.Utils.setupForExternalUrl;
import static it.fago.archaius.util.Utils.systemPolling;

import org.apache.commons.configuration.AbstractConfiguration;
import org.slf4j.Logger;

import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;

/**
 * 
 * @author Stefano
 * 
 */
public class DynaPropWithCallback {

	//
	private static final String DEFAULT_STRING_VALUE="";
	
	public static void main(String[] args) throws Exception {

		Logger logger = logger(DynaPropWithCallback.class);

		// What follow are the parameter as System properties to set timing
		//
		systemPolling(1000, 10000);

		//
		// Setup external file
		// This File has priority on invoked newconfig.properties that follow...
		// The System Property is configured by the String:
		// "archaius.configurationSource.additionalUrls"
		//
		setupForExternalUrl();

		AbstractConfiguration config = ConfigurationManager.getConfigInstance();
		// property setup
		DynamicStringProperty p1 = DynamicPropertyFactory.getInstance()
				.getStringProperty("stringprop", DEFAULT_STRING_VALUE);
		p1.addCallback(callaback(p1,logger));

		logger.info("config: {}\nproperty: {}", config, p1);

		anyKeyToStop(logger);
	}

	// =============================================
	//
	//
	//
	// =============================================

	private static final Runnable callaback(
			final DynamicStringProperty source,
			final Logger logger) {
		return new Runnable() {
			public void run() {
				logger.info("{} is changed at: {} with value: {} ",
						source.getName(), 
						source.getChangedTimestamp(),
						source.get());
			}
		};
	}

}// END