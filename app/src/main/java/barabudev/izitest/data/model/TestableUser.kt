package barabudev.izitest.data.model

import com.squareup.moshi.Json

data class TestableUser(
    @field:Json(name = "id")
    val id: String,

    @field:Json(name = "name")
    val name: String,

    @field:Json(name = "mail")
    val mail: String,

    @field:Json(name = "age")
    val age: Int
)