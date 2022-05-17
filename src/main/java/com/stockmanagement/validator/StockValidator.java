package com.stockmanagement.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stockmanagement.model.Stock;
import com.stockmanagement.service.StockService;
@Component
public class StockValidator {
	@Autowired
	StockService stockService;
public Boolean stockValidation(Stock stock) {
	Stock st = stockService.findByProductId(stock.getProductId());
	if(stock.getQuantity()<=st.getQuantity()) {
		return true;
	} else {
	}
	return false;
	
}
}
