package client.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product", schema = "mydbtest")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Product(){}

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "count")
    private int count;

    @Column(name = "currentPrice")
    private double currentPrice;

    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL)
    private List<Order> orders;


    public Product(String name, String description, int count, double currentPrice) {
        this.name = name;
        this.description = description;
        this.count = count;
        this.currentPrice = currentPrice;
        orders = new ArrayList<Order>();
    }

    public void addOrders(Order order){
        order.setProductId(this);
        orders.add(order);
    }

    public void removeOrder(Order order){
        orders.remove(order);
    }

    public List<Order> getList() {
        return orders;
    }

    public void setList(List<Order> list) {
        this.orders = list;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
}
