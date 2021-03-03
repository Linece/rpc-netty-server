package com.zdc.netty;

import com.zdc.api.IPaymentService;

public class PaymentServiceImpl implements IPaymentService {
	@Override
	public void dopay() {
		System.out.println("执行dopay...................................");
	}
}
