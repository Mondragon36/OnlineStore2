package eam.edu.ingesoft.onlinestore.services

import eam.edu.ingesoft.onlinestore.exceptions.BusinessException
import eam.edu.ingesoft.onlinestore.model.entities.ProductStore
import eam.edu.ingesoft.onlinestore.model.requests.ProductStoreRequest
import eam.edu.ingesoft.onlinestore.repositories.ProductStoreRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class ProductStoreService {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var productStoreRepository: ProductStoreRepository

    /**
     * c. crear productos en tienda. el mismo product no puede estar 2 veces en la tienda
     *
     * d. disminuir la cantidad de un product en la tienda. No se puede disminuir a menos de cero.
     *
     * e. Aumentar la cantidad de un product en la tienda.
     *
     * f. listar todos los productos de una tienda.
     *
     * i. listar todas las tiendas.
     */

    fun createProductStore(productStore: ProductStore) {
        val productById = productStoreRepository.find(productStore.id)

        if (productById != null) {
            throw BusinessException("This product already exists on the store")
        }
        productStoreRepository.create(productStore)
    }


    fun createProductStoreRequests(productStoreRequest: ProductStoreRequest) {
        val productStoreId = productStoreRepository.find(productStoreRequest.id_Product_store)
        if (productStoreId != null) {
            throw BusinessException("This product Store whit this name already exists")
        }
        val productStore = ProductStore(productStoreRequest.id_Product_store,productStoreRequest.price,productStoreRequest.stock,productStoreRequest.product,productStoreRequest.store)
        productStoreRepository.create(productStore)
    }

    fun decreaseStockProductStore(productStore: ProductStore, cantidadDisminuir: Double) {
        productStoreRepository.find(productStore.id)
            ?: throw BusinessException("This product in this store does not exists")
        if (productStore.stock < cantidadDisminuir) {
            throw BusinessException("There is not stock")
        }
        val cantidadNueva = productStore.stock - cantidadDisminuir
        val productStoreFind = entityManager.find(ProductStore::class.java, productStore.id)
        productStoreFind.stock = cantidadNueva
        productStoreRepository.update(productStoreFind)
    }

    fun increaseStockProductStore(productStore: ProductStore, cantidadAumentar: Double) {
        productStoreRepository.find(productStore.id)
            ?: throw BusinessException("This product in this store does not exists")
        val cantidadNueva = productStore.stock + cantidadAumentar
        val productStoreFind = entityManager.find(ProductStore::class.java, productStore.id)
        productStoreFind.stock = cantidadNueva
        productStoreRepository.update(productStoreFind)
    }

    fun listProductsByProductStore(id: Long) = productStoreRepository.findByProduct(id)

    fun listStoreByProductStore(id: String) = productStoreRepository.findByStore(id)

    fun listProductsByProductStoreV2(id: String) = productStoreRepository.findByProductV2(id)

    fun listStoreByProductStoreV2() = productStoreRepository.findByStoreV2()

    fun listProductCategoryV2(idCategory: Long, idStore: String) = productStoreRepository.findByCategoryV2(idCategory,idStore )

}