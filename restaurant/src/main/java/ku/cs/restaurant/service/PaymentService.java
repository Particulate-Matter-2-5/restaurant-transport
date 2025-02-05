package ku.cs.restaurant.service;

import com.stripe.exception.StripeException;
import ku.cs.restaurant.dto.Payment.PaymentResponse;
import ku.cs.restaurant.entity.Order;

public interface PaymentService {
    PaymentResponse createPaymentLink(Order order) throws StripeException;
}
