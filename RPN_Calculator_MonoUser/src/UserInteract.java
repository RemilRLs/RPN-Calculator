import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.List;

public class UserInteract {
    private final PileRPL pile;
    private InputUser inputUser;
    private OutputUser outputUser;
    private final UserLog userlog;
    private Socket clientSocket;
    private boolean exited = false;

    public boolean isRemoteActive = false;
    boolean exitInteractive = false;

    /**
     * Constructor of the UserInteract
     * @param inputUser User input
     * @param outputUser User output
     * @param userlog User log
     * @param clientSocket Client socket
     */
    public UserInteract(InputUser inputUser, OutputUser outputUser, UserLog userlog, Socket clientSocket) {
        this.inputUser = inputUser;
        this.outputUser = outputUser;
        this.userlog = userlog;
        this.clientSocket = clientSocket;

        this.pile = new PileRPL();

    }

    /**
     * Method to start the interactive mode.
     * @param recordLogs Flag to know if we need to record logs
     * @param replayMode Flag to know if we need to replay logs
     */

    public void interactiveMode(boolean recordLogs, boolean replayMode) throws IOException {

        if (replayMode) {
            List<String> logsInput = userlog.getLogs();

            if (logsInput.isEmpty()) {
                outputUser.sendOutput("[X] - No logs available for replay.");
            } else {
                outputUser.sendOutput("[Replay] Beginning of the replay mode\n");

                for (String log : logsInput) {
                    outputUser.sendOutput("\n[Replay] Command: " + log + "\n");
                    processInput(log);

                    if (log.equals("exit")) {
                        outputUser.sendOutput("[*] - Finished Replay Mode back to interactive mode.\n");
                    }
                }
            }


        }


        while (!exitInteractive) {
            outputUser.sendOutput("\n[?] - Enter a number or an operation : ");
            String input = inputUser.getUserInput();

            if (recordLogs && (!input.equals("remote") && !input.equals("local"))) {
                userlog.writeLogFile(input);
            }

            if (input.equalsIgnoreCase("remote") && !isRemoteActive) {
                changingToRemoteMode(12345);
                continue;
            } else if (input.equalsIgnoreCase("remote") && isRemoteActive) {
                outputUser.sendOutput("[X] - Already in remote connexion");
                continue;
            } else if (input.equalsIgnoreCase("local") && isRemoteActive) {
                changingToLocalMode();
                continue;
            } else if(input.equalsIgnoreCase("local") && !isRemoteActive){
                outputUser.sendOutput("[X] - Already in local mode");
                continue;
            }

            if (exitInteractive) {
                return;
            }

            if (input.equals("exit")) {
                outputUser.sendOutput("[*] - Info : Exiting the program.\n");

                if(isRemoteActive){
                    outputUser.sendOutput("[*] - Info : Closing socket.\n");
                    clientSocket.close();
                }
                exitInteractive = true;
                exited = true;
                return;

            } else {
                String[] commands = input.trim().split("\\s+");
                for (String command : commands) {
                    processInput(command);
                }
            }
        }

    }

    /**
     * Method to know if the user has exited the program.
     * @return true if the user has exited | false if not.
     */
    public boolean hasExited() {
        return exited;
    }

    /**
     * Method to process operation like add, minus, multiply and more...
     *
     * @param arg Argument (can be +, -, *, ...)
     */
    private void processInput(String arg) throws IOException {

        if (arg.equals("exit")) {
            outputUser.sendOutput("[*] - Info : Exiting interactive mode.\n");
            return;
        } else if (arg.matches("\\(\\d+,\\d+\\)")) {
            String[] parts = arg.replaceAll("[()]", "").split(","); // I only need the two number.

            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);

            Vecteur2D vecteur = new Vecteur2D(x, y);
            pile.push(vecteur);
            outputUser.sendOutput(pile.toString().trim());
        } else if (isInt(arg)) {
            ObjEmp obj = new ObjEmp(Integer.parseInt(arg));
            pile.push(obj);
            outputUser.sendOutput(pile.toString().trim());
        } else {
            executeOperation(arg);
        }
    }

    /**
     * Method that is used to execute the operation.
     * @param operation Operation to execute
     */
    private void executeOperation(String operation) throws IOException {

        switch (operation) {
            case "+":
                pile.addition();
                break;
            case "-":
                pile.soustraction();
                break;
            case "*":
            case "x":
                pile.multiplication();
                break;
            case "/":
                boolean divisionSuccess = pile.division();
                if (!divisionSuccess) {
                    outputUser.sendOutput("[X] - Error: Cannot divide by 0");
                }
                break;
            case "pop":
                if (pile.pop() == null) {
                    outputUser.sendOutput("[X] - Error: Cannot pop when there is no element");
                }
                break;
            case "head":
                Object head = pile.head();
                if (head == null) {
                    outputUser.sendOutput("[X] - Error: Cannot head when there is no element");
                } else {
                    outputUser.sendOutput("\n[*] - Head: " + head);
                }
                break;
            case "pile":
                break;
            default:
                outputUser.sendOutput("\n[X] - Error: Operation not recognized: " + operation);
                return;
        }
        outputUser.sendOutput(pile.toString().trim()); // I print the pile after each operation.
    }

    /**
     * Method to know if a string can be converted to an int.
     *
     * @param str Get String that we want to check.
     * @return true if the string can be converted to a string | false if not.
     */
    public boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Method to change to remote mode.
     * @param port Port to connect
     */

    public void changingToRemoteMode(int port) throws IOException {
        isRemoteActive = true;

        outputUser.sendOutput("[*] - Switching to remote mode, waiting for a Telnet connection on localhost port 12345...");

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            clientSocket = serverSocket.accept();
            outputUser.sendOutput("[*] - Client connected.");

            inputUser = new SocketInputUser(clientSocket);
            outputUser = new SocketOutputUser(clientSocket);

        } catch (IOException e) {
            isRemoteActive = false;
            outputUser.sendOutput("[X] - Error happened: " + e.getMessage());
        }
    }

    // https://stackoverflow.com/questions/10240694/java-socket-api-how-to-tell-if-a-connection-has-been-closed#:~:text=isClosed()%20tells%20you%20whether,you%20have%2C%20it%20returns%20false.

    /**
     * Method to change to local mode.
     */
    public void changingToLocalMode() throws IOException {
        isRemoteActive = false;
        outputUser.sendOutput("[*] - Returning to local mode.\n");

        // Need to close the socket after used it.

        if(clientSocket != null && !clientSocket.isClosed()) {
            clientSocket.close();
        }
        inputUser = new LocalInputUser();
        outputUser = new ConsoleOutputUser();

        outputUser.sendOutput("[*] - Connection closed for remote.\n");
    }
}
