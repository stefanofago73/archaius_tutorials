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
public class SimpleConfig {
	//
	private static final String DEFAULT_STRING_VALUE = "stringprop::NOT_SET";

	public static void main(String[] args) {

		Logger logger = logger(SimpleConfig.class);

		//
		// Since no config.properties is present, You will see also the line:
		// "To enable URLs as dynamic configuration sources, define System property archaius.configurationSource.additionalUrls or make config.properties available on classpath"
		//
		AbstractConfiguration config = ConfigurationManager.getConfigInstance();
		DynamicStringProperty stringProperty = DynamicPropertyFactory
				.getInstance().getStringProperty("stringprop",
						DEFAULT_STRING_VALUE);

		logger.info("config: {}\nproperty: {} ", config, stringProperty);
	}

}// END