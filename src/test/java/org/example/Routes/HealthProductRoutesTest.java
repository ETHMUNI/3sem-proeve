package org.example.Routes;
import io.javalin.Javalin;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import org.example.Config.HibernateConfig;
import org.example.Populator;
import org.example.Routes.HealthProductRoutes;
import org.junit.jupiter.api.*;

import jakarta.persistence.EntityManagerFactory;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HealthProductRoutesTest {

    private Javalin app;
    private EntityManagerFactory emf;
    private EntityManager em;
    private static final int TEST_PORT = 7070;

    @BeforeAll
    public void setup() {
        emf = HibernateConfig.getEntityManagerFactory(true);
        app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
        }).start(TEST_PORT);

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = TEST_PORT;

        app.routes(HealthProductRoutes.getHealthProductRoutes());
    }

    @BeforeEach
    public void beforeEachTest() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        // Clean up the database tables
        em.createQuery("DELETE FROM HealthProduct").executeUpdate();
        em.createQuery("DELETE FROM Storage").executeUpdate();
        em.getTransaction().commit();
        em.clear();
        em.close();

        new Populator(emf).populate();
    }

    @AfterEach
    public void afterEachTest() {
        if (em.isOpen()) {
            em.close();
        }
    }

    @AfterAll
    public void tearDown() {
        app.stop();
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    @Test
    public void testGetAllHealthProducts() {
        RestAssured.given()
                .accept("application/json")
                .when()
                .get("/healthshop/api/healthproducts")
                .then()
                .statusCode(200)
                .contentType("application/json");
    }

    @Test
    public void testGetHealthProductById() {
        // First, fetch all products to get an existing ID
        int firstProductId = RestAssured.given()
                .accept("application/json")
                .when()
                .get("/healthshop/api/healthproducts")
                .then()
                .statusCode(200)
                .extract().jsonPath().getInt("find { it.id }.id"); // Assuming the JSON structure has an 'id' field at each item

        // Now, use the retrieved ID to fetch a specific product
        RestAssured.given()
                .accept("application/json")
                .when()
                .get("/healthshop/api/healthproducts/" + firstProductId)
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", equalTo(firstProductId));
    }


    @Test
    public void testCreateHealthProduct() {
        String json = """
            {
              "id": 6,
              "category": "Vitamins",
              "name": "Vitamin C",
              "calories": 5,
              "description": "Supports immune system",
              "price": 12.50
            }
            """;
        RestAssured.given()
                .contentType("application/json")
                .accept("application/json")
                .body(json)
                .when()
                .post("/healthshop/api/healthproducts")
                .then()
                .statusCode(201)
                .body("name", equalTo("Vitamin C"));
    }

    @Test
    public void testUpdateHealthProduct() {
        String json = """
            {
              "category": "Vitamins",
              "name": "Vitamin D Plus",
              "calories": 10,
              "description": "Enhanced formula for bone health",
              "price": 15.50,
              "expireDate": "2026-04-11"
            }
            """;
        RestAssured.given()
                .contentType("application/json")
                .accept("application/json")
                .body(json)
                .when()
                .put("/healthshop/api/healthproducts/6")
                .then()
                .statusCode(200)
                .body("name", equalTo("Vitamin D Plus"));
    }
}