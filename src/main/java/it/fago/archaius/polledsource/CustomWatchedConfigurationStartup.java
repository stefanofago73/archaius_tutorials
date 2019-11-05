package it.fago.archaius.polledsource;

import static it.fago.archaius.util.Utils.anyKeyToStop;
import static it.fago.archaius.util.Utils.logger;
import static it.fago.archaius.util.Utils.pauseFor;

import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.slf4j.Logger;

import com.netflix.config.ConcurrentCompositeConfiguration;
import com.netflix.config.ConcurrentMapConfiguration;
import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicLongProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicWatchedConfiguration;

/**
 * 
 * @author Stefano
 * 
 */
public class CustomWatchedConfigurationStartup {

	public static void main(String[] args) {
		//
		Logger logger = logger(CustomWatchedConfigurationStartup.class);

		// setup system properties
		System.setProperty("test.key4", "test.value4-system");
		System.setProperty("test.key5", "test.value5-system");
		final ConcurrentMapConfiguration systemConfig = new ConcurrentMapConfiguration();
		final CustomWatchedConfigurationSource source = new CustomWatchedConfigurationSource();
		final DynamicWatchedConfiguration watchedConfig = new DynamicWatchedConfiguration(
				source);

		source.addUpdateListener(new CustomWatchedUpdateListener());
		watchedConfig.addConfigurationListener(new ConfigurationListener() {
			
			public void configurationChanged(ConfigurationEvent chdEvt) {
				logger(getClass()).info("\n[configurationChanged - type:{} source: {} before: {}]: {} = {} ",chdEvt.getType(),chdEvt.getSource(),chdEvt.isBeforeUpdate(),chdEvt.getPropertyName(),chdEvt.getPropertyValue());
			}
		});
		
		source.init();
		

		systemConfig.loadProperties(System.getProperties());

		ConcurrentMapConfiguration mapConfig = new ConcurrentMapConfiguration();
		mapConfig.addProperty("test.key1", "test.value1-map");
		mapConfig.addProperty("test.key2", "test.value2-map");
		mapConfig.addProperty("test.key3", "test.value3-map");
		mapConfig.addProperty("test.key4", "test.value4-map");

		final ConcurrentCompositeConfiguration compositeConfig = new ConcurrentCompositeConfiguration();
		compositeConfig
				.addConfiguration(watchedConfig, "Custom Watched Source");
		compositeConfig.addConfiguration(mapConfig, "Map configuration");
		compositeConfig.addConfiguration(systemConfig, "System configuration");

		ConfigurationManager.install(compositeConfig);

		pauseFor(2000L);
		
		DynamicLongProperty prop = DynamicPropertyFactory.getInstance().getLongProperty("TS", -1L);
		logger.info("On Startup we see: {} ",prop.get());

		anyKeyToStop(logger);
		
		prop = DynamicPropertyFactory.getInstance().getLongProperty("TS", -1L);
		logger.info("Before down we see: {} ",prop.get());

		source.destroy();
	}

}// END