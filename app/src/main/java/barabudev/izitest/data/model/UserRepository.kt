package barabudev.izitest.data.model

import com.squareup.moshi.Json

data class UserRepository(

    @field:Json(name = "id")
    val id: String,

    @field:Json(name = "name")
    val name : String,

    @field:Json(name = "full_name")
    val fullName: String
)