package worker.contact;

/**
 * 作者：Created by 高宇 on 2016/9/13.
 * 邮箱：741208260@qq.com
 */
public class Person {

    private int id;
    private String name;
    private String number;
    //构造方法
    public Person(String number, String name) {
        this.number=number;
        this.name= name;
    }
    public  Person(){

    }

    public Person(int id, String name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                  ", name='" + name + '\'' +
                  ", number='" + number + '\'' +

                '}';
    }
}