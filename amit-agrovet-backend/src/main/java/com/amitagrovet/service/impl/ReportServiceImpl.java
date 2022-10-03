package com.amitagrovet.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amitagrovet.dto.ReportOrderDto;
import com.amitagrovet.entity.Order;
import com.amitagrovet.entity.enums.OrderType;
import com.amitagrovet.service.OrderService;
import com.amitagrovet.service.ReportService;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Value("${jasper.templates.path}")
	String templatePath;
	
	@Value("${jasper.output.path}")
	String outputPath;
	
	@Value("${jasper.invoice.name}")
	String invoiceName;
	
	@Value("${jasper.report.name}")
	String reportName;
	
	@Autowired
	private OrderService orderService;
	
	@Override
	public String generateBillPdf(Order order) {
		String path="";
		Map<String, Object> params = new HashMap<>();
		populateParams(order, params);
		try {
			JasperReport report = JasperCompileManager.compileReport(templatePath+invoiceName);
			JasperPrint jp = JasperFillManager.fillReport(report, params, new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jp, outputPath+"/bills/"+"bill_"+order.getParty().getName()+"_"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HHmmss"))+".pdf");
			path=outputPath+"bill_"+order.getParty().getName()+"_"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HHmmss"))+".pdf";
		} catch (JRException e) {
			e.printStackTrace();
		}
		return path;
	}

	private void populateParams(Order order, Map<String, Object> params) {
		BigDecimal price = order.getParty().getPrice();
		BigDecimal amount = price.multiply(order.getQuantity());
		
		params.put("name", order.getParty().getName());
		params.put("address", order.getParty().getAddress());
		params.put("gstNumber", order.getParty().getGstNumber());
		params.put("stateCode", order.getParty().getStateCode());
		params.put("billNumber", order.getBillNumber());
		params.put("vehicle", order.getVehicle());
		params.put("date", order.getDate());
		params.put("quantity", order.getQuantity());
		params.put("price", price);
		params.put("amount", amount);
		params.put("totalInWords", getTotalInWords(amount));
		
	}

	private String getTotalInWords(BigDecimal amount) {
		String value = getTotalValueAfterTax(amount);
		String inWords="";
		
		try {
			inWords = NumberToWord(value);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (inWords.trim().length() == 0) {
			inWords = "Zero";
		}
		inWords = inWords + " Only.";
		return inWords;
	}

	private String getTotalValueAfterTax(BigDecimal amount) {
		return amount.multiply(BigDecimal.valueOf(0.05)).add(amount).setScale(0, RoundingMode.HALF_UP).toString();
	}

	private static String NumberToWord(String number) throws Exception {
		String twodigitword = "";
		String word = "";
		String[] HTLC = { "", "Hundred", "Thousand", "Lakh", "Crore" }; // H-hundread , T-Thousand, ..
		int split[] = { 0, 2, 3, 5, 7, 9 };
		String[] temp = new String[split.length];
		boolean addzero = true;
		int len1 = number.length();
		if (len1 > split[split.length - 1]) {
			throw new Exception("Error. Maximum Allowed digits " + split[split.length - 1]);
		}
		for (int l = 1; l < split.length; l++)
			if (number.length() == split[l])
				addzero = false;
		if (addzero == true)
			number = "0" + number;
		int len = number.length();
		int j = 0;
		// spliting & putting numbers in temp array.
		while (split[j] < len) {
			int beg = len - split[j + 1];
			int end = beg + split[j + 1] - split[j];
			temp[j] = number.substring(beg, end);
			j = j + 1;
		}

		for (int k = 0; k < j; k++) {
			twodigitword = ConvertOnesTwos(temp[k]);
			if (k >= 1) {
				if (twodigitword.trim().length() != 0)
					word = twodigitword + " " + HTLC[k] + " " + word;
			} else
				word = twodigitword;
		}
		return (word);
	}

	private static String ConvertOnesTwos(String t) {
		final String[] ones = { "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
				"Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen" };
		final String[] tens = { "", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty",
				"Ninety" };

		String word = "";
		int num = Integer.parseInt(t);
		if (num % 10 == 0)
			word = tens[num / 10] + " " + word;
		else if (num < 20)
			word = ones[num] + " " + word;
		else {
			word = tens[(num - (num % 10)) / 10] + word;
			word = word + " " + ones[num % 10];
		}
		return word;
	}

	
	@Override
	public String generateMonthlyReport(OrderType type, String month) {
		List<ReportOrderDto> reportData = orderService.getAllOrdersByType(type, month)
				.stream()
				.map(order -> { 
					ReportOrderDto newOrder = new ReportOrderDto();
					newOrder.setDate(order.getDate());
					newOrder.setBillNumber(order.getBillNumber());
					newOrder.setName(order.getParty().getName());
					newOrder.setGstNumber(order.getParty().getGstNumber());
					newOrder.setStateCode(order.getParty().getStateCode());
					newOrder.setHsnCode(order.getHsnCode());
					newOrder.setWeight(order.getQuantity());
					newOrder.setPrice(order.getParty().getPrice());
					newOrder.setFinalAmount(order.getAdjustedAmount());
					return newOrder; 
				}).collect(Collectors.toList());
		String[] monthData = month.split("-"); // value: 2022-09
		String monthName = Month.of(Integer.valueOf(monthData[1])).toString();
		String reportType = type.toString().equals("SELL")?"SALES":type.toString();
		
		Map<String, Object> params = new HashMap<>();
		params.put("reportType", reportType);
		params.put("month", monthName+" "+monthData[0]);
		if(reportData==null || reportData.isEmpty()) {
			return "No orders to generate report for "+monthName;
		}
		try {
			JasperReport report = JasperCompileManager.compileReport(templatePath+reportName);
			JasperPrint jp = JasperFillManager.fillReport(report, params, new JRBeanCollectionDataSource(reportData));
			JasperExportManager.exportReportToPdfFile(jp, outputPath+"/reports/"+"AMIT AGROVET "+" "+reportType+" "+monthName+" "+monthData[0]+".pdf");
			return "Report for "+monthName+" generated";
		} catch (JRException e) {
			e.printStackTrace();
		}
		return null;
	}

}
