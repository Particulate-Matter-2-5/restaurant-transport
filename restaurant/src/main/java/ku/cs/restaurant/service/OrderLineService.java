package ku.cs.restaurant.service;

import ku.cs.restaurant.entity.Food;
import ku.cs.restaurant.entity.OrderLine;
import ku.cs.restaurant.entity.Order;
import ku.cs.restaurant.entity.OrderLineKey;
import ku.cs.restaurant.repository.OrderLineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLineService {
    private final OrderLineRepository orderLineRepository;

    public OrderLineService(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }

    public void createOrderLine(int qty, Order order, Food food) {
        OrderLine orderLine = new OrderLine();

        OrderLineKey orderLineKey = new OrderLineKey(food.getId(), order.getId());
        orderLine.setId(orderLineKey);

        orderLine.setQty(qty);
        orderLine.setOrder(order);
        orderLine.setFood(food);

        orderLineRepository.save(orderLine);
    }

    public List<OrderLine> findOrderLine() {
        return orderLineRepository.findAll();
    }

    public OrderLine updateQuantity(OrderLine orderLine) {
        orderLine.setQty(orderLine.getQty());
        return orderLineRepository.save(orderLine);
    }
}