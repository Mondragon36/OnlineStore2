package eam.edu.ingesoft.onlinestore.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import eam.edu.ingesoft.onlinestore.model.entities.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
//arrancar el servidor web
@AutoConfigureMockMvc
class ProductStoreControllerTest {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var mocMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun createProductStoreHappyPathTest() {
        //prerequisitos..
        val categrory = Category(1L, "Anime")
        entityManager.persist(categrory)
        val product = Product(4L, "COMANDO G", "Melosa", categrory)
        entityManager.persist(product)
        val city = City(1L, "Armenia")
        entityManager.persist(city)
        val store = Store("1", "Br Antonio nariño Cr 44 #48-47", "La tienda de los nerds", city)
        entityManager.persist(store)
        val body = """
             {
                 "id_Product_store": 10,
                 "store": {
                     "id": "1",
                     "address": "Br Antonio nariño Cr 44 #48-47",
                     "name": "La tienda de los nerds",
                     "city": {
                         "id": 1,
                         "name": "Armenia"
                     }
                 },
                 "product": {
                     "id": 4,
                     "name": "COMANDO G",
                     "branch": "Melosa",
                     "category": {
                         "id": 1,
                         "name": "Anime"
                     }
                 },
                 "stock": 30,
                 "price": 250
             }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .post("/productsStore")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(200, resp.status)
    }



    @Test
    fun createProductNotFoundTest() {
        //prerequisitos..
        val categrory = Category(1L, "Anime")
        entityManager.persist(categrory)
        val product = Product(4L, "COMANDO G", "Melosa", categrory)
        entityManager.persist(product)
        val city = City(1L, "Armenia")
        entityManager.persist(city)
        val store = Store("1", "Br Antonio nariño Cr 44 #48-47", "La tienda de los nerds", city)
        entityManager.persist(store)
        val productStore = ProductStore(1L, 250.0, 30.0, product, store)
        entityManager.persist(productStore)
        val body = """
             {
                 "id_Product_store": 1,
                 "store": {
                     "id": "1",
                     "address": "Br Antonio nariño Cr 44 #48-47",
                     "name": "La tienda de los nerds",
                     "city": {
                         "id": 1,
                         "name": "Armenia"
                     }
                 },
                 "product": {
                     "id": 4,
                     "name": "COMANDO G",
                     "branch": "Melosa",
                     "category": {
                         "id": 1,
                         "name": "Anime"
                     }
                 },
                 "stock": 30,
                 "price": 250
             }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .post("/productsStore")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This product Store whit this name already exists\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    private fun createProductsStore() {
        val categrory = Category(1L, "Anime")
        entityManager.persist(categrory)
        val product = Product(4L, "COMANDO G", "Melosa", categrory)
        entityManager.persist(product)
        val product2 = Product(5L, "COMANDO G", "Melosa", categrory)
        entityManager.persist(product2)
        val product3 = Product(6L, "COMANDO G", "Melosa", categrory)
        entityManager.persist(product3)
        val city = City(1L, "Armenia")
        entityManager.persist(city)
        val store = Store("1", "Br Antonio nariño", "La tienda de los nerds", city)
        entityManager.persist(store)
        val store2 = Store("2", "La patria", "La tienda de los nerds", city)
        entityManager.persist(store2)
        val store3 = Store("3", "El limonar", "La tienda de los nerds", city)
        entityManager.persist(store3)
        entityManager.persist(ProductStore(1L, 200.000,20.0,product,store))
        entityManager.persist(ProductStore(2L, 300.000,30.0,product2,store))
        entityManager.persist(ProductStore(3L, 400.000,40.0,product3,store3))
    }

    @Test
    fun getProductsByProductStoreHappyPath() {
        //prerrequisitos
        createProductsStore()

        val request = MockMvcRequestBuilders
            .get("/productsStore/3/products")
            .contentType(MediaType.APPLICATION_JSON)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response

        println(resp.contentAsString)

        val product = objectMapper.readValue(resp.contentAsString, Array<Product>::class.java)
        Assertions.assertEquals(200, resp.status)

        Assertions.assertEquals(1, product.size)
    }

    @Test
    fun getListStoresHappyPath() {
        //prerrequisitos
        createProductsStore()

        val request = MockMvcRequestBuilders
            .get("/productsStore/stores")
            .contentType(MediaType.APPLICATION_JSON)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response

        println(resp.contentAsString)

        val store = objectMapper.readValue(resp.contentAsString, Array<Store>::class.java)
        Assertions.assertEquals(200, resp.status)

        Assertions.assertEquals(3, store.size)
    }


    @Test
    fun getListProductByCategoryHappyPath() {
        //prerrequisitos
        createProductsStore()

        val request = MockMvcRequestBuilders
            .get("/productsStore/1/categories/1/stores")
            .contentType(MediaType.APPLICATION_JSON)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response

        println(resp.contentAsString)

        val product = objectMapper.readValue(resp.contentAsString, Array<Product>::class.java)
        Assertions.assertEquals(200, resp.status)

        Assertions.assertEquals(2, product.size)
    }



}