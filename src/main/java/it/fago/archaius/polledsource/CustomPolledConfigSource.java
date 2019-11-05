package it.fago.archaius.polledsource;

import static it.fago.archaius.util.Utils.logger;

import java.util.HashMap;

import org.slf4j.Logger;

import com.netflix.config.PollResult;
import com.netflix.config.PolledConfigurationSource;

/**
 * 
 * @author Stefano
 * 
 */
public class CustomPolledConfigSource implements PolledConfigurationSource {
	//
	private HashMap<String, Object> changingProps = new HashMap<String, Object>();
	//
	private Long lastPoll = System.currentTimeMillis();
	//
	private Logger logger = logger(CustomPolledConfigSource.class);

	public PollResult poll(boolean initial, Object checkPoint) throws Exception {

		if (initial) {
			logger.info("\nPOLL method : initial result --> creating NO_CHANGE POLL_RESULT...");
			//
			// in the logic of a WatchedUpdateResult
			// a full with NULL means that nothing is changed...
			// In the case of incremental changes, something
			// result changed if one of activities map is filled
			// with value; possible values are added, changed, deleted...
			return PollResult.createFull(null);
		}

		changingProps.put("TS", System.currentTimeMillis());
		changingProps.put("TS_DATA", "DATA::" + System.currentTimeMillis());

		if ((System.currentTimeMillis() - lastPoll) > 4000) {
			lastPoll = System.currentTimeMillis();
			//
			// to de-comment to see effect of error on notification/pollresult
			//
			// throw new RuntimeException("Dummy Exception!...");
		}

		//
		// here I want only to notify property changes
		//
		return PollResult.createIncremental(null, changingProps, null,
				System.currentTimeMillis());
	}

}// END