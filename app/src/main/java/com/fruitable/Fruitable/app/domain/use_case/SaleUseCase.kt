package com.fruitable.Fruitable.app.domain.use_case

import com.fruitable.Fruitable.app.domain.use_case.sale.*
import javax.inject.Inject

data class SaleUseCase @Inject constructor(
    val deleteSale: DeleteSale,
    val getSale: GetSale,
    val getSales: GetSales,
    val postSale: PostSale,
    val updateSale: UpdateSale
)