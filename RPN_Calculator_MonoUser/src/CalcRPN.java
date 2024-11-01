import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.File;

public class CalcRPN {
    private final UserLog userlog = new UserLog();
    private boolean exitServer = false;

    /**
     * Start RPN Calculator with argument that the user can gave.
     * @param args Arguments
     */
    public void start(String[] args) throws IOException {
        final int PORT = 12345;

        String userMode = "local"; // Default user mode.
        String logMode = "none"; // Default log mode.

        boolean recordLogs = false;
        boolean replayMode = false;


        InputUser inputUser;
        OutputUser outputUser;



        for (String arg : args) {
            if (arg.startsWith("-user=")) {
                userMode = arg.split("=")[1].toLowerCase(); // Get user mode : local or remote.
            } else if (arg.startsWith("-log")) {
                logMode = arg.split("=")[1].toLowerCase();
            } else {
                System.out.println("[X] - Invalid argument: " + arg);
                printHelp();
                return;
            }
        }

        if(userMode.equals("local")){
            inputUser = new LocalInputUser();
            outputUser = new ConsoleOutputUser();
        }  else if(userMode.equals("remote")){
            if (logMode.equals("rec")) {
                userlog.resetFileLogs();
                userlog.openLogFile();
                recordLogs = true;
            } else if (logMode.equals("replay")) {
                File logFile = new File("log.txt");
                if (!logFile.exists()) {
                    System.out.println("[X] - Log file does not exist. Replay mode cannot be activated.");
                } else {
                    replayMode = true;
                }
            }
            startServer(PORT, recordLogs, replayMode);
            return;
        } else {
            System.out.println("[X] - Invalid argument for User mode.\n");
            printHelp();
            return;
        }

        UserInteract userInteract = new UserInteract(inputUser, outputUser, userlog, null);
            if (logMode.equals("none")) {
                userInteract.interactiveMode(false, false);  // No logs.
            } else if (logMode.equals("rec")) {
                userlog.resetFileLogs();
                userlog.openLogFile();
                userInteract.interactiveMode(true, false);  // With logs.
            } else if (logMode.equals("replay")) {
                userInteract.interactiveMode(false, true);  // Mode replay.
            } else {
                System.out.println("[X] - Invalid argument for the Log mode.\n");
                printHelp();
                return;
            }
}

    /**
     * Start the server with the given port.
     * @param port Port to listen
     * @param recordLogs Flag to know if we need to record logs
     * @param replayMode Flag to know if we need to replay logs
     */
    public void startServer(int port, boolean recordLogs, boolean replayMode) throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server started. Waiting for connections on port " + port);

            while(!exitServer) {
                Socket clientSocket = serverSocket.accept();

                System.out.println("Client connected.");

                InputUser inputUser = new SocketInputUser(clientSocket);
                OutputUser outputUser = new SocketOutputUser(clientSocket);


                UserInteract userInteract = new UserInteract(inputUser, outputUser, userlog, clientSocket);
                userInteract.isRemoteActive = true;
                userInteract.interactiveMode(recordLogs, replayMode);


                clientSocket.close();

                if (userInteract.hasExited()) {
                    exitServer = true; // The user type exit when he was into remote mode (tricky).
                }
            }
        } catch (IOException e) {
            System.err.println("[X] - Server error: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }

    private static void printHelp() {
        System.out.println("\nRPN Calculator\n---------------------------------");
        System.out.println("\nUsage : number number operation");
        System.out.println("Example : 1 2 3 + *");
        System.out.println("User Mode: -user={[local], remote}");
        System.out.println("Log Mode : -log={[none], rec, replay}");
        System.out.println("\n---------------------------------");

    }
}
