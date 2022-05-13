package com.stockmanagement.dao;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.stockmanagement.model.Stock;
@Repository
public interface StockRepository extends JpaRepository<Stock,Integer> {

	Optional<Stock> findByProductId(Integer productId);
	@Transactional
	@Modifying
    @Query("UPDATE Stock u set u.quantity=u.quantity+:quantity WHERE u.productId=:id")
    public  void updateByProductId(@Param("quantity")Integer quantity,@Param("id")Integer id);
	
	@Transactional
	@Modifying
	@Query("UPDATE Stock u set u.quantity=u.quantity-:quantity WHERE u.productId=:id")
	public void reduceStockByProductId(@Param("quantity")Integer quantity,@Param("id") Integer id);
}
