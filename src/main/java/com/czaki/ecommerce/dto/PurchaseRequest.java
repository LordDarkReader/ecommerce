package com.czaki.ecommerce.dto;

import com.czaki.ecommerce.entity.Address;
import com.czaki.ecommerce.entity.Customer;
import com.czaki.ecommerce.entity.Order;
import com.czaki.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class PurchaseRequest {

    private Customer customer;

    private Address shippingAddress;

    private Address billingAddress;

    private Order order;

    private Set<OrderItem> orderItems;

}
