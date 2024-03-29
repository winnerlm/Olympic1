package player.tingxing;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by pseudonym on 2016/5/18.
 */
public class NextBigDayCalculator {

    protected DateTime memoDate_;
    protected DateTime nextBigDate_;
    protected String type_;
    protected final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    public NextBigDayCalculator(String type){
        type_ = type;
    }

    public final void setInitialDate(String date){
        memoDate_ = formatter.parseDateTime(date);
        initialize();
    }

    // return next big day date
    public String getDate(){
        return nextBigDate_.toString(formatter);
    }

    // return the description string
    public String getDescription(){
        return "";
    }

    // return the days to that day
    public long getLeftDays(){
        DateTime today = DateTime.now().withTimeAtStartOfDay();
        if(nextBigDate_.isEqual(today)) {
            return 0;
        }
        Duration duration = new Duration(DateTime.now().withTimeAtStartOfDay(), nextBigDate_);
        return duration.getStandardDays() + 1;
    }

    // return type
    public final String getType(){
        return type_;
    }

    protected void initialize(){
        // override in subclass
    }
}
