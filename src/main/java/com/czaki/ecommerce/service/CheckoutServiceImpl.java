package com.czaki.ecommerce.service;

import com.czaki.ecommerce.dao.CustomerRepository;
import com.czaki.ecommerce.dto.PaymentInfo;
import com.czaki.ecommerce.dto.PurchaseRequest;
import com.czaki.ecommerce.dto.PurchaseResponse;
import com.czaki.ecommerce.entity.Customer;
import com.czaki.ecommerce.entity.Order;
import com.czaki.ecommerce.entity.OrderItem;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


@Service
public class CheckoutServiceImpl implements CheckoutService {

    private CustomerRepository customerRepository;

    @Autowired
    public  CheckoutServiceImpl(CustomerRepository customerRepository, @Value("${stripe.key.secret}") String secretKey) {
        this.customerRepository = customerRepository;
        Stripe.apiKey = secretKey;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(PurchaseRequest purchaseRequest) {
        Order order = purchaseRequest.getOrder();

        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        Set<OrderItem> orderItems = purchaseRequest.getOrderItems();
        orderItems.forEach(order::add);

        order.setShippingAddress(purchaseRequest.getShippingAddress());
        order.setBillingAddress(purchaseRequest.getBillingAddress());

        Customer customer = purchaseRequest.getCustomer();

        Customer customerFromDB = customerRepository.findByEmail(customer.getEmail());

        if (customerFromDB != null) {
            customer = customerFromDB;
        }

        customer.add(order);

        customerRepository.save(customer);

        return new PurchaseResponse(orderTrackingNumber);
    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);
        params.put("description", "E commerce purchase");
        params.put("receipt_email", paymentInfo.getReceiptEmail());

        return PaymentIntent.create(params);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}
