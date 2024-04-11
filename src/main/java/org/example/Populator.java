package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.example.Ressources.HealthProduct;
import org.example.Ressources.Storage;

import java.time.LocalDate;

public class Populator {

    private final EntityManagerFactory entityManagerFactory;

    public Populator(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void populate() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            // Populate health products
            HealthProduct hp1 = new HealthProduct("Vitamins", "Multivitamin", 20, 25.99, "A comprehensive daily hp1");
            HealthProduct hp2 = new HealthProduct("Supplements", "Omega-3", 15, 19.50, "Fish oil supplement rich in omega-3");
            HealthProduct hp3 = new HealthProduct("Personal Care", "Aloe Vera Gel", 5, 12.99, "Soothing and moisturizing aloe vera gel");
            HealthProduct hp4 = new HealthProduct("Vitamins", "Vitamin C", 0, 9.99, "Immune system support with vitamin C");
            HealthProduct hp5 = new HealthProduct("Supplements", "Protein Powder", 120, 29.99, "Whey protein powder for muscle recovery");

            entityManager.persist(hp1);
            entityManager.persist(hp2);
            entityManager.persist(hp3);
            entityManager.persist(hp4);
            entityManager.persist(hp5);

            // Populate storage objects
            Storage storage1 = new Storage(LocalDate.now(), 100, 1);
            Storage storage2 = new Storage(LocalDate.now(), 200, 2);

            storage1.getHealthProducts().add(hp1);
            storage1.getHealthProducts().add(hp2);
            storage2.getHealthProducts().add(hp3);
            storage2.getHealthProducts().add(hp4);

            entityManager.persist(storage1);
            entityManager.persist(storage2);

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
}