package entity;

public class Customer {
    private int id;
    private String name;
    private String carrier; // 통신사
    private String number; // 폰번호
    private String grade; // 등급

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

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Customer(int id, String name, String carrier, String number, String grade) {
        this.id = id;
        this.name = name;
        this.carrier = carrier;
        this.number = number;
        this.grade = grade;
    }
}
