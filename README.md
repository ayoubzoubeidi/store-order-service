## Project for developing cloud microservices using spring cloud (Work in Progress).

![image](https://github.com/ayoubzoubeidi/store-config-repo/blob/main/system%20design.png?raw=true)



## Services:
* ### [store-product-service](https://github.com/ayoubzoubeidi/store-product-service) :
  * Registering products, getting information about products. Reactive Stack.
* ### [store-inventory-service](https://github.com/ayoubzoubeidi/store-inventory-service) :
  * Managing the inventory, order allocation and de-allocation.
* ### [store-order-service](https://github.com/ayoubzoubeidi/store-baseOrder-service) :
  * Managing [Orchestration-based saga](https://microservices.io/patterns/data/saga.html). Spring State Machine.
* ### [store-shipping-service](https://github.com/ayoubzoubeidi/store-shipping-service) :
  * Mock shipping process.
* ### [store-account-service](https://github.com/ayoubzoubeidi/store-account-service) :
  * Manage user accounts with nodeJs.
* ### [store-gateway](https://github.com/ayoubzoubeidi/store-gateway) :
  * Gateway service for store.
* ### [store-eureka-server](https://github.com/ayoubzoubeidi/store-eureka-server) :
  * Eureka server for service registry/discovery.
* ### [store-config-server](https://github.com/ayoubzoubeidi/store-config-server) :
  * Spring cloud config server for services configuration.
* ### [store-config-repo](https://github.com/ayoubzoubeidi/store-config-repo) :
  * Spring cloud config repo for storing configurations.

![image](https://github.com/ayoubzoubeidi/store-config-repo/blob/main/services.png?raw=true)