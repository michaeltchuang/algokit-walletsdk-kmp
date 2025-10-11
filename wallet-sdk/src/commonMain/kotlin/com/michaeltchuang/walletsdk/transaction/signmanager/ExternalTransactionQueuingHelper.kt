package com.michaeltchuang.walletsdk.transaction.signmanager

import com.michaeltchuang.walletsdk.foundation.utils.ListQueuingHelper
import com.michaeltchuang.walletsdk.transaction.model.ExternalTransaction

class ExternalTransactionQueuingHelper : ListQueuingHelper<ExternalTransaction, ByteArray>()
