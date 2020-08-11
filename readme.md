
## car-rental

### Scope

This is a simple car rental booking API service, it provides Restful APIs to support customers to search/create/update/delete their rental orders. 
The core of this service is to constantly maintain stock information when process rental orders. So, in order to keep this project simple, 
we only keep 3 modules `rental order`, `stock` and `car`. As for `payment`, `customer` are not in scope.

### Design

This service is implemented base on SpringMVC + Spring-boot + JPA + MySQL. 
So `transaction management` is requested to help us achieve the business `consistency` and `isolation`.

#### DB Schema

TABLE rental_order

| Field | Type | Null | Key | Default | Extra |
| -- | -- | -- | -- | -- | -- |
| stock_id | smallint | NO | PRI | null | auto_increment |
| car_id | smallint | No | | null | |
| current_rental_order_id | smallint | YES | | null | |

#### APIs Spec

![api-spec.png](/docs/api-specs.png)
