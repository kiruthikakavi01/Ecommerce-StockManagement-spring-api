package com.stockmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.stockmanagement.dto.MessageDTO;
import com.stockmanagement.model.Stock;
import com.stockmanagement.service.StockService;
import com.stockmanagement.validator.StockValidator;

@RestController
public class StockController {
	@Autowired
	StockService stockService;
	@Autowired
	StockValidator stockValidator;

	@PostMapping("stock/save")
	public ResponseEntity<?> addStock(@RequestBody Stock stock) {
		try {
			stockService.save(stock);
			MessageDTO message = new MessageDTO("success");
			return new ResponseEntity<>(message, HttpStatus.OK);
		} catch (Exception e) {
			MessageDTO message = new MessageDTO(e.getMessage());
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("stock/list")
	public List<Stock> listStock() {
		List<Stock> findAll = null;
		try {
			findAll = stockService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return findAll;
	}

	@GetMapping("stock/{id}") // find by Dish Id
	public Stock findById(@PathVariable("id") Integer id) {
		System.out.println("findById " + id);
		Stock stock = stockService.findById(id);
		return stock;
	}

	@GetMapping("stock/product/{id}")
	public Stock findByProductId(@PathVariable("id") Integer id) {
		System.out.println("findByProductId" + id);
		Stock stock = stockService.findByProductId(id);
		return stock;

	}

	@PostMapping("stock/reduce")
	public ResponseEntity<?> reduceStock(@RequestBody Stock stock) {
		MessageDTO message = null;
		try {
			boolean b = stockValidator.stockValidation(stock);
			if (b == true) {
				stockService.reduceByProductId(stock);
				message = new MessageDTO("success");
				return new ResponseEntity<>(message, HttpStatus.OK);
			} else if (b == false) {
				message = new MessageDTO("Out of Stock");
				return new ResponseEntity<>(message, HttpStatus.OK);
			}
		} catch (Exception e) {
			message = new MessageDTO(e.getMessage());
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

		}
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	@PostMapping("stock/reduce/bulk")
	public ResponseEntity<?> reduceStockb(@RequestBody List<Stock> stocks) {

		MessageDTO message = null;
		try {
			for (Stock stock : stocks) {
				boolean b = stockValidator.stockValidation(stock);
				if (b == true) {
					stockService.reduceByProductId(stock);
					message = new MessageDTO("success");
					// return new ResponseEntity<>(message, HttpStatus.OK);
				} else if (b == false) {
					message = new MessageDTO("Out of Stock");
					// return new ResponseEntity<>(message, HttpStatus.OK);
				}
			}

		} catch (Exception e) {
			message = new MessageDTO(e.getMessage());
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

		}
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

}
