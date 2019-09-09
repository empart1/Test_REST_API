package client.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "order", schema = "mydbtest")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @Column(name = "orderNumber")
    private int orderNumber;

    @Column(name = "orderDate")
    private String orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId")
    private Userrrr customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product productId;

    @Column(name = "productName")
    private String productName;

    @Column(name = "price")
    private double price;

    @Column(name = "count")
    private int count;

    @Column(name = "sum")
    private double sum;

    public Order(){}

    public Order(int orderNumber, String orderDate, Userrrr customerId, Product productId, String productName, double price, int count, double sum) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.count = count;
        this.sum = sum;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Userrrr getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Userrrr customerId) {
        this.customerId = customerId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }


}
