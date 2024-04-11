**Task 1**

1.5


| HTTP Method | REST Resource            | JSON                                                                                                                                                                                     | Description                                                                                                          |
|-------------|--------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------|
| GET         | /api/healthproducts      | [{"id": 4, "category": "Personal Care", "name": "Aloe Vera Gel", "calories": 5, "price": 12.99,"description": "Soothing and moisturizing aloe vera gel", "expireDate": "2026-04-12"}.... | Retrieve all health products                                                                                         |
| GET         | /api/healthproducts/{id} | {"id": 1, "category": "Vitamins", "name": "Multivitamin", "calories": 20, "price": 25.99, "description": "A comprehensive daily vitamins", "expireDate": "2026-04-12" }                                                                                                                                           | Retrieve a specific product by ID                                                                                    |
| POST        | /api/healthproducts      | {"id": 6, "category": "Vitamins", "name": "Vitamin C","calories": 5, "price": 12.5, "description": "Supports immune system","expireDate": "2026-04-12"}                                                                                                                                                           | Add a new healthproduct. The created product object should be returned with the assigned id and an expireDate field. |
| PUT         | /api/healthproducts/{id} | {"id": 6, "category": "Vitamins", "name": "Vitamin D Plus","calories": 10, "price": 15.5, "description": "Enhanced formula for bone health", "expireDate": "2026-04-11" }                                                                                                                                         | Update an existing health product by ID.                                                                             |



**Task 2**

2.1

| HTTP Method | REST Resource            | Possible Exceptions                             | Status Code |
|-------------|--------------------------|--------------------------------------------------|-------------|
| GET         | /api/healthproducts      | NoProductFoundException                          | 404         |
| GET         | /api/healthproducts/{id} | ValidationException, NoProductFoundException     | 400, 404    |
| POST        | /api/healthproducts      | ValidationException                              | 400         |
| PUT         | /api/healthproducts/{id} | ValidationException, NoProductFoundException     | 400, 404    |
| DELETE      | /api/healthproducts/{id} | ValidationException, NoProductFoundException     | 400, 404    |

2.3
I have not made a logger but instead im logging the data as JSON properties response.

**Task 4**

4.7

| HTTP Method | REST Resource            | JSON                                                                                                                                                                                     | Description                                                                                                          |
|-------------|--------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------|
| GET         | /api/healthproducts      | [{"id": 4, "category": "Personal Care", "name": "Aloe Vera Gel", "calories": 5, "price": 12.99,"description": "Soothing and moisturizing aloe vera gel", "expireDate": "2026-04-12"}.... | Retrieve all health products                                                                                         |
| GET         | /api/healthproducts/{id} | {"id": 1, "category": "Vitamins", "name": "Multivitamin", "calories": 20, "price": 25.99, "description": "A comprehensive daily vitamins", "expireDate": "2026-04-12" }                                                                                                                                           | Retrieve a specific product by ID                                                                                    |
| POST        | /api/healthproducts      | {"id": 6, "category": "Vitamins", "name": "Vitamin C","calories": 5, "price": 12.5, "description": "Supports immune system","expireDate": "2026-04-12"}                                                                                                                                                           | Add a new healthproduct. The created product object should be returned with the assigned id and an expireDate field. |
| PUT         | /api/healthproducts/{id} | {"id": 6, "category": "Vitamins", "name": "Vitamin D Plus","calories": 10, "price": 15.5, "description": "Enhanced formula for bone health", "expireDate": "2026-04-11" }                                                                                                                                         | Update an existing health product by ID.                                                                             |