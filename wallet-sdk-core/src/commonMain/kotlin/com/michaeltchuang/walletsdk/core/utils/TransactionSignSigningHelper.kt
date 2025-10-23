package com.michaeltchuang.walletsdk.core.utils

import com.michaeltchuang.walletsdk.core.foundation.utils.ListQueuingHelper
import com.michaeltchuang.walletsdk.core.transaction.model.TransactionSignData

class TransactionSignSigningHelper : ListQueuingHelper<TransactionSignData, ByteArray>()
