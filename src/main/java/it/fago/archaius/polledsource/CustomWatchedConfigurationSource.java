package it.fago.archaius.polledsource;

import static it.fago.archaius.util.Utils.logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import com.netflix.config.WatchedConfigurationSource;
import com.netflix.config.WatchedUpdateListener;
import com.netflix.config.WatchedUpdateResult;

public class CustomWatchedConfigurationSource implements
		WatchedConfigurationSource {
	//
	private Logger logger = logger(CustomWatchedConfigurationSource.class);
	//
	// here we report all final states for this source
	//
	private Map<String, Object> internalData = new HashMap<String, Object>();

	//
	// Listeners Management
	//
	private CopyOnWriteArraySet<WatchedUpdateListener> listeners = new CopyOnWriteArraySet<WatchedUpdateListener>();
	//
	// internal timer simulation for updated data
	//
	private ScheduledExecutorService worker;

	public void init() {
		logger.info("starting...");
		worker = Executors.newSingleThreadScheduledExecutor();
		worker.scheduleAtFixedRate(new UpdateTask(this), 1L, 1L,
				TimeUnit.SECONDS);
	}

	public void destroy() {
		worker.shutdown();
		try {
			worker.awaitTermination(2000L, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			logger.warn("some problem waiting for shutdown... {} ",
					e.toString());
		}
		worker.shutdownNow();
		worker = null;
		logger.info("destroyed...");
	}

	public void addUpdateListener(WatchedUpdateListener listener) {
		if (listener == null)
			return;
		listeners.add(listener);
	}

	public void removeUpdateListener(WatchedUpdateListener listener) {
		if (listener == null)
			return;
		listeners.remove(listener);
	}

	public Map<String, Object> getCurrentData() throws Exception {
		return internalData;
	}

	protected void update() {
		internalData.put("TS", System.currentTimeMillis());
		WatchedUpdateResult evt = WatchedUpdateResult
				.createFull(new HashMap<String, Object>(internalData));
		for (Iterator<WatchedUpdateListener> iterator = listeners.iterator(); iterator
				.hasNext();) {
			iterator.next().updateConfiguration(evt);
		}
	}

	// ===========================================================
	//
	//
	//
	// ===========================================================

	private static class UpdateTask implements Runnable {

		//
		private Logger logger = logger(UpdateTask.class);

		private final CustomWatchedConfigurationSource source;

		public UpdateTask(final CustomWatchedConfigurationSource source) {
			this.source = source;
		}

		public void run() {
			try {
				logger.info("\nupdating source");
				  source.update();
			} catch (Exception exc) {
				logger.warn("Something wrong updating source! {} ",
						exc.toString());
			}
			
		}

	}// END

	// ===========================================================
	//
	//
	//
	// ===========================================================

}// END