package it.fago.archaius.polledsource;

import static it.fago.archaius.util.Utils.alternativeURL;
import static it.fago.archaius.util.Utils.disableDefaultConfiguration;
import static it.fago.archaius.util.Utils.logger;

import org.slf4j.Logger;

import com.netflix.config.AbstractPollingScheduler;
import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicConfiguration;
import com.netflix.config.DynamicLongProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.config.FixedDelayPollingScheduler;

/**
 * 
 * @author Stefano
 *
 */
public class PollingConfigurationStartup {

	public static void main(String[] args) throws Exception {

		disableDefaultConfiguration();
		alternativeURL();

		Logger logger = logger(PollingConfigurationStartup.class);

		// ===========================================================================
		//
		//
		//
		// ===========================================================================

		CustomPolledConfigSource source = new CustomPolledConfigSource();
		AbstractPollingScheduler scheduler = new FixedDelayPollingScheduler(
				3000, 2000, true);
		DynamicConfiguration dconfig = new DynamicConfiguration(source,
				scheduler);

		scheduler.addPollListener(new CustomPollingListener());

		ConfigurationManager.install(dconfig);

		DynamicStringProperty sprop = DynamicPropertyFactory.getInstance()
				.getStringProperty("TS_DATA", "none");
		//
		// to de-comment to see the effect on pollresult
		// sprop.addValidator(new PropertyChangeValidator() {
		// int counter = 0;
		//
		// public void validate(String newValue) throws ValidationException {
		// if (counter++ == 4) {
		// counter = 0;
		// throw new ValidationException("Bastard Validation!...");
		// }
		// logger.info("{} accept the value : {} ", this, newValue);
		// }
		// });

		DynamicLongProperty lprop = DynamicPropertyFactory.getInstance()
				.getLongProperty("TS", 0);
		DynamicStringProperty p1 = DynamicPropertyFactory.getInstance()
				.getStringProperty("stringprop", "");

		while (true) {
			logger.info("\n Properties : {}  -   {}  - {}", sprop, lprop, p1);
			Thread.sleep(1000L);
		}
	}
}// END