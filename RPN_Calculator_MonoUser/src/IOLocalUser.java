import java.util.Scanner;

class LocalInputUser extends InputUser {
    private Scanner scanner;

    public LocalInputUser() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String getUserInput() {
        return scanner.nextLine();
    }
}


class ConsoleOutputUser extends OutputUser {
    @Override
    public void sendOutput(String message) {
        System.out.println(message);
    }
}