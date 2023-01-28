package com.fruitable.Fruitable.app.data.network.dto.sale

import com.fruitable.Fruitable.app.data.network.dto.user.UserDTO

data class SaleResponseDTO(
    val id: Int = 0,
    val userId: UserDTO = UserDTO(),
    val contact: String = "",
    val vege: Int = 1,
    val title: String = "",
    val content: String = "",
    val price: Int = 0,
    val endDate: String? = "",
    val tags: List<String> = emptyList(),
    val filePath: List<String> = emptyList(),
    val fileURL: List<String> = emptyList()
)
