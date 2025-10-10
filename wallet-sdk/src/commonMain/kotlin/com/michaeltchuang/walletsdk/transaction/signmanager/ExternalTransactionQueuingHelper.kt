package com.michaeltchuang.walletsdk.transaction.signmanager

import com.michaeltchuang.walletsdk.transaction.model.ExternalTransaction
import com.michaeltchuang.walletsdk.foundation.utils.ListQueuingHelper


class ExternalTransactionQueuingHelper  : ListQueuingHelper<ExternalTransaction, ByteArray>()
