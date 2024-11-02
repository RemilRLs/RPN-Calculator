import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Server that manages the RPL calculator with shared or multiple mode for the stack.
 */
public class ServerRPL {
    private static final PileRPL sharedPileRPL = new PileRPL();
    private static boolean isSharedMode = true;
    private static final Map<Socket, PileRPL> userListPileRPL = Collections.synchronizedMap(new HashMap<>());

    private static final Lock pileLock = new ReentrantLock(); // To avoid concurrent access to the shared pile.

    private static final int DEFAULT_PORT = 12345;

    /**
     * Method to start the server with the given arguments.
     * @param args Arguments
     */

    public void start(String[] args) throws IOException {
        parseArguments(args);
        startServer(DEFAULT_PORT);
    }

    /**
     * Method to parse the arguments.
     * @param args Arguments
     */
    private void parseArguments(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("-stack=")) {
                String mode = arg.split("=")[1].toLowerCase();
                if (mode.equals("shared")) {
                    isSharedMode = true;
                } else if (mode.equals("multiple")) {
                    isSharedMode = false;
                } else {
                    System.err.println("[X] - Invalid argument: " + arg);
                    printHelp();
                    System.exit(1);
                }
            } else {
                System.err.println("[X] - Invalid argument: " + arg);
                printHelp();
                System.exit(1);
            }
        }
    }

    /**
     * Method to start the server and listen to the port.
     * @param port Port to listen
     */

    private void startServer(int port) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("[*] - Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("[*] - New client connected: " + clientSocket.getRemoteSocketAddress());

                PileRPL pileRPL = isSharedMode ? sharedPileRPL : new PileRPL();
                if (!isSharedMode) {
                    userListPileRPL.put(clientSocket, pileRPL);
                }

                InputUser inputUser = new SocketInputUser(clientSocket);
                OutputUser outputUser = new SocketOutputUser(clientSocket);

                new Thread(new ClientRPL(clientSocket, inputUser, outputUser, pileRPL, isSharedMode, pileLock)).start();
            }
        } catch (IOException e) {
            System.err.println("[X] - Server error: " + e.getMessage());
        }
    }

    /**
     * Display the help message.
     */
    private void printHelp() {
        System.out.println("\nRPN Calculator\n---------------------------------");
        System.out.println("\nUsage: number number operation");
        System.out.println("Example: 1 2 3 + *");
        System.out.println("Usage: -stack={[shared], multiple}");
        System.out.println("Example: $java ServerCalcRPL -stack=shared");
        System.out.println("\n---------------------------------");
    }
}
