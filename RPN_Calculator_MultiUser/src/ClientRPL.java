import java.io.IOException;
import java.net.Socket;

public class ClientRPL implements Runnable {
    private final PileRPL pile;
    private final Socket clientSocket;
    private final InputUser inputUser;
    private final OutputUser outputUser;

    /**
     * Constructor of the ClientRPL (each player will have his own ClientRPL)
     * @param clientSocket Socket of the client
     * @param inputUser User input
     * @param outputUser User output
     * @param pile Pile linked to the client
     */
    public ClientRPL(Socket clientSocket, InputUser inputUser, OutputUser outputUser, PileRPL pile) {
        this.clientSocket = clientSocket;
        this.inputUser = inputUser;
        this.outputUser = outputUser;
        this.pile = pile;


    }

    @Override
    public void run() {
        try {
            while (!clientSocket.isClosed()){
                outputUser.sendOutput("\n[?] - Enter a number or an operation : ");
                String input = inputUser.getUserInput();

                if (input.equalsIgnoreCase("exit")) {
                    outputUser.sendOutput("[*] - Info: Exiting interactive mode.\n");
                    clientSocket.close();
                    break;
                }
                else {
                    String[] commands = input.trim().split("\\s+");
                    for (String command : commands) {
                        processInput(command);
                    }
                }
            }

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

    /**
     * Method to process operation like add, minus, multiply and more...
     *
     * @param arg Argument (can be +, -, *, ...)
     */
    private void processInput(String arg) throws IOException {

        if (arg.equalsIgnoreCase("exit")) {
            outputUser.sendOutput("[*] - Info: Exiting interactive mode.\n");
            clientSocket.close();
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

}
