package com.czaki.ecommerce.service;

import com.czaki.ecommerce.dto.PaymentInfo;
import com.czaki.ecommerce.dto.PurchaseRequest;
import com.czaki.ecommerce.dto.PurchaseResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface CheckoutService {

    PurchaseResponse placeOrder(PurchaseRequest purchaseRequest);

    PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;
}
