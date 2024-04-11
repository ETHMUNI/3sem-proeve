package org.example.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.example.DTO.HealthProductDTO;

import java.util.HashSet;
import java.util.Set;

public class HealthProductDAO implements iDAO<HealthProductDTO, HealthProductDTO> {

    private final EntityManagerFactory entityManagerFactory;

    public HealthProductDAO(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Set<HealthProductDTO> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return new HashSet<>(entityManager.createQuery("SELECT hp FROM HealthProduct hp", HealthProductDTO.class).getResultList());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public HealthProductDTO getById(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(HealthProductDTO.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public HealthProductDTO create(HealthProductDTO healthProduct) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(healthProduct);
            transaction.commit();
            return healthProduct;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public HealthProductDTO update(HealthProductDTO healthProduct) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            HealthProductDTO updatedHealthProduct = entityManager.merge(healthProduct);
            transaction.commit();
            return updatedHealthProduct;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public HealthProductDTO delete(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            HealthProductDTO healthProduct = entityManager.find(HealthProductDTO.class, id);
            if (healthProduct != null) {
                entityManager.remove(healthProduct);
                transaction.commit();
                return healthProduct;
            } else {
                throw new IllegalArgumentException("Health product not found");
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }
}
