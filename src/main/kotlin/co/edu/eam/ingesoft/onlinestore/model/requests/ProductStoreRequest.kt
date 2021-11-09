package eam.edu.ingesoft.onlinestore.model.requests

import eam.edu.ingesoft.onlinestore.model.entities.Product
import eam.edu.ingesoft.onlinestore.model.entities.Store

data class ProductStoreRequest(
    var id_Product_store: Long,
    var store: Store,
    var product: Product,
    var stock: Double,
    var price: Double
)
