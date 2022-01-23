package com.to.wms.service;

import com.to.wms.controller.dto.GenericResponseDto;
import com.to.wms.controller.dto.product.*;
import com.to.wms.model.Category;
import com.to.wms.model.Department;
import com.to.wms.model.Location;
import com.to.wms.model.Product;
import com.to.wms.repository.CategoryRepository;
import com.to.wms.repository.DepartmentRepository;
import com.to.wms.repository.LocationRepository;
import com.to.wms.repository.ProductRepository;
import com.to.wms.service.exceptions.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;
    private final DepartmentRepository departmentRepository;
    private final ProductMapper mapper;

    public ProductService(
            ProductRepository productRepository,
            LocationRepository locationRepository,
            CategoryRepository categoryRepository,
            DepartmentRepository departmentRepository,
            ProductMapper productMapper
    ) {
        this.productRepository = productRepository;
        this.locationRepository = locationRepository;
        this.categoryRepository = categoryRepository;
        this.departmentRepository = departmentRepository;
        this.mapper = productMapper;
    }

    @Transactional(readOnly = true)
    public List<ProductWithLocationGetDto> getProductByName(String productName) throws ProductNotFoundException {
        List<Product> products = productRepository.findByName(productName);
        if (products.size() == 0) {
            throw new ProductNotFoundException();
        }
        return products.stream()
                .map(mapper::productToProductWithLocationGetDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    public List<ProductGetDto> getProductsByLocation(String departmentName) throws DepartmentNotFoundException {
        departmentRepository.findDepartmentByName(departmentName).orElseThrow(DepartmentNotFoundException::new);
        return productRepository.findAllProductsByDepartmentName(departmentName).stream()
                .map(mapper::productToProductGetDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    public List<ProductWithLocationGetDto> getProductsByCategory(String categoryName) throws CategoryNotFoundException {
        if (categoryRepository.findByName(categoryName).isEmpty()) {
            throw new CategoryNotFoundException();
        }
        return productRepository.findAllProductsByCategoryName(categoryName).stream()
                .map(mapper::productToProductWithLocationGetDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public Integer getProductQuantity(String productName) {
        List<Product> products = productRepository.findByName(productName);
        return products.stream()
                .map(Product::getQuantity)
                .mapToInt(i -> i)
                .sum();
    }

    @Transactional
    public ProductWithLocationGetDto addProduct(ProductPostDto productPostDto, String categoryName, String departmentName, String shelf) throws CategoryNotFoundException, LocationNotFoundException, ProductAlreadyExistException, DepartmentNotFoundException {
        Category category = categoryRepository.findByName(categoryName).orElseThrow(CategoryNotFoundException::new);
        Location location = findProductLocation(departmentName, shelf);

        if (productRepository.findProductByNameAndLocation(productPostDto.getName(), location).isPresent()) {
            throw new ProductAlreadyExistException();
        }
        Product product = mapper.productPostDtoToProduct(productPostDto);
        product.setLocation(location);
        product.setCategory(category);
        location.getProducts().add(product);
        productRepository.save(product);
        locationRepository.save(location);
        return mapper.productToProductWithLocationGetDto(product);
    }

    private Location findProductLocation(String departmentName, String shelf) throws DepartmentNotFoundException, LocationNotFoundException {
        Department department = departmentRepository.findDepartmentByName(departmentName).orElseThrow(DepartmentNotFoundException::new);
        return locationRepository.findByDepartmentAndShelf(department, shelf).orElseThrow(LocationNotFoundException::new);
    }

    public ProductWithLocationGetDto updateProductCategory(String productName, String departmentName, String shelf, String categoryName) throws LocationNotFoundException, DepartmentNotFoundException, CategoryNotFoundException, ProductNotFoundException {
        Category category = categoryRepository.findByName(categoryName).orElseThrow(CategoryNotFoundException::new);
        Location location = findProductLocation(departmentName, shelf);
        Product product = productRepository.findProductByNameAndLocation(productName, location).orElseThrow(ProductNotFoundException::new);
        product.setCategory(category);
        return mapper.productToProductWithLocationGetDto(productRepository.save(product));
    }

    public ProductWithLocationGetDto editProductQuantity(String productName, String departmentName, String shelf, Integer quantity) throws LocationNotFoundException, DepartmentNotFoundException, ProductNotFoundException {
        Location location = findProductLocation(departmentName, shelf);
        Product product = productRepository.findProductByNameAndLocation(productName, location).orElseThrow(ProductNotFoundException::new);
        product.setQuantity(quantity);
        return mapper.productToProductWithLocationGetDto(productRepository.save(product));
    }

    public ProductWithLocationGetDto editProduct(String productName, String departmentName, String shelf, ProductPutDto productPutDto) throws LocationNotFoundException, DepartmentNotFoundException, ProductNotFoundException, ProductAlreadyExistException {
        Location location = findProductLocation(departmentName, shelf);
        Product product = productRepository.findProductByNameAndLocation(productName, location).orElseThrow(ProductNotFoundException::new);
        if (productRepository.findProductByNameAndLocation(productPutDto.getName(), location).isPresent()) {
            throw new ProductAlreadyExistException();
        }
        product.setDescription(productPutDto.getDescription());
        product.setQuantity(productPutDto.getQuantity());
        return mapper.productToProductWithLocationGetDto(productRepository.save(product));
    }

    public List<ProductWithLocationGetDto> getAll() {
        return productRepository.findAll().stream()
                .map(mapper::productToProductWithLocationGetDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public GenericResponseDto delete(String productName, String departmentName, String shelf) throws LocationNotFoundException, DepartmentNotFoundException, ProductNotFoundException {
        Location location = findProductLocation(departmentName, shelf);
        Product product = productRepository.findProductByNameAndLocation(productName, location).orElseThrow(ProductNotFoundException::new);
        productRepository.delete(product);
        return new GenericResponseDto("Successfully deleted product!");
    }


}
