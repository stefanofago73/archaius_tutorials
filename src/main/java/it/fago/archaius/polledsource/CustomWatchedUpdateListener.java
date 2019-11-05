package it.fago.archaius.polledsource;

import static it.fago.archaius.util.Utils.logger;

import org.slf4j.Logger;

import com.netflix.config.WatchedUpdateListener;
import com.netflix.config.WatchedUpdateResult;

public class CustomWatchedUpdateListener implements WatchedUpdateListener {
	//
	private Logger logger = logger(CustomWatchedUpdateListener.class);

	public void updateConfiguration(WatchedUpdateResult evt) {
		logger.info("\n[{}]\nincremental: {}\ndata: {}\n", evt.toString(),
				evt.isIncremental(), evt.getComplete());
	}

}// END
