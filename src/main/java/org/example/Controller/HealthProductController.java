package org.example.Controller;

import io.javalin.http.Handler;
import jakarta.persistence.EntityManagerFactory;
import org.example.Config.HibernateConfig;
import org.example.DAO.HealthProductDAO;
import org.example.DTO.HealthProductDTO;
import org.example.Exceptions.NoProductFoundException;
import org.example.Exceptions.ValidationException;
import org.example.Ressources.HealthProduct;

import java.time.LocalDate;
import java.util.Set;

public class HealthProductController implements IHealthProductController {

    private EntityManagerFactory emf;
    private HealthProductDAO healthProductDAO;
    private static final HealthProductController instance = new HealthProductController();

    private HealthProductController() {
        this.emf = HibernateConfig.getEntityManagerFactory(false); // Change to true for testing
        this.healthProductDAO = new HealthProductDAO(emf);
    }

    public static HealthProductController getInstance() {
        return instance;
    }

    @Override
    public Handler getAll() {
        return ctx -> {
            Set<HealthProductDTO> products = healthProductDAO.getAll();
            if (products.isEmpty()) {
                throw NoProductFoundException.create(404, "No products found.");
            } else {
                ctx.json(products);
            }
        };
    }

    @Override
    public Handler getById() {
        return ctx -> {
            int id;
            try {
                id = Integer.parseInt(ctx.pathParam("id"));
            } catch (NumberFormatException e) {
                throw ValidationException.create(400, "Invalid product ID.");
            }

            HealthProductDTO product = healthProductDAO.getById(id);
            if (product == null) {
                throw NoProductFoundException.create(404,"Product not found.");
            } else {
                ctx.json(product);
            }
        };
    }

    @Override
    public Handler create() {
        return ctx -> {
            HealthProductDTO product = ctx.bodyValidator(HealthProductDTO.class)
                    .check(dto -> dto.getPrice() > 0, "Price must be greater than 0")
                    .get();
            product.setExpireDate(LocalDate.now().plusYears(2)); // when creating a new product the years would be 2 by default
            HealthProductDTO createdProduct = healthProductDAO.create(product);
            if (createdProduct != null) {
                ctx.status(201).json(createdProduct);
            } else {
                ctx.status(500).result("Product could not be created.");
            }
        };
    }

    @Override
    public Handler update() {
        return ctx -> {
            int id;
            try {
                id = Integer.parseInt(ctx.pathParam("id"));
            } catch (NumberFormatException e) {
                throw ValidationException.create(400, "Invalid product ID.");
            }

            HealthProductDTO productToUpdate = ctx.bodyValidator(HealthProductDTO.class).get();
            productToUpdate.setId(id); // Ensure the ID is set to the path param
            HealthProductDTO updatedProduct = healthProductDAO.update(productToUpdate);

            if (updatedProduct != null) {
                ctx.json(updatedProduct);
            } else {
                throw NoProductFoundException.create("Product not found or could not be updated.");
            }
        };
    }

    @Override
    public Handler delete() {
        return ctx -> {
            int id;
            try {
                id = Integer.parseInt(ctx.pathParam("id"));
            } catch (NumberFormatException e) {
                throw ValidationException.create(400, "Invalid product ID.");
            }

            HealthProductDTO deletedProduct = healthProductDAO.delete(id);
            if (deletedProduct != null) {
                ctx.result("Product deleted successfully.");
            } else {
                throw NoProductFoundException.create(404,"Product not found.");
            }
        };
    }
}
