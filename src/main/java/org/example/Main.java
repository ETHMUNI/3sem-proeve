package org.example;

import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;
import org.example.Routes.HealthProductRoutes;
import org.example.Config.HibernateConfig;

public class Main {
    public static void main(String[] args) {
        // Create the Javalin server
        Javalin app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
        });

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory(false); // Change to true for testing

        // Populate the database
        Populator populator = new Populator(emf);
        populator.populate();

        // Register the routes
        app.routes(HealthProductRoutes.getHealthProductRoutes());

        app.exception(Exception.class, (e, ctx) -> {
            ctx.status(500).result("Internal server error: " + e.getMessage());
        });

        app.start(7000);
    }
}
