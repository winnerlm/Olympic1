package player.tingxing;

import org.joda.time.DateTime;

/**
 * Created by pseudonym on 2016/5/18.
 */
public class AnniversaryCalculator extends NextBigDayCalculator {

    public AnniversaryCalculator(String type){
        super(type);
    }

    @Override
    public String getDescription(){
        int years = nextBigDate_.getYear() - memoDate_.getYear();

        return Integer.toString(years);
    }

    @Override
    protected void initialize(){
        DateTime today = new DateTime();

        DateTime anniversaryDate = memoDate_.year().setCopy(today.getYear());

        if(anniversaryDate.isEqualNow() || anniversaryDate.isAfterNow()){
            // the big day is at this year
            nextBigDate_ = anniversaryDate;
        }else {
            // the big day is at the next year
            nextBigDate_ = anniversaryDate.year().addToCopy(1);
        }
    }
}
