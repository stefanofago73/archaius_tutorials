package it.fago.archaius.base;

import static it.fago.archaius.util.Utils.logger;
import static it.fago.archaius.util.Utils.setupForExternalUrl;

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
public class AlternativeFileConfigPriority {

	public static void main(String[] args) throws Exception {

		Logger logger = logger(AlternativeFileConfigPriority.class);

		//
		// Setup external file
		// This File has priority on invoked newconfig.properties that follow...
		// The System Property is configured by the String:
		// "archaius.configurationSource.additionalUrls"
		//
		setupForExternalUrl();

		//
		// The default configuration file is on the classpath: config.properties
		// If I want to change this default I can set the system property:
		// "archaius.configurationSource.defaultFileName"
		// that will reported also in
		// URLConfigurationSource.DEFAULT_CONFIG_FILE_FROM_CLASSPATH
		//
		ConfigurationManager.loadCascadedPropertiesFromResources("newconfig");
		AbstractConfiguration config = ConfigurationManager.getConfigInstance();
		DynamicStringProperty p1 = DynamicPropertyFactory.getInstance()
				.getStringProperty("stringprop", "");

		logger.info("config: {}\nproperty: {}", config, p1);

	}

	// =============================================
	//
	//
	//
	// =============================================

}// END