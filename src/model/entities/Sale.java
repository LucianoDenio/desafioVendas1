package model.entities;

public class Sale {

    private Integer month;
    private Integer year;
    private String seller;
    private Integer items;
    private Double total;

    public Sale(Integer month, Integer year, String seller, Integer items, Double total) {
        this.month = month;
        this.year = year;
        this.seller = seller;
        this.items = items;
        this.total = total;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getYear() {
        return year;
    }

    public String getSeller() {
        return seller;
    }

    public Integer getItems() {
        return items;
    }

    public Double getTotal() {
        return total;
    }

    public Double averagePrice() {
        if (items != null && items > 0) {
            return total / items;
        } else {
            return 0.0;
        }
    }

    @Override
    public String toString() {
        return month + "/" + year + ", " + seller + ", " + items + ", " + String.format("%.2f", total)
                + ", pm = " + String.format("%.2f", averagePrice());
    }
}
