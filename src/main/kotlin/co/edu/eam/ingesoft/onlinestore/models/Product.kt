package co.edu.eam.ingesoft.onlinestore.models

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "tbl_products")
data class Product(

    @Id
    @Column(name = "ïd_producto")
    val id: Long,

    @Column(name = "marca")
    var branch: String,

    @Column(name = "nombre")
    var name: String,

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    val caategory: Category,
):Serializable
