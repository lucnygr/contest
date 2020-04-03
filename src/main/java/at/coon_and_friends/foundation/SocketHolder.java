package at.coon_and_friends.foundation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.pmw.tinylog.Logger;

public class SocketHolder {

	private Socket socket;

	private BufferedReader reader;

	private PrintWriter writer;

	public SocketHolder(String host, int port) throws IOException,
	InterruptedException {
		try {
			this.socket = new Socket(host, port);
		} catch (ConnectException ce) {
			Logger.warn("ConnecitonException: " + ce.getMessage()
					+ ", Retry after wait.");
			Thread.sleep(500);
			this.socket = new Socket(host, port);
		}
		InputStream is = socket.getInputStream();
		OutputStream os = socket.getOutputStream();

		this.reader = new BufferedReader(new InputStreamReader(is));
		this.writer = new PrintWriter(os);
	}

	public void close() {
		try {
			this.reader.close();
		} catch (IOException e) {
			Logger.warn(e);
		}
		this.writer.close();
		try {
			this.socket.close();
		} catch (IOException e) {
			Logger.warn(e);
		}
	}

	public void printLine(String s) {
		Logger.debug("Write: " + s);
		this.writer.println(s);
		this.writer.flush();
	}

	public String readLine() throws IOException {
		String line = this.reader.readLine();
		Logger.debug("Read: " + line);
		return line;
	}

	public List<String> readLineAsList() throws IOException {
		String line = this.reader.readLine();
		Logger.debug("Read: " + line);
		Scanner scanner = new Scanner(line);
		scanner.useDelimiter(" ");
		List<String> result = new ArrayList<>();
		while (scanner.hasNext()) {
			result.add(scanner.next());
		}
		scanner.close();
		return result;
	}

	public BufferedReader getReader() {
		return reader;
	}

	public PrintWriter getWriter() {
		return writer;
	}

}
