package com.demo.kafka.customSerializer;

public class Order {
    private String customerName;

    public Order(String customerName, String product, Integer quantity) {
        this.customerName = customerName;
        this.product = product;
        this.quantity = quantity;
    }

    private String product;
    private Integer quantity;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
