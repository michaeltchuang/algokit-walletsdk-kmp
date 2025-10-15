package com.michaeltchuang.walletsdk.core.transaction.signmanager

import com.michaeltchuang.walletsdk.core.foundation.utils.ListQueuingHelper
import com.michaeltchuang.walletsdk.core.transaction.model.ExternalTransaction

class ExternalTransactionQueuingHelper : ListQueuingHelper<ExternalTransaction, ByteArray>()
