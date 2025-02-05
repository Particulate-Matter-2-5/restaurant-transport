package ku.cs.restaurant.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import ku.cs.restaurant.dto.Payment.PaymentResponse;
import ku.cs.restaurant.entity.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.api.key.test}")
    private String stripeSecretKey;

    @Override
    public PaymentResponse createPaymentLink(Order order) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        if (order == null || order.getTotal() <= 0)
            throw new IllegalArgumentException("Invalid order: Order must not be null and total must be greater than zero.");

        double total = (order.getTotal() + (order.getTotal() * 0.07)) * 100;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.PROMPTPAY)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5173/payment/success?id=" + order.getId())
                .setCancelUrl("http://localhost:5173/payment/fail")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("thb")
                                .setUnitAmount((long) total) // Ensure the total is in the
                                // smallest currency unit
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Food price") // Capitalized product name
                                        .build())
                                .build())
                        .build())
                .build();

        Session session = Session.create(params);

        PaymentResponse response = new PaymentResponse();
        response.setPaymentLink(session.getUrl());

        return response;
    }
}
