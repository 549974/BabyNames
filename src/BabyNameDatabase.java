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
        File scan = new File(filename);
        Scanner in = new Scanner(scan);
        String l;
        String wrong;
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
            //1	Jacob	"25,838"	Emily	"23,948"
            //Jacob 25838    Emily	23948
            int count = 0;
            int last = 0;
            String name1 = null;
            String name2 = null;
            int pop1 = 0;
            int pop2 = 0;
            Scanner input = new Scanner(line);
            String li = input.nextInt() + "";
            line = line.replace("\"", "");
            line = line.replace(",", "");
            String mut = line.substring(li.length() + 1);
            for(int i = 0; i < mut.length(); i++) {
                if(line.charAt(i) == '\t' && count == 0) {
                    name1 = mut.substring(0, i);
                    last = i;
                    count++;
                }
                if(line.charAt(i) == '\t' && count == 1) {
                    pop1 = Integer.parseInt(mut.substring(last+1, i));
                    last = i;
                    count++;
                }
                if(line.charAt(i) == '\t' && count == 2) {
                    name2 = mut.substring(last+1, i);
                    last = i;
                    count++;
                }
                if(count == 3) {
                    pop2 = Integer.parseInt(mut.substring(last+1));
                }
            }
            BabyName mEntry = new BabyName(name1, GenderOfName.MALE);
            BabyName fEntry = new BabyName(name2, GenderOfName.FEMALE);
            for(BabyName test : records) {
                if(mEntry.getName() == test.getName()) {
                    if(mEntry.getGender() != test.getGender()) {
                        test.setGender(GenderOfName.NEUTRAL);
                    }
                }
                if(fEntry.getName() == test.getName()) {
                    if(fEntry.getGender() != test.getGender()) {
                        test.setGender(GenderOfName.NEUTRAL);
                    }
                }
            } mEntry.addData(pop1, year);
            fEntry.addData(pop2, year);
            input.close();
        }
    }