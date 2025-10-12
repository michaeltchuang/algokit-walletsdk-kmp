package com.michaeltchuang.walletsdk.transaction.signmanager

import com.michaeltchuang.walletsdk.deeplink.model.KeyRegTransactionDetail

object PendingTransactionRequestManger{
    var txnDetail: KeyRegTransactionDetail? = null
    fun getPendingTransactionRequest(): KeyRegTransactionDetail? {
        return txnDetail
    }
    fun storePendingTransactionRequest(detail: KeyRegTransactionDetail) {
        this.txnDetail = detail
    }
    fun clearPendingTransactionRequest() {
        this.txnDetail = null
    }
}
