package it.fago.archaius.polledsource;

import static it.fago.archaius.util.Utils.logger;

import org.slf4j.Logger;

import com.netflix.config.PollListener;
import com.netflix.config.PollResult;

public class CustomPollingListener implements PollListener{
//
	private static Logger logger = logger(CustomPollingListener.class);
	
	public void handleEvent(
			final EventType evt, 
			final PollResult result,
			final Throwable exception) {
		
		if (evt == EventType.POLL_SUCCESS) {
			logger.info(
					"\nPOLL_LISTENER\nevent type: {}\nresult: has changed {} - changed {} - check: {}\nexception : {} ",
					evt, 
					result.hasChanges(), 
					result.getChanged(),
					result.getCheckPoint(), "none");
			return;
		}
		
		logger.info(
				"\nPOLL_LISTENER\nevent type: {}\nresult: has changed {} - changed {} - check: {}\nexception : {} ",
				evt, 
				"none", 
				"none", 
				"none", 
				exception.toString());
	}

}//END