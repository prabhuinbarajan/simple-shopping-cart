package com.bhn.shoppingcart.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


/**
 *     name: "HAPPY HOLIDAYS SWAP $50.00",
 *     product_configuration_id: "L769TTATAB8QF4DWS1T9CC6R3B",
 *     description: "Enjoy Your Happy Holidays Swap Gift Card!",
 *     terms_and_conditions: "https://www.giftcards.com/gift-card-balance-check/happy-holidays#faq",
 *     image_url: "https://mediacdn.giftcards.com/giftcards/bhn-catalog-product-images/07675037888.png2d78a3d8bdf44cfa1fd6f372a640e1e68ba7102c.png",
 *     price: 55.00,
 *     activation_amount: 50.00,
 *     currency: "USD",
 *     tax: 0.00,
 *     category: "DIGITAL_GOODS"
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("tax")
    private BigDecimal tax;
    @JsonProperty("terms_and_conditions")
    private String termsAndConditions;
    @JsonProperty("activation_amount")
    private BigDecimal activationAmount;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("category")
    private String category;
    @JsonProperty("product_configuration_id")
    private String productConfigurationId;
}
