import java.util.ArrayList;
import java.util.Collections;

/**
 * Object that represents a name for a baby. Includes the sex of the name
 * and birth data for the number of babies born with that name in a
 * particular year.
 * @author __________
 */
public class BabyName {
    private GenderOfName gender;
    private String name;
    private ArrayList<Integer> birthCounts = new ArrayList<Integer>();
    private ArrayList<Integer> years = new ArrayList<Integer>();

    public BabyName(String name, GenderOfName gender) {
        this.gender = gender;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public String getGender() {
        return gender.name();
    }
    public ArrayList<Integer> getBirthCounts() {
        return birthCounts;
    }
    public ArrayList<Integer> getYears() {
        return years;
    }
    public void setGender(GenderOfName genderSet) {
        gender = genderSet;
    }
    public void addData(int count, int year) {
        int index = 0;
        if(years.size() > 0) {
            int max = Collections.max(years);
            int min = Collections.min(years);
            if(year > max) {
                years.add(year);
                index = years.indexOf(max) + 1;
            } else if(year < min) {
                years.add(0, year);
            }
            else if(year > min && year < max) {
                if(years.contains(year)) {
                    index = years.indexOf(year);
                } else {
                    for(int y : years) {
                        if(y < year) {
                            index = years.indexOf(y) + 1;
                        }
                    } years.add(index, year);
                }
            }
        } else {
            years.add(year);
        }
        birthCounts.add(index, count);
    }
    public void addData(ArrayList<Integer> namesnum, ArrayList<Integer> occur) {
        for(int i = 0; i < namesnum.size(); i++) {
            addData(namesnum.get(i), occur.get(i));
        }
    }
    public int index(int y) {
        return years.indexOf(y);
    }
    /**
     * Formats the object as a String.
     * @return formatted String
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Name: " + name + "\nSex of Name: " + gender.toString().toLowerCase());
        for (int i = 0; i < years.size(); i++){
            if (i == 0){
                result.append("\nData: ");
            }
            result.append(String.format("(%d, %d), ", birthCounts.get(i), years.get(i)));
            if (i == years.size()-1){
                result.deleteCharAt(result.length()-2); // Remove extra space
            }
        }
        return result.toString();
    }
}