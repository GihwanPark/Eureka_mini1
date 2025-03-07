package entity;

public class Basket {
    private int id;
    private int amount;
    private int price;
    private int phoneId;
    private int custId;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(int phoneId) {
        this.phoneId = phoneId;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public Basket(int id, int amount, int price, int phoneId, int custId) {
        this.id = id;
        this.amount = amount;
        this.price = price;
        this.phoneId = phoneId;
        this.custId = custId;
    }
}
