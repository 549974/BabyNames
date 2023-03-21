import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Manages the list of BabyNames as well as reading and writing to the files
 * with the BabyNames.
 * @author __________
 */
public class BabyNameDatabase {
    private String databaseFileName;
    private ArrayList<BabyName> records = new ArrayList<BabyName>();
    private File fileRecord;

    public BabyNameDatabase(String fName) {
        databaseFileName = fName;
        fileRecord = new File(databaseFileName);
    }
    public ArrayList<BabyName> getRecords() {
        return records;
    }
    /**
     * Reads the csv file that holds the baby name birth data and updates
     * the records variable.
     * @param filename name of the file to read from
     * @throws IOException could not find or close file
     */
    public void readRecordsFromBirthDataFile(String filename) throws IOException {
        // TODO 2: Write the code below this line.
        fileRecord = new File(filename);
        Scanner in = new Scanner(fileRecord);
        String l = null;
        String wrong = null;
        int yer = Integer.parseInt(filename.substring(filename.length() - 8, filename.length() - 4));
        while (in.hasNext()) {
            if (in.hasNextInt()) {
                l = in.nextLine();
                processLineFromBirthDataFile(l, yer);
            } else {
                wrong = in.nextLine();
            }
        }
    }
        /**
         * Processes one formatted line of the csv file into baby names and
         * adds/updates the records array.
         * @param line the string holding the line from the csv file
         * @param year when the data is from
         */
        public void processLineFromBirthDataFile(String line, int year){
            // TODO 3: Write the code below this line.
            // use delimiter ^[a-zA-Z]+$ for only a-z
            //1	Jacob	"25,838"	Emily	"23,948"
            // mut =Jacob"25,838"Emily"23,948"
            int count = 0;
            Scanner input = new Scanner(line);
            input.useDelimiter("\t");
            String li = input.nextInt() + "";
            String mut = line.substring(li.length());
            int name1;
            for(int i = 0; i < mut.length(); i++) {
                if(line.charAt(i) == '"' && count == 0) {
                    name1 = i - 1;
                    count++;
                } if(line.charAt(i) == '"' && count == 1) {

                }
            }
        }
    }