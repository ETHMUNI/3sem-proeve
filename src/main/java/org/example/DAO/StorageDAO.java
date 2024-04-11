package org.example.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.example.DTO.HealthProductDTO;
import org.example.DTO.StorageDTO;

import java.util.HashSet;
import java.util.Set;

public class StorageDAO implements iDAO<StorageDTO, StorageDTO> {

    private static StorageDAO instance;
    private final EntityManagerFactory emf;

    private StorageDAO(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    public static StorageDAO getInstance(EntityManagerFactory entityManagerFactory) {
        if (instance == null) {
            instance = new StorageDAO(entityManagerFactory);
        }
        return instance;
    }

    @Override
    public Set<StorageDTO> getAll() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT s FROM Storage s", StorageDTO.class);
            return new HashSet<>(query.getResultList());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public StorageDTO getById(int id) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.find(StorageDTO.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public StorageDTO create(StorageDTO storageDTO) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(storageDTO);
            transaction.commit();
            return storageDTO;
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
    public StorageDTO update(StorageDTO storageDTO) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            StorageDTO updatedStorageDTO = entityManager.merge(storageDTO);
            transaction.commit();
            return updatedStorageDTO;
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
    public StorageDTO delete(int id) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            StorageDTO storageDTO = entityManager.find(StorageDTO.class, id);
            if (storageDTO != null) {
                entityManager.remove(storageDTO);
                transaction.commit();
                return storageDTO;
            } else {
                throw new IllegalArgumentException("Storage not found");
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
