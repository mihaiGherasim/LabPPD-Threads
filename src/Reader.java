import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.Scanner;

public class Reader {
    public static String[] readFromFile(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        String[] monomials = null;
        while(scanner.hasNextLine()){
            String data = scanner.nextLine();
            monomials = data.split(" ");
        }
        scanner.close();
        return monomials;
    }
}
