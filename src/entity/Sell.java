package entity;

public class Sell {
    private int id;
    private String phoneName;
    private int totalAmount;
    private int totalSalePrice;
    private String color;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalSalePrice() {
        return totalSalePrice;
    }

    public void setTotalSalePrice(int totalSalePrice) {
        this.totalSalePrice = totalSalePrice;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Sell(String phoneName, int totalAmount, int totalSalePrice, String color) {
        this.phoneName = phoneName;
        this.totalAmount = totalAmount;
        this.totalSalePrice = totalSalePrice;
        this.color = color;
    }
}
