package com.fruitable.Fruitable.app.presentation.state

import com.fruitable.Fruitable.app.data.network.dto.sale.SaleResponseDTO

data class SaleDetailState (
    val isLoading: Boolean = false,
    val saleDetail: SaleResponseDTO = SaleResponseDTO(),
    val error: String = ""
)