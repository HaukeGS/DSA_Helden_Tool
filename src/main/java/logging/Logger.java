package logging;

import java.util.Collection;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import logging.LogRecord.Level;

public class Logger {
	private static final int MAX_LOG_ENTRIES = 1_000_000;

	private final BlockingDeque<LogRecord> log = new LinkedBlockingDeque<>(MAX_LOG_ENTRIES);

	private void log(LogRecord record) {
		log.offer(record);
	}

	public void debug(String msg) {
		log(new LogRecord(Level.DEBUG, msg));
	}

	public void info(String msg) {
		log(new LogRecord(Level.INFO, msg));
	}

	public void warn(String msg) {
		log(new LogRecord(Level.WARN, msg));
	}

	public void error(String msg) {
		log(new LogRecord(Level.ERROR, msg));
	}

	public void drainTo(Collection<? super LogRecord> c) {
		log.drainTo(c);
	}

}