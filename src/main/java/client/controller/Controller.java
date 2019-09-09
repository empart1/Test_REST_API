package client.controller;


import client.domain.Order;
import client.domain.Product;
import client.domain.Userrrr;
import client.repository.OrderRepo;
import client.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@org.springframework.stereotype.Controller
public class Controller {

    private ProductRepo productRepo;
    private OrderRepo orderRepo;

    @Autowired
    public Controller(ProductRepo productRepo, OrderRepo orderRepo){
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    @GetMapping("/hello")
    public String show(Order order, Model model) {

        model.addAttribute("orders", orderRepo.findAll());
        return "index";
    }

    @GetMapping("/signup")
    public String showSignUpForm(Order order) {
        return "add-order";
    }

    @PostMapping("/addorder")
    public String addOrder(@Valid Order order, BindingResult result, Model model) {
        Product product = productRepo.findByName(order.getProductName());
        if (result.hasErrors() || product==null || product.getCount()<1 || order.getCount()>product.getCount()) {
            return "add-order";
        }
         product.setCount(product.getCount() - order.getCount());


        order.setPrice(product.getCurrentPrice());
        order.setSum(order.getCount() * order.getPrice());

        if(product.getCount()==0 || product.getCount()<0){
            productRepo.delete(product);
        }else{
            productRepo.save(product);
        }
        orderRepo.save(order);
        model.addAttribute("orders", orderRepo.findAll());
        return "index";
    }

    @GetMapping("/edit/{orderId}")
    public String showUpdateForm(@PathVariable("orderId") int orderId, Model model) {
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + orderId));
        model.addAttribute("orders", order);
        return "update-order";
    }

    @PostMapping("/update/{orderId}")
    public String updateOrder(@PathVariable("orderId") int orderId, @Valid Order order, BindingResult result, Model model) {
        if (result.hasErrors()) {
            order.setOrderId(orderId);
            return "update-order";
        }
        Order orderChanged = orderRepo.findByOrderId(orderId);
        Product product = productRepo.findByName(order.getProductName());
        if(orderChanged.getCount()<order.getCount()){
            if(order.getCount()> product.getCount()){
                return "update-order";
            }
            product.setCount(product.getCount() - order.getCount());
            order.setPrice(product.getCurrentPrice());
            order.setSum(order.getCount() * order.getPrice());
        }

        productRepo.save(product);
        orderRepo.save(order);
        model.addAttribute("orders", orderRepo.findAll());
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable("id") int id, Model model) {
        Order order = orderRepo.findByOrderId(id);
        Product product = productRepo.findByName(order.getProductName());
        product.setCount(product.getCount() + order.getCount());
        productRepo.save(product);
        orderRepo.delete(order);
        model.addAttribute("orders", orderRepo.findAll());
        return "index";
    }

    @GetMapping("/newoffer")
    public String getNewOffer(@RequestBody Userrrr user, Model model) throws ParseException {
        List<Product>  list = productRepo.findAll();
        List<Product> result = new ArrayList<>();
        List<Product> productsInThisMonth = new ArrayList<>();
        List<Order> orders = user.getOrders();
        DateFormat format = new SimpleDateFormat("dd.mm.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MONTH, -1);
        Date date = calendar.getTime();
        for(Order bean : orders){
            Date dateOrder = format.parse(bean.getOrderDate());
              if(dateOrder.after(date) || dateOrder.equals(date)){
                  productsInThisMonth.add(bean.getProductId());
              }
        }
        List<Product> intersection = new ArrayList<>(list);
        intersection.retainAll(productsInThisMonth);
        List<Product> res = new ArrayList<>(list);
        res.addAll(productsInThisMonth);
        res.removeAll(intersection);
        Collections.sort(res, (o1, o2) -> {
            if(o1.getCount()>o2.getCount()){
                return -1;
            }else{
                return 1;
            }
        });
        result.addAll(res.stream().limit(2).collect(Collectors.toList()));


        for (Iterator<Product> iterator = result.iterator(); iterator.hasNext(); ) {
            Product p = iterator.next();
            p.setCurrentPrice(p.getCurrentPrice() * 0.7);
        }

        model.addAttribute("products", result);
        return "new-offer";
    }

}
