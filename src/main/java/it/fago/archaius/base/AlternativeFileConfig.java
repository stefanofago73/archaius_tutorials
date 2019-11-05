package it.fago.archaius.base;

import static it.fago.archaius.util.Utils.logger;

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
public class AlternativeFileConfig {
	//
	private static final String DEFAULT_STRING_VALUE = "stringprop::NOT_SET";

	public static void main(String[] args) throws Exception {
		Logger logger = logger(AlternativeFileConfig.class);
		//
		// it will be used an external file to configure the properties at
		// startup
		//
		ConfigurationManager.loadCascadedPropertiesFromResources("newconfig");
		//
		// Create Configuration from loaded file...
		//
		AbstractConfiguration config = ConfigurationManager.getConfigInstance();
		//
		// defining & using a dyna prop...
		//
		DynamicStringProperty stringProperty = DynamicPropertyFactory
				.getInstance().getStringProperty("stringprop",
						DEFAULT_STRING_VALUE);

		logger.info("config: {}\nproperty: {} ", config, stringProperty);
	}

}// END