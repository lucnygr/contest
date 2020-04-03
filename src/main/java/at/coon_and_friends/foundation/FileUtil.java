package at.coon_and_friends.foundation;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public final class FileUtil {

	private FileUtil() {
	}

	public static String readFile(String filepath) throws IOException {
		Path pathToFile = Paths.get(filepath);
		return readFile(pathToFile);
	}

	public static String readFile(Path pathToFile) throws IOException {
		byte[] bytes = null;
		bytes = Files.readAllBytes(pathToFile);
		return new String(bytes);
	}

	public static List<Path> listFiles(String dirpath) throws IOException {
		List<Path> contents = new ArrayList<>();
		Path pathToDir = Paths.get(dirpath);
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(pathToDir)) {
			for (Path p : ds) {
				contents.add(p);
			}
		}
		return contents;
	}

	public static List<String> readFiles(String dirpath) throws IOException {
		List<String> contents = new ArrayList<>();
		for (Path p : listFiles(dirpath)) {
			contents.add(readFile(p));
		}
		return contents;
	}

	public static void writeFile(String filepath, String content) throws IOException {
		Path pathToFile = Paths.get(filepath);
		Files.write(pathToFile, content.getBytes("UTF-8"), StandardOpenOption.CREATE, StandardOpenOption.WRITE,
				StandardOpenOption.TRUNCATE_EXISTING);
	}

	public static String calcOutfilePath(Path inFilePath) {
		String infile = inFilePath.toAbsolutePath().toFile().getAbsolutePath();
		String outfile = infile.replace("\\in\\", "\\out\\");
		return outfile;
	}
}
