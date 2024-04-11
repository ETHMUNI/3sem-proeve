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
I have not made a logger but instead i'm logging the data as JSON properties response.

**Task 4**

4.7

| HTTP Method | REST Resource            | JSON                                        | Description |
|-------------|--------------------------|---------------------------------------------|-------------|
| GET         | /api/healthproducts      | NoProductFoundException                     | 404         |
| GET         | /api/healthproducts/{id} | ValidationException, NoProductFoundException | 400, 404    |
| POST        | /api/healthproducts      | ValidationException                         | 400         |
| PUT         | /api/healthproducts/{id} | ValidationException, NoProductFoundException | 400, 404    |
| DELETE      | /api/healthproducts/{id} | ValidationException, NoProductFoundException | 400, 404    |