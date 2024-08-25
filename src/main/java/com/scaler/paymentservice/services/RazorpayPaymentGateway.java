package com.scaler.paymentservice.services;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service("RazorpayPaymentGateway")
public class RazorpayPaymentGateway implements PaymentService{

    private RazorpayClient razorpayClient;

    public RazorpayPaymentGateway(RazorpayClient razorpayClient){
        this.razorpayClient=razorpayClient;
    }

    @Override
    public String generatePaymentLink(Long orderId) throws RazorpayException {
        //Make a call to Razorpay to generate the payment link
        // Initialize client


        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",1000); //10.00 //98.76  = 98.76 * 100 =9876
        paymentLinkRequest.put("currency","INR");
       // paymentLinkRequest.put("accept_partial",true);
       // paymentLinkRequest.put("first_min_partial_amount",100);
        paymentLinkRequest.put("expire_by",System.currentTimeMillis() + 10 * 60 * 1000); //10 mins TIMER
        paymentLinkRequest.put("reference_id",orderId.toString());
        paymentLinkRequest.put("description","Payment for Chocolate");
        JSONObject customer = new JSONObject();

        //Call the Orderservice to get Order details
        //Order order= restTemplate.getForObject("OrderService URL",Order.class);

        customer.put("name","Krishna Agrawal");
        customer.put("contact","8707610861");
        customer.put("email","krishnaagrawal1990@gmail.com");
        paymentLinkRequest.put("customer",customer);
        JSONObject notify = new JSONObject();
        notify.put("sms",true);
        notify.put("email",true);
        paymentLinkRequest.put("reminder_enable",true);
       JSONObject notes = new JSONObject();
       // notes.put("policy_name","Jeevan Bima");
      //  paymentLinkRequest.put("notes",notes);
        paymentLinkRequest.put("callback_url","https://scaler.com/");
        paymentLinkRequest.put("callback_method","get");

        PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);
        return payment.get("short_url");
    }
}
