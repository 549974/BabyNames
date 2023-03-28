import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Manages the list of BabyNames as well as reading and writing to the files
 * with the BabyNames.
 * @author Jake Abendroth
 * @version 1.0
 * @since 2023-03-27
 */
public class BabyNameDatabase {
    private String databaseFileName;
    private ArrayList<BabyName> records = new ArrayList<>();
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
     *
     * @param filename name of the file to read from
     * @throws IOException could not find or close file
     */
    public void readRecordsFromBirthDataFile(String filename) throws IOException {
        Scanner in = new Scanner(new File(filename));
        int year = Integer.parseInt(filename.substring(9, 13));
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (Character.isDigit(line.charAt(0))) {
                processLineFromBirthDataFile(line, year);
            }
        }
        in.close();
    }

    /**
     * Processes one formatted line of the csv file into baby names and
     * adds/updates the records array.
     *
     * @param line the string holding the line from the csv file
     * @param year when the data is from
     */
    public void processLineFromBirthDataFile(String line, int year) {
        line = line.replaceAll("\"", "");
        String[] fields = line.split(",");
        ArrayList<String> par = new ArrayList<>(Arrays.asList(fields));
        for(int f = 0; f < par.size(); f++) {
            String x = par.get(f);
            if(Character.isDigit(x.charAt(0)) && f != par.size()-1 && Character.isDigit(par.get(f+1).charAt(0))) {
                par.set(f, par.get(f)+par.get(f+1));
                par.remove(f+1);
            }
        }
        String maleName = par.get(1);
        int maleCount = Integer.parseInt(par.get(2));
        String femaleName = par.get(3);
        int femaleCount = Integer.parseInt(par.get(4));
        BabyName mEntry;
        BabyName fEntry;
        boolean fFound = false;
        boolean mFound = false;
        for(BabyName test : records){
            if(test.getName().equals(maleName) && test.getGender().equals(GenderOfName.FEMALE.name())) {
                test.setGender(GenderOfName.NEUTRAL);
                test.addData(maleCount, year);
                mFound = true;
            } if(test.getName().equals(maleName) && test.getGender().equals(GenderOfName.MALE.name())) {
                test.addData(maleCount, year);
                mFound = true;
            } if(test.getName().equals(femaleName) && test.getGender().equals(GenderOfName.MALE.name())) {
                test.setGender(GenderOfName.NEUTRAL);
                test.addData(femaleCount, year);
                fFound = true;
            } if(test.getName().equals(femaleName) && test.getGender().equals(GenderOfName.FEMALE.name())) {
                test.addData(femaleCount, year);
                fFound = true;
            }
        }
        if(!fFound) {
            fEntry = new BabyName(femaleName, GenderOfName.FEMALE);
            records.add(fEntry);
            fEntry.addData(femaleCount, year);
        } if(!mFound) {
            mEntry = new BabyName(maleName, GenderOfName.MALE);
            records.add(mEntry);
            mEntry.addData(maleCount, year);
        }
    }
}
