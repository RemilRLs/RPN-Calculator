import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.File;

public class UserLog {
    private static final String FILE_NAME = "log.txt";
    private FileWriter writerLog;

    /**
     * Open the log file
     */
    public void openLogFile(){
        try{
            writerLog = new FileWriter(FILE_NAME, true);
        }catch (IOException e){
            System.out.println("[X] - Cannot open the file, please retry...");
            e.printStackTrace();
        }
    }

    /**
     * Write the operation in the log file
     * @param operation Operation to write
     */

    public void writeLogFile(String operation) throws IOException {
        if (writerLog != null) {
            writerLog.write(operation);
            writerLog.write(System.lineSeparator());
            writerLog.flush();
        } else {
            System.err.println("[X] - Log file is not open or no created.");
        }
    }

    /**
     * Get the logs from the file
     * @return List of logs
     */

    public List<String> getLogs() throws IOException {

        return Files.readAllLines(Paths.get(FILE_NAME));
    }

    /**
     * Reset the file logs
     */

    public void resetFileLogs() throws IOException {
        File fileLogs = new File(FILE_NAME);
        fileLogs.delete();

    }

    /**
     * Close the log file
     */
    public void closeFile() throws IOException {
        if (writerLog != null) {
            writerLog.close();
        }
    }


}
