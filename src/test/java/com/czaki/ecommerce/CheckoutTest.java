package com.czaki.ecommerce;

import com.czaki.ecommerce.dto.PurchaseRequest;
import com.czaki.ecommerce.dto.PurchaseResponse;
import com.czaki.ecommerce.entity.Address;
import com.czaki.ecommerce.entity.Customer;
import com.czaki.ecommerce.service.CheckoutService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest()
public class CheckoutTest {

    @Autowired
    private CheckoutService checkoutService;

    @Test
    @Disabled
    public void testPlaceOrder() {

        // given
        PurchaseResponse response = new PurchaseResponse(UUID.randomUUID().toString());
        PurchaseRequest purchaseRequest = new PurchaseRequest();
        Address address = new Address();
        address.setCity("Wwa");
        address.setCountry("Poland");
        address.setState("Mazowsze");
        address.setZipCode("00-000");
        Customer customer = new Customer();
        customer.setEmail("yo@yo.pl");
        customer.setFirstName("Bruce");
        customer.setLastName("Wayne");

        purchaseRequest.setBillingAddress(address);
        purchaseRequest.setShippingAddress(address);
        purchaseRequest.setCustomer(customer);
        //purchaseRequest.setOrder();
        //purchaseRequest.setOrderItems();


        // when
        PurchaseResponse res = checkoutService.placeOrder(purchaseRequest);

        // then
        assertEquals(response, res);

    }

}
