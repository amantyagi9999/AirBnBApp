package com.projects.service;

import com.projects.entity.Booking;

public interface CheckoutService {

    String getCheckoutSession(Booking booking, String successUrl, String failureUrl);

}
