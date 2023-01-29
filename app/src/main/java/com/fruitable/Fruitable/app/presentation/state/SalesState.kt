package com.fruitable.Fruitable.app.presentation.state

import com.fruitable.Fruitable.app.data.network.dto.sale.SaleResponseDTO

data class SalesState(
    val isLoading: Boolean = false,
    var salesDTO: List<SaleResponseDTO> = emptyList(),
    val error: String = ""
)
