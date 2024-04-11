package org.example.DAO;

import org.example.DTO.HealthProductDTO;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class HealthProductDAOMock implements iDAO<HealthProductDTO, HealthProductDTO> {
    private static final HealthProductDAOMock instance = new HealthProductDAOMock();
    private static Map<Integer, HealthProductDTO> healthProducts;

    static {
        healthProducts = new HashMap<>();
        populateData();
    }

    private HealthProductDAOMock() {
    }

    public static HealthProductDAOMock getInstance() {
        return instance;
    }

    private static void populateData() {
        healthProducts.put(1, new HealthProductDTO(1, "Vitamins", "Multivitamin", 20, 25.99, "A comprehensive daily multivitamin", LocalDate.of(2024, 12, 31)));
        healthProducts.put(2, new HealthProductDTO(2, "Supplements", "Omega-3", 15, 19.50, "Fish oil supplement rich in omega-3", LocalDate.of(2025, 6, 30)));
        healthProducts.put(3, new HealthProductDTO(3, "Personal Care", "Aloe Vera Gel", 5, 12.99, "Soothing and moisturizing aloe vera gel", LocalDate.of(2023, 10, 15)));
        healthProducts.put(4, new HealthProductDTO(4, "Vitamins", "Vitamin C", 0,9.99, "Immune system support with vitamin C", LocalDate.of(2024, 8, 20)));
        healthProducts.put(5, new HealthProductDTO(5, "Supplements", "Protein Powder", 120, 29.99, "Whey protein powder for muscle recovery", LocalDate.of(2023, 11, 30)));
    }

    public Set<HealthProductDTO> getAll() {
        return new HashSet<>(healthProducts.values());
    }

    public HealthProductDTO getById(int id) {
        return healthProducts.get(id); // Consider explicitly handling null if needed
    }


    public HealthProductDTO create(HealthProductDTO healthProduct) {
        if (!healthProducts.containsKey(healthProduct.getId())) {
            healthProducts.put(healthProduct.getId(), healthProduct);
            return healthProduct;
        } else {
            // Handle the case where the ID already exists, e.g., throw an exception
            // For simplicity, returning null or logging can be an option
            System.err.println("Product with ID " + healthProduct.getId() + " already exists.");
            return null;
        }
    }

    public HealthProductDTO update(HealthProductDTO healthProduct) {
        if (healthProducts.containsKey(healthProduct.getId())) {
            healthProducts.put(healthProduct.getId(), healthProduct);
            return healthProduct;
        } else {
            // Handle the case where the ID does not exist, e.g., throw an exception
            System.err.println("Product with ID " + healthProduct.getId() + " does not exist.");
            return null;
        }
    }

    public HealthProductDTO delete(int id) {
        return healthProducts.remove(id);
    }

    public Set<HealthProductDTO> getTwoWeeksToExpire() {
        LocalDate twoWeeksFromNow = LocalDate.now().plusWeeks(2);
        return healthProducts.values().stream()
                .filter(p -> p.getExpireDate().isBefore(twoWeeksFromNow) || p.getExpireDate().isEqual(twoWeeksFromNow))
                .collect(Collectors.toSet());
    }

    public Set<HealthProductDTO> getLowCalorieProducts() {
        return healthProducts.values().stream()
                .filter(p -> p.getCalories() < 50)
                .collect(Collectors.toSet());
    }

    public List<String> getProductNames(List<HealthProductDTO> productList) {
        return productList.stream()
                .map(HealthProductDTO::getName)
                .collect(Collectors.toList());
    }

    public Map<String, Double> getTotalPriceByCategory() {
        return healthProducts.values().stream()
                .collect(Collectors.groupingBy(
                        HealthProductDTO::getCategory,
                        Collectors.summingDouble(HealthProductDTO::getPrice)
                ));
    }

    public Set<HealthProductDTO> getByCategory(String category) {
        return healthProducts.values().stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toSet());
    }
}
