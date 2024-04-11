package org.example.Routes;

import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.Controller.HealthProductController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class HealthProductRoutes {

    static HealthProductController healthProductController = HealthProductController.getInstance();

    public static EndpointGroup getHealthProductRoutes() {
        return () -> {
            path("/healthshop", () -> {
                get("/api/healthproducts", logAndHandle(healthProductController.getAll()));
                get("/api/healthproducts/{id}", logAndHandle(healthProductController.getById()));
                post("/api/healthproducts", logAndHandle(healthProductController.create()));
                put("/api/healthproducts/{id}", logAndHandle(healthProductController.update()));
            });
        };
    }

    private static Handler logAndHandle(Handler handler) {
        return ctx -> {
            try {
                logRequest(ctx);
            } catch (Exception e) {
                // Log the error if any, but continue with the request
                e.printStackTrace();
            }
            handler.handle(ctx);
        };
    }

    private static void logRequest(Context ctx) {
        Map<String, Object> logData = new HashMap<>();
        logData.put("timestamp", LocalDateTime.now().toString());
        logData.put("method", ctx.method());
        logData.put("path", ctx.path());
        logData.put("status", ctx.status());
        logData.put("ip", ctx.ip());

        // Convert logData to JSON and set it as response properties
        ctx.json(logData);
    }
}
