package com.casaba.lab8.service;

import com.casaba.lab8.entity.Customer;
import com.casaba.lab8.entity.Invoice;
import com.casaba.lab8.entity.Product;
import com.casaba.lab8.repository.CustomerRepository;
import com.casaba.lab8.repository.InvoiceRepository;
import com.casaba.lab8.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    public Invoice createInvoice(Long customerId, List<Long> productIds) {
        // Find customer
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Find products
        List<Product> products = productRepository.findAllById(productIds);

        // Calculate total
        Double totalAmount = products.stream()
                .mapToDouble(product -> product.getPrice())  // Use lambda instead of method reference
                .sum();

        // Create invoice
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber("INV-" + System.currentTimeMillis());
        invoice.setCustomer(customer);
        invoice.setProducts(products);
        invoice.setTotalAmount(totalAmount);

        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    public List<Invoice> getInvoicesByCustomer(Long customerId) {
        return invoiceRepository.findByCustomerId(customerId);
    }
}