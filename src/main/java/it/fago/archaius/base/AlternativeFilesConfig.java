package it.fago.archaius.base;

import static it.fago.archaius.util.Utils.logger;
import static it.fago.archaius.util.Utils.setupNewDefaultConfigFileName;

import org.apache.commons.configuration.AbstractConfiguration;
import org.slf4j.Logger;

import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.config.sources.URLConfigurationSource;

/**
 * 
 * @author Stefano
 * 
 */
public class AlternativeFilesConfig {
	//
	private static final String DEFAULT_STRING_VALUE = "";

	public static void main(String[] args) throws Exception {

		Logger logger = logger(AlternativeFilesConfig.class);

		//
		// The default configuration file is on the classpath: config.properties
		// If I want to change this default I can set the system property:
		// "archaius.configurationSource.defaultFileName"
		// that will reported also in
		// URLConfigurationSource.DEFAULT_CONFIG_FILE_FROM_CLASSPATH
		//
		setupNewDefaultConfigFileName();

		logger.info(
				"Now the default configuration file is: {} while the standard file is: {} ",
				URLConfigurationSource.DEFAULT_CONFIG_FILE_FROM_CLASSPATH,
				URLConfigurationSource.DEFAULT_CONFIG_FILE_NAME);

		AbstractConfiguration config = ConfigurationManager.getConfigInstance();
		DynamicStringProperty p1 = DynamicPropertyFactory.getInstance()
				.getStringProperty("stringprop", DEFAULT_STRING_VALUE);

		logger.info("config: {}\nproperty: {}", config, p1);
	}

}// END