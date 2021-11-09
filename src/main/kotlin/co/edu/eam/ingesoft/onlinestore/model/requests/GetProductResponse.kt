package eam.edu.ingesoft.onlinestore.model.requests

data class GetProductResponse(
    var id: Long,
    var name: String,
    var branch: String,
)
