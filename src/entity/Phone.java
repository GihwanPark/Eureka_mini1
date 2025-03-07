package entity;

public class Phone {
    private int id;
    private String model;
    private String color;
    private String brand;
    private int price;
    private int amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Phone(int id, String model, String color, String brand, int price, int amount) {
        this.id = id;
        this.model = model;
        this.color = color;
        this.brand = brand;
        this.price = price;
        this.amount = amount; // 재고
    }
}
