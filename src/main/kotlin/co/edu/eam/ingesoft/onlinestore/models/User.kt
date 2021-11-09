package co.edu.eam.ingesoft.onlinestore.models

import java.io.Serializable
import javax.persistence.*


@Entity
@Table(name = "tbl_usuarios")
data class User(

    @Id
    @Column(name = "id_usuario")
    val id: String,

    @Column(name = "direccion")
    var address: String,

    @Column(name = "apellido")
    var lastName: String,

    @Column(name = "nombre")
    var name: String,

    @ManyToOne
    @JoinColumn(name = "id_ciudad")
    val city: City,
):Serializable
