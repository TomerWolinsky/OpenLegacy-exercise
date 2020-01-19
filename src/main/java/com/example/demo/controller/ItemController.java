package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ItemRepository;
import com.example.demo.model.Item;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ItemController {

    private final ItemRepository repository;

    public ItemController(ItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/stock")
    @ApiOperation(value = "Get all available Items in stock")
    List<Item> getAllInventoryItems() {
        return repository.findAll();
    }

    @GetMapping("/stock/{itemId}")
    @ApiOperation(value = "Get Item details by ID")
    ResponseEntity<Item> getItemDetails(@ApiParam(value = "Item Id for the requested item details", required = true) @PathVariable Long itemId) throws ResourceNotFoundException {
        Item item = repository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id :: " + itemId));
        return ResponseEntity.ok().body(item);
    }

    @PutMapping("/stock/withdrawal/{itemId}/{amount}")
    @ApiOperation(value = "Withdrawal quantity of a specific Item from stock")
    String withdrawalItem(@ApiParam(value = "Item Id to withdrawal from", required = true) @PathVariable Long itemId,
                          @ApiParam(value = "Amount to withdrawal", required = true) @PathVariable int amount) throws ResourceNotFoundException {
        Item item = repository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id :: " + itemId));
        item.setAmount(item.getAmount() - amount);
        return repository.save(item).toString() + " successfully updated amount";
    }

    @PutMapping("/stock/deposit/{itemId}/{amount}")
    @ApiOperation(value = "Deposit quantity of a specific Item to stock")
    String depositItem(@ApiParam(value = "Item Id to deposit to", required = true) @PathVariable Long itemId,
                       @ApiParam(value = "Amount to deposit", required = true) @PathVariable int amount) throws ResourceNotFoundException {
        Item item = repository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id :: " + itemId));
        item.setAmount(item.getAmount() + amount);
        return repository.save(item).toString() + " successfully updated amount";
    }

    @PostMapping("/stock/add")
    @ApiOperation(value = "Add Item to stock")
    String addItemToStock(@ApiParam(value = "Item to store in stock", required = true) @RequestBody Item item) {
        return repository.save(item).toString() + " added successfully to stock.";
    }

    @DeleteMapping("/stock/delete/{itemId}")
    @ApiOperation(value = "Delete Item from stock by ID")
    Map<String, Boolean> deleteItemFromStock(@ApiParam(value = "Item Id that you want to delete from stock", required = true) @PathVariable Long itemId) throws ResourceNotFoundException {
        Item item = repository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id :: " + itemId));
        repository.delete(item);
        Map<String, Boolean> response = new HashMap<>();
        response.put(item.toString() + " deleted successfully from stock.", Boolean.TRUE);
        return response;
    }
}
