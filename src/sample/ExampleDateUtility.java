package sample;

import com.klugesoftware.farmamanager.utility.DateUtility;

import java.util.Calendar;
import java.util.Locale;

public class ExampleDateUtility {

    public static void main (String[] args){
        Calendar myCal = Calendar.getInstance(Locale.ITALY);
        System.out.println(myCal.getFirstDayOfWeek());
        System.out.println(myCal.get(Calendar.DAY_OF_WEEK));
        System.out.println(myCal.get(Calendar.MONTH));
        System.out.println(DateUtility.converteDateToGUIStringDDMMYYYY(myCal.getTime()));
        myCal.set(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH),1);
        myCal.add(Calendar.MONTH,-4);
        System.out.println(DateUtility.converteDateToGUIStringDDMMYYYY(myCal.getTime()));
    }
}
