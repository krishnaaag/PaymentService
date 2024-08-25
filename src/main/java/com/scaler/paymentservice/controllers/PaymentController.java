package com.scaler.paymentservice.controllers;

import com.razorpay.RazorpayException;
import com.scaler.paymentservice.dtos.GeneratePaymentLinkDto;
import com.scaler.paymentservice.services.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class giPaymentController {

    private PaymentService paymentService;

   public PaymentController(@Qualifier("StripePaymentGateway") PaymentService paymentService){
        this.paymentService=paymentService;
    }

    //POST-> http://localhost:8080/paayments
    @PostMapping()
    public String generatePaymentLink(@RequestBody GeneratePaymentLinkDto requestDto) throws StripeException, RazorpayException {

       //Ideally we should handle the exception in the Controller using Controller Advice.
        return paymentService.generatePaymentLink(requestDto.getOrderId());
    }

    @PostMapping("/webhook")
    public void handleWebhookEvent(@RequestBody Object object)
    {
        System.out.println("Webhook triggered");
    }
}
