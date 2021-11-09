package eam.edu.ingesoft.onlinestore.controllers

import eam.edu.ingesoft.onlinestore.model.entities.Product
import eam.edu.ingesoft.onlinestore.model.entities.Store
import eam.edu.ingesoft.onlinestore.model.requests.GetProductResponse
import eam.edu.ingesoft.onlinestore.model.requests.GetStoreResponse
import eam.edu.ingesoft.onlinestore.model.requests.ProductStoreRequest
import eam.edu.ingesoft.onlinestore.services.ProductStoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/productsStore")
class ProductStoreController {
    @Autowired
    lateinit var productStoreService: ProductStoreService

    @PostMapping
    fun createProduct(@RequestBody productStoreRequest: ProductStoreRequest) {
        productStoreService.createProductStoreRequests(productStoreRequest)
    }

    @GetMapping("/{id}/products")
    fun getProductByProductStore(@PathVariable("id") idStore: String): List<Product> {
        val products = productStoreService.listProductsByProductStoreV2(idStore)

        val response = products.map {
            Product(it.id, it.name, it.branch, it.category)
        }
        return response
    }

    @GetMapping("/stores")
    fun getStoresByProductStore(): List<Store> {
        val products = productStoreService.listStoreByProductStoreV2()

        val response = products.map {
            Store(it.id, it.address, it.name, it.city)
        }
        return response
    }

    @GetMapping("/{idCategory}/categories/{idStore}/stores")
    fun getProductsCategoryByProductStore(
        @PathVariable("idCategory") idCategory: Long,
        @PathVariable("idStore") idStore: String
    ): List<Product> {
        val products = productStoreService.listProductCategoryV2(idCategory, idStore)

        val response = products.map {
            Product(it.id, it.name, it.branch, it.category)
        }
        return response
    }

}