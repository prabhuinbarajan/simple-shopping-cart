package com.bhn.shoppingcart.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "Products")
@NoArgsConstructor
@Getter
@Setter
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "name", columnDefinition = "varchar(50)", nullable = false)
    private String name;

    @Size(max = 1024)
    @Column(name = "description", columnDefinition = "varchar(1024)", nullable = false)
    private String description;

    @NotNull
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name="currency")
    private String currency;

    @Column(name="tax")
    private BigDecimal tax;

    @Size(max = 1024)
    @Column(name="terms_and_conditions", columnDefinition = "varchar(1024)")
    private String termsAndConditions;

    @Column(name="activation_amount", nullable = false)
    private BigDecimal activationAmount;

    @Size(max = 1024)
    @Column(name="image_url", columnDefinition = "varchar(1024)")
    private String imageUrl;

    @Size(max = 100)
    @Column(name="category", columnDefinition = "varchar(100)")
    private String category;

    @Size(max = 100)
    @Column(name="product_configuration_id", columnDefinition = "varchar(100)")
    private String productConfigurationId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(getId(), product.getId()) &&
                Objects.equals(getPrice(), product.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPrice());
    }
}
