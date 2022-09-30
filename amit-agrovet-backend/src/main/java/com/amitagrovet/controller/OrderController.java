package com.amitagrovet.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amitagrovet.dto.ResponseDto;
import com.amitagrovet.entity.Order;
import com.amitagrovet.entity.Party;
import com.amitagrovet.entity.enums.OrderType;
import com.amitagrovet.service.OrderService;
import com.amitagrovet.service.ReportService;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ReportService reportService;
	
	@GetMapping("/number")
	ResponseEntity<?> getBillNumber(){
		return new ResponseEntity<>(new ResponseDto<>("success", orderService.getLatestBillNumber()), HttpStatus.OK);
	}
	
	@GetMapping("/all/{month}/{type}")
	ResponseEntity<?> getAllOrders(@PathVariable("month") String month, @PathVariable("type") OrderType type){
		return new ResponseEntity<>(new ResponseDto<>("success", orderService.getAllOrdersByType(type, month)), HttpStatus.OK);
	}

	@PostMapping("/new")
	ResponseEntity<?> generateBill(@RequestBody Order order){
	
		if(orderService.addOrEditOrder(order)!=null) {
		
			if(order.getOrderType().toString().equalsIgnoreCase("SELL")) {
				String pdfPath = reportService.generateReportPdf(order);
				if(pdfPath!="")		
					return new ResponseEntity<>(new ResponseDto<>("success", "Bill PDF successfully generated and saved"), HttpStatus.CREATED);
				else
					return new ResponseEntity<>(new ResponseDto<>("error", "PDF was not generated."), HttpStatus.INTERNAL_SERVER_ERROR);
			}else {
				return new ResponseEntity<>(new ResponseDto<>("success", "Purchase order saved successfully"), HttpStatus.CREATED);
			}
		}
		
		return new ResponseEntity<>(new ResponseDto<>("error", "Order was not saved. Save information offline."), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("/edit")
	ResponseEntity<?> editOrder(@RequestBody Order order){
		return new ResponseEntity<>(new ResponseDto<Order>("success", orderService.addOrEditOrder(order)), HttpStatus.CREATED);
	}
}
