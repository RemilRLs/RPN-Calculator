import java.io.*;
import java.net.Socket;

public class SocketInputUser extends InputUser {
    private final BufferedReader reader;

    /**
     * Constructor of the SocketInputUser
     * @param socket Client socket
     */
    public SocketInputUser(Socket socket) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public String getUserInput() throws IOException {
        return reader.readLine();
    }
}

class SocketOutputUser extends OutputUser {
    private final BufferedWriter writer;

    /**
     * Constructor of the SocketOutputUser
     * @param socket Client socket
     */
    public SocketOutputUser(Socket socket) throws IOException {
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    @Override
    public void sendOutput(String message) throws IOException {
        writer.write(message);
        writer.newLine();
        writer.flush();
    }
}
