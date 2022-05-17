package com.stockmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stockmanagement.dao.StockRepository;
import com.stockmanagement.model.Stock;
@Service
public class StockService {
	@Autowired
	StockRepository stockRepository;
	public void save(Stock stock) throws Exception  {
		try {
			Optional<Stock> stockObj = stockRepository.findByProductId(stock.getProductId());
			if(stockObj.isPresent()) {
			stockRepository.updateByProductId(stock.getQuantity(),stock.getProductId());
				
			}else {
				stockRepository.save(stock);
			}
		} catch (Exception e) {
			
			throw new Exception(e.getMessage());
		}	
	}
	public List<Stock> findAll() throws Exception{
		List<Stock> listStock=null;
		try {
			listStock=stockRepository.findAll();	
		}catch(Exception e) {
				throw new Exception(e.getMessage());
		}
		return listStock;
	}
public Stock findById(Integer id) {
		
		Optional<Stock> stock = stockRepository.findById(id);
		if (stock.isPresent()) {
			return stock.get();
		} else {
			return null;
		}
	}
public Stock findByProductId(Integer id) {
	Optional<Stock> stock = stockRepository.findByProductId(id);
	if (stock.isPresent()) {
		return stock.get();
	} else {
		return null;
	}
}
public void reduceByProductId(Stock stock) {
	stockRepository.reduceStockByProductId(stock.getQuantity(),stock.getProductId());
}

}
