package it.fago.archaius.hierarchy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.netflix.config.PollResult;
import com.netflix.config.PolledConfigurationSource;

class DummyPollingSource implements PolledConfigurationSource {

	volatile boolean incremental;
	volatile Map<String, Object> full, added, deleted, changed;

	public DummyPollingSource(boolean incremental) {
		this.incremental = incremental;
	}

	public synchronized void setIncremental(boolean value) {
		this.incremental = value;
	}

	public synchronized void setContent(String content, Map<String, Object> map) {
		String[] pairs = content.split(",");
		if (pairs != null) {
			for (String pair : pairs) {
				String[] nameValue = pair.trim().split("=");
				if (nameValue.length == 2) {
					map.put(nameValue[0], nameValue[1]);
				}
			}
		}
	}

	public synchronized void setFull(String content) {
		full = new ConcurrentHashMap<String, Object>();
		setContent(content, full);
	}

	public synchronized void setAdded(String content) {
		added = new ConcurrentHashMap<String, Object>();
		setContent(content, added);
	}

	public synchronized void setDeleted(String content) {
		deleted = new ConcurrentHashMap<String, Object>();
		setContent(content, deleted);
	}

	public synchronized void setChanged(String content) {
		changed = new ConcurrentHashMap<String, Object>();
		setContent(content, changed);
	}


	public synchronized PollResult poll(boolean initial, Object checkPoint)
			throws Exception {
		if (incremental) {
			return PollResult.createIncremental(added, changed, deleted, null);
		} else {
			return PollResult.createFull(full);
		}
	}

}// END
