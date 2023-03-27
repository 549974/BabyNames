import java.io.IOException;

/**
 * Tests the BabyName lab.
 * @author Mr. Turner
 */
public class BabyNameTester {
    public static void main(String[] args) {
        BabyNameDatabase p1 = new BabyNameDatabase("nofile.txt");
        try{
            p1.readRecordsFromBirthDataFile("BabyNames2005.csv");
            //p1.readRecordsFromBirthDataFile("BabyNames2006.csv");
        }
        catch(IOException e){
            System.err.print("readRecordsFromBirthDataFile threw an IOException");
        }
        int count = 0;
        for(BabyName name : p1.getRecords()){
            if (name.getGender().equals(GenderOfName.NEUTRAL)){
                System.out.println(name);
                count++;
            }
        }
        System.out.println("count: " + count);
    }
}