package com.to.wms.controller;

import com.to.wms.controller.dto.GenericResponseDto;
import com.to.wms.controller.dto.product.ProductGetDto;
import com.to.wms.controller.dto.product.ProductPostDto;
import com.to.wms.controller.dto.product.ProductPutDto;
import com.to.wms.controller.dto.product.ProductWithLocationGetDto;
import com.to.wms.model.Product;
import com.to.wms.service.ProductService;
import com.to.wms.service.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductWithLocationGetDto>> getAllProducts() {
        List<ProductWithLocationGetDto> products = productService.getAll();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/{productName}/quantity")
    public ResponseEntity<Integer> getProductQuantity(@PathVariable String productName) {
        Integer quantity = productService.getProductQuantity(productName);
        return ResponseEntity.ok(quantity);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<ProductWithLocationGetDto>> getProductByName(
            @PathVariable String name

    ) throws ProductNotFoundException {
        List<ProductWithLocationGetDto> product = productService.getProductByName(name);
        return ResponseEntity.ok(product);
    }

    @GetMapping(params = {"department"})
    public ResponseEntity<List<ProductGetDto>> getProductsByLocation(
            @RequestParam String department
    ) throws DepartmentNotFoundException {
        List<ProductGetDto> products = productService.getProductsByLocation(department);
        return ResponseEntity.ok(products);
    }

    @GetMapping(params = {"category"})
    public ResponseEntity<List<ProductWithLocationGetDto>> getProductsByCategory(@RequestParam String category) throws CategoryNotFoundException {
        List<ProductWithLocationGetDto> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    @PostMapping()
    public ResponseEntity<ProductWithLocationGetDto> addProduct(
            @RequestBody @Valid ProductPostDto productPostDto,
            @RequestParam String department,
            @RequestParam String category,
            @RequestParam String shelf
    ) throws CategoryNotFoundException, LocationNotFoundException, ProductAlreadyExistException, DepartmentNotFoundException {
        ProductWithLocationGetDto productGetDto = productService.addProduct(productPostDto, category, department, shelf);
        return new ResponseEntity<>(productGetDto, HttpStatus.CREATED);
    }

    @PutMapping("{productName}/category")
    public ResponseEntity<ProductWithLocationGetDto> updateProductCategory(
            @PathVariable String productName,
            @RequestParam String department,
            @RequestParam("new_category") String category,
            @RequestParam String shelf
    ) throws CategoryNotFoundException, ProductNotFoundException, LocationNotFoundException, DepartmentNotFoundException {
        ProductWithLocationGetDto product = productService.updateProductCategory(productName, department, shelf, category);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PatchMapping("{productName}/quantity")
    public ResponseEntity<ProductWithLocationGetDto> editProductQuantity(
            @PathVariable String productName,
            @RequestParam String department,
            @RequestParam String shelf,
            @RequestParam("new_quantity") Integer newQuantity
    ) throws ProductNotFoundException, LocationNotFoundException, DepartmentNotFoundException {
        ProductWithLocationGetDto product = productService.editProductQuantity(productName, department, shelf, newQuantity);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/{productName}")
    public ResponseEntity<ProductWithLocationGetDto> editProduct(
            @PathVariable String productName,
            @RequestParam String department,
            @RequestParam String shelf,
            @RequestBody @Valid ProductPutDto productPutDto
            ) throws ProductNotFoundException, LocationNotFoundException, DepartmentNotFoundException, ProductAlreadyExistException {
        ProductWithLocationGetDto product = productService.editProduct(productName, department, shelf, productPutDto);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/{productName}")
    public  ResponseEntity<GenericResponseDto> deleteById(
            @PathVariable String productName,
            @RequestParam String department,
            @RequestParam String shelf
    ) throws ProductNotFoundException, LocationNotFoundException, DepartmentNotFoundException {
        GenericResponseDto responseDto = productService.delete(productName, department, shelf);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
