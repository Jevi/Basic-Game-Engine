package util;

public enum DebugLevel {
	FATAL("FATAL"), WARNING("WARNING"), LOW_DEBUG("LOW_DEBUG"), MID_DEBUG("MID_DEBUG"), HIGH_DEBUG("HIGH_DEBUG"), BEGIN("BEGIN"), END("END");

	private String level;

	private DebugLevel(String level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return level;
	}
}
