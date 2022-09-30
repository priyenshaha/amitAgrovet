package com.amitagrovet.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amitagrovet.entity.Order;
import com.amitagrovet.service.ReportService;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Value("${jasper.template.path}")
	String templatePath;
	
	@Value("${jasper.output.path}")
	String outputPath;

	@Override
	public String generateReportPdf(Order order) {
		String path="";
		Map<String, Object> params = new HashMap<>();
		populateParams(order, params);
		try {
			JasperReport report = JasperCompileManager.compileReport(templatePath);
			JasperPrint jp = JasperFillManager.fillReport(report, params, new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jp, outputPath+"bill_"+order.getParty().getName()+"_"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HHmmss"))+".pdf");
			path=outputPath+"bill_"+order.getParty().getName()+"_"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HHmmss"))+".pdf";
		} catch (JRException e) {
			e.printStackTrace();
		}
		return path;
	}

	private void populateParams(Order order, Map<String, Object> params) {
		BigDecimal price = order.getParty().getPrice();
		BigDecimal amount = price.multiply(BigDecimal.valueOf(order.getQuantity()));

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

}
