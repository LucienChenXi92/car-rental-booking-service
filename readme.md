
# Car Rental Booking Service

[Live Demo](http://demo.lucienchen.xyz)

### Scope

This is a simple car rental booking API service, it provides Restful APIs to support `customers` to search/create/update/delete their rental orders. 
The core of this service is to constantly maintain stock information when process rental orders. So, in order to keep this project as simple as possible, 
we only keep three modules `rental order`, `stock` and `car`. As for `payment`, `customer` modules are not in scope. 

### Design

This service is implemented base on SpringMVC + Spring-boot + JPA + MySQL.
 
#### DB Schema

**TABLE - rental_order**
| Field | Type | Null | Key | Extra |
| -- | -- | -- | -- | -- |
| rental_order_id | smallint | NO | PRI | auto_increment |
| user_id | varchar(20) | NO |  |  |
| stock_id | smallint | NO | | |
| rental_start_time | timestamp | YES | | |
| rental_target_end_time | timestamp | YES | | |
| rental_actual_end_time | timestamp | YES | | |
| last_update | timestamp | NO | | |
| delete | tinyint | NO | | |

**NOTICE**  
Field `rental_actual_end_time` is null by default. Only when customers return cars to the company, then it will be assigned a non-null timestamp. So this field can help to indicate the order status.  
Field `delete` is actually a soft delete solution, when customs delete specific order, the delete value will turn to `1`.  
Only when field `rental_actual_end_time` is non-null, can customers delete rental orders.

**TABLE - stock**  
| Field | Type | Null | Key | Extra |
| -- | -- | -- | -- | -- |
| stock_id | smallint | NO | PRI | auto_increment |
| car_id | smallint | No | | |
| current_rental_order_id | smallint | YES | | |

**NOTICE**  
Because the business scenario is car rental, so each stock is very expensive and should be easy to track. So I added a field `current_rental_order_id` under into stock table. Default value is `"-1"` and it means available, when this stock is in used, will sync the rental_order_id here, with this, it will be easy to track the situation.

**TABLE - car**
| Field | Type | Null | Key | Extra | 
| -- | -- | -- | -- | -- | 
| car_id | smallint | NO | PRI | auto_increment |
| brand | varchar(20) | YES | | |
| model | varchar(20) | YES | | |
| cost_per_day | decimal(4,2) | YES | |

#### APIs Spec
[Swagger entrance](http://service.lucienchen.xyz/swagger-ui.html#/)
![api-spec.png](/docs/api-specs.png)


#### 
