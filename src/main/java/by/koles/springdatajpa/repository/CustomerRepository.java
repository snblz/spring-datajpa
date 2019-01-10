package by.koles.springdatajpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import by.koles.springdatajpa.entity.Customer;

@org.springframework.stereotype.Repository
public interface CustomerRepository extends Repository<Customer, Integer> {
	
	@Query(value = "SELECT * FROM tracker.customer", nativeQuery = true)
	List<Customer> findAll();
	
	Customer findById(int id);
	
	Customer save(Customer persisted);
	
	void delete(Customer deleted);
	
}
