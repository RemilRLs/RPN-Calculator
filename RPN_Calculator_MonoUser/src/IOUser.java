import java.io.*;


/**
 * Class that cannot bit declared directly it like a schema that other class herit and have to be implemented.
 */
abstract class InputUser {
    public abstract String getUserInput() throws IOException;
}


abstract class OutputUser {
    public abstract void sendOutput(String message) throws IOException;
}
