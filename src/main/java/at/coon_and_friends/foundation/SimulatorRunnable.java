package at.coon_and_friends.foundation;

import java.io.File;

import org.pmw.tinylog.Logger;

public class SimulatorRunnable implements Runnable {

	private String level;

	private File directory;

	public SimulatorRunnable(String level, String directory) {
		this.level = level;
		this.directory = new File(directory);
		if (!this.directory.exists()) {
			throw new IllegalArgumentException(directory + " does not exist.");
		}
		if (!this.directory.isDirectory()) {
			throw new IllegalArgumentException(directory + " is no directory.");
		}
	}

	@Override
	public void run() {
		Logger.info("Starting simulator for level " + this.level);

		Process process = null;
		try {
			ProcessBuilder pb = new ProcessBuilder("java", "-jar",
					"simulator.jar", "--level=" + this.level, "--tcp="
							+ Constants.PORT);
			pb.directory(this.directory);
			pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
			pb.redirectError(ProcessBuilder.Redirect.INHERIT);
			Logger.info("Starting process");
			process = pb.start();

			process.waitFor();
		} catch (Exception e) {
			Logger.error(e);
		}

		if (process != null) {
			Logger.info("Destroying simulator");
			process.destroyForcibly();
		}
	}

}
