package com.michaeltchuang.walletsdk.core.transaction.signmanager

import com.michaeltchuang.walletsdk.core.deeplink.model.KeyRegTransactionDetail

object PendingTransactionRequestManger {
    var txnDetail: KeyRegTransactionDetail? = null

    fun getPendingTransactionRequest(): KeyRegTransactionDetail? = txnDetail

    fun storePendingTransactionRequest(detail: KeyRegTransactionDetail) {
        this.txnDetail = detail
    }

    fun clearPendingTransactionRequest() {
        this.txnDetail = null
    }
}
