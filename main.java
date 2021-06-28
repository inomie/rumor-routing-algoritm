import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/**
 * @author  CS: hed20rlg, id17afs, dv20ejn, dv20emn
 * Date: 2021-05-26
 */

/**
 * The main program that takes an file as input and create the environment.
 */
public class main {

    public static void main(String[] args) throws IOException {


        try {
            /* Reads the input file and makes the maze of it */
            Environment environment = new Environment(new FileReader(args[0]));

            while(environment.getTime() < 10000) {
                environment.timeStepUpdate();
            }
        } catch (FileNotFoundException e) {
            /* Error if no file is found as an input */
            throw new IOException("No file found");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            throw new IOException("No file argument");
        }


    }
}
