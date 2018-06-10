package logging;

import java.util.Date;

public class LogRecord {
	public enum Level {
		DEBUG, INFO, WARN, ERROR
	}

	private final Date timestamp;
	private final Level level;
	private final String message;

	public LogRecord(Level level, String message) {
		this.timestamp = new Date();
		this.level = level;
		this.message = message;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public Level getLevel() {
		return level;
	}

	public String getMessage() {
		return message;
	}
}
