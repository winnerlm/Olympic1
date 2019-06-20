package player.tingxing;

import android.graphics.drawable.Drawable;

/**
 * Created by pseudonym on 2016/5/17.
 */
public class Person {
    private String name_;
    private Drawable image_;

    public Person(String name, Drawable image){
        name_ = name;

        image_ = image;
    }

    public Drawable getImage() {
        return image_;
    }

    public String getName() {
        return name_;
    }
}
