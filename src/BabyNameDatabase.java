import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
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
        Scanner in = new Scanner(new File(filename));
        int year = Integer.parseInt(filename.substring(filename.length() - 8, filename.length() - 4));
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (line.matches("^\\d")) {
                processLineFromBirthDataFile(line, year);
            }
        }
        in.close();
    }
        /**
         * Processes one formatted line of the csv file into baby names and
         * adds/updates the records array.
         * @param line the string holding the line from the csv file
         * @param year when the data is from
         */
        public void processLineFromBirthDataFile(String line, int year) {
            String[] fields = line.split("\\t");
            String maleName = fields[1].trim();
            String femaleName = fields[3].trim();
            int maleCount = Integer.parseInt(fields[2].replaceAll(",", "").trim());
            int femaleCount = Integer.parseInt(fields[4].replaceAll(",", "").trim());

            BabyName mEntry = new BabyName(maleName, GenderOfName.MALE);
            BabyName fEntry = new BabyName(femaleName, GenderOfName.FEMALE);

            for (BabyName test : records) {
                if (mEntry.getName().equals(test.getName())) {
                    if (!Objects.equals(mEntry.getGender(), test.getGender())) {
                        test.setGender(GenderOfName.NEUTRAL);
                    }
                }
                if (fEntry.getName().equals(test.getName())) {
                    if (!Objects.equals(fEntry.getGender(), test.getGender())) {
                        test.setGender(GenderOfName.NEUTRAL);
                    }
                }
            }

            mEntry.addData(maleCount, year);
            fEntry.addData(femaleCount, year);

            if (!records.contains(mEntry)) {
                records.add(mEntry);
            }
            if (!records.contains(fEntry)) {
                records.add(fEntry);
            }
        }

}