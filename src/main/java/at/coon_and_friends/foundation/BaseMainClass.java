package at.coon_and_friends.foundation;

import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;

public abstract class BaseMainClass {

	static {
		Configurator
		.defaultConfig()
		.level(Level.DEBUG)
		.formatPattern(
				"{date:mm:ss} {class}.{method}() {level}: {message}")
				.activate();

		// .formatPattern("{date:HH:mm:ss} [{thread}] {class}.{method}() {level}: {message}")
	}

}
