package entity;

import java.sql.Date;

public class Orders {
    private int id;
    private int amount;
    private String grade;
    private Date date;
    private int salePrice;
    private String phoneName;    // 추가
    private String custName;     // 추가
    private String phoneNumber;  // 추가

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Orders(int id, String grade, String phoneName, String custName, String number, int salePrice, Date date) {
        this.id = id;
        this.grade = grade;
        this.phoneName = phoneName;
        this.custName = custName;
        this.phoneNumber = number;
        this.date = date;
        this.salePrice = salePrice;
    }
}
