package com.casaba.lab8.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor  // ADD THIS
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    private String description;

    private Integer quantity;

    // Comment out Invoice relationship for now
    // @ManyToMany(mappedBy = "products")
    // private List<Invoice> invoices = new ArrayList<>();
}