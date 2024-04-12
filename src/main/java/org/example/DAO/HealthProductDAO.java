package org.example.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.example.DTO.HealthProductDTO;
import org.example.Ressources.HealthProduct;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class HealthProductDAO implements iDAO<HealthProductDTO, HealthProductDTO> {

    private final EntityManagerFactory entityManagerFactory;

    public HealthProductDAO(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    // Utility method to convert HealthProduct to HealthProductDTO
    private HealthProductDTO convertToDTO(HealthProduct healthProduct) {
        return new HealthProductDTO(
                healthProduct.getId(),
                healthProduct.getCategory(),
                healthProduct.getName(),
                healthProduct.getCalories(),
                healthProduct.getPrice(),
                healthProduct.getDescription(),
                healthProduct.getExpireDate()
        );
    }

    @Override
    public Set<HealthProductDTO> getAll() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Query query = entityManager.createQuery("SELECT hp FROM HealthProduct hp JOIN FETCH hp.storage", HealthProduct.class);
            Set<HealthProduct> products = new HashSet<>(query.getResultList());
            return products.stream().map(this::convertToDTO).collect(Collectors.toSet());
        }
    }

    @Override
    public HealthProductDTO getById(int id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            HealthProduct healthProduct = entityManager.find(HealthProduct.class, id);
            return healthProduct != null ? convertToDTO(healthProduct) : null;
        }
    }

    @Override
    public HealthProductDTO create(HealthProductDTO healthProductDTO) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try (entityManager) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            HealthProduct healthProduct = new HealthProduct(
                    healthProductDTO.getCategory(),
                    healthProductDTO.getName(),
                    healthProductDTO.getCalories(),
                    healthProductDTO.getPrice(),
                    healthProductDTO.getDescription(),
                    healthProductDTO.getExpireDate()
            );
            entityManager.persist(healthProduct);
            transaction.commit();
            return convertToDTO(healthProduct);
        }
    }

    @Override
    public HealthProductDTO update(HealthProductDTO healthProductDTO) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try (entityManager) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            HealthProduct healthProduct = entityManager.find(HealthProduct.class, healthProductDTO.getId());
            if (healthProduct != null) {
                healthProduct.setCategory(healthProductDTO.getCategory());
                healthProduct.setName(healthProductDTO.getName());
                healthProduct.setCalories(healthProductDTO.getCalories());
                healthProduct.setPrice(healthProductDTO.getPrice());
                healthProduct.setDescription(healthProductDTO.getDescription());
                healthProduct.setExpireDate(healthProductDTO.getExpireDate());
                entityManager.merge(healthProduct);
            }
            transaction.commit();
            return convertToDTO(healthProduct);
        }
    }

    @Override
    public HealthProductDTO delete(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try (entityManager) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            HealthProduct healthProduct = entityManager.find(HealthProduct.class, id);
            if (healthProduct != null) {
                entityManager.remove(healthProduct);
                transaction.commit();
                return convertToDTO(healthProduct);
            } else {
                throw new IllegalArgumentException("Health product not found");
            }
        }
    }
}
