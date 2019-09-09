package client.domain;

import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Userrrr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "customerId", cascade = CascadeType.ALL)
     private List<Order> orders;

    public Userrrr() {
    }

    public Userrrr(String name) {
        this.name = name;
        orders = new ArrayList<>();
    }

  public void addOrders(Order order){
        order.setCustomerId(this);
      orders.add(order);
  }

    public void removeOrder(Order order){
        orders.remove(order);
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
