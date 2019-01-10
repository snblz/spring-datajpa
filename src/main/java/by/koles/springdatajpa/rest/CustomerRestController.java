package by.koles.springdatajpa.rest;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.koles.springdatajpa.entity.Customer;
import by.koles.springdatajpa.repository.CustomerRepository;

@RestController
@RequestMapping("/api")
public class CustomerRestController {
	private final Locale LOCALE = Locale.ENGLISH;
	static final Logger LOG = LoggerFactory.getLogger(CustomerRestController.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping("/customers")
	public List<Customer> getCustomers() {

		LOG.info("LOCALE message:");
		LOG.info("{}", messageSource.getMessage("l1", null, LOCALE));

		System.out.println(LOG.isDebugEnabled() + " / " + LOG.isErrorEnabled() + " / " + LOG.isInfoEnabled() + " / "
				+ LOG.isTraceEnabled() + " / " + LOG.isWarnEnabled());
		
		return customerRepository.findAll();
	}

	@GetMapping("/customers/{customerId}")
	public Customer getCustomer(@PathVariable int customerId) {
		LOG.info("LOCALE message:");
		LOG.info("{}", messageSource.getMessage("l1", null, LOCALE));
		Customer customer = customerRepository.findById(customerId);
		return customer;
	}

	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer customer) {
		LOG.info("LOCALE message:");
		LOG.info("{}", messageSource.getMessage("l1", null, LOCALE));
		customerRepository.save(customer);
		return customer;
	}

	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer customer) {
		LOG.info("LOCALE message:");
		LOG.info("{}", messageSource.getMessage("l1", null, LOCALE));
		customerRepository.save(customer);
		return customer;
	}

	@DeleteMapping("/customers/{customerId}")
	public String deleteCustomer(@PathVariable int customerId) {
		LOG.info("LOCALE message:");
		LOG.info("{}", messageSource.getMessage("l1", null, LOCALE));
		customerRepository.delete(customerRepository.findById(customerId));
		return "Deleted customer id: " + customerId;
	}

}
