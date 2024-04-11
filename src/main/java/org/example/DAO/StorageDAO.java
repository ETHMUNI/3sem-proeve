package org.example.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.example.DTO.HealthProductDTO;
import org.example.DTO.StorageDTO;
import org.example.Ressources.Storage;
import org.example.Ressources.HealthProduct;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    private StorageDTO convertToDTO(Storage storage) {
        Set<HealthProductDTO> productDTOs = storage.getHealthProducts().stream()
                .map(p -> new HealthProductDTO(p.getId(), p.getCategory(), p.getName(), p.getCalories(), p.getPrice(), p.getDescription(), p.getExpireDate()))
                .collect(Collectors.toSet());

        return new StorageDTO(
                storage.getId(),
                storage.getUpdatedTimeStamp(),
                storage.getTotalAmount(),
                storage.getShelfNumber(),
                productDTOs
        );
    }

    @Override
    public Set<StorageDTO> getAll() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT s FROM Storage s JOIN FETCH s.healthProducts", Storage.class);
            Set<Storage> storages = new HashSet<>(query.getResultList());
            return storages.stream().map(this::convertToDTO).collect(Collectors.toSet());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public StorageDTO getById(int id) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            Storage storage = entityManager.find(Storage.class, id);
            return storage != null ? convertToDTO(storage) : null;
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
            Storage storage = new Storage();
            storage.setUpdatedTimeStamp(storageDTO.getUpdatedTimeStamp());
            storage.setTotalAmount(storageDTO.getTotalAmount());
            storage.setShelfNumber(storageDTO.getShelfNumber());
            entityManager.persist(storage);
            transaction.commit();
            return convertToDTO(storage);
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
            Storage storage = entityManager.find(Storage.class, storageDTO.getId());
            if (storage != null) {
                storage.setUpdatedTimeStamp(storageDTO.getUpdatedTimeStamp());
                storage.setTotalAmount(storageDTO.getTotalAmount());
                storage.setShelfNumber(storageDTO.getShelfNumber());
                entityManager.merge(storage);
                transaction.commit();
                return convertToDTO(storage);
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

    @Override
    public StorageDTO delete(int id) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Storage storage = entityManager.find(Storage.class, id);
            if (storage != null) {
                entityManager.remove(storage);
                transaction.commit();
                return convertToDTO(storage);
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
