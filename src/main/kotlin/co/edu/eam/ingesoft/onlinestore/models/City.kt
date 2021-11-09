package co.edu.eam.ingesoft.onlinestore.models

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tbl_ciudades")
data class City(

    @Id
    @Column(name = "id_ciudades")
    val id: Long,

    @Column(name = "nombre")
    var name: String,

):Serializable
