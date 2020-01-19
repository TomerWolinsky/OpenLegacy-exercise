package com.example.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@ApiModel(description = "All details about the Item.")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "Item ID generated automatically")
    private Long itemId;
    @ApiModelProperty(notes = "Item name")
    private String name;
    @ApiModelProperty(notes = "Item amount in stock")
    private int amount;
    @ApiModelProperty(notes = "Item inventory code")
    private String inventoryCode;

    public Item() {

    }

    public Item(String name, int amount, String inventoryCode) {
        this.name = name;
        this.amount = amount;
        this.inventoryCode = inventoryCode;
    }
}
