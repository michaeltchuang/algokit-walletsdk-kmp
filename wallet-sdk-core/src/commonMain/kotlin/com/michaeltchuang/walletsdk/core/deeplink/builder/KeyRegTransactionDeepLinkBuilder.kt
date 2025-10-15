package com.michaeltchuang.walletsdk.core.deeplink.builder

import com.michaeltchuang.walletsdk.core.deeplink.model.DeepLink
import com.michaeltchuang.walletsdk.core.deeplink.model.DeepLinkPayload

internal class KeyRegTransactionDeepLinkBuilder : DeepLinkBuilder {
    override fun doesDeeplinkMeetTheRequirements(payload: DeepLinkPayload): Boolean = payload.type == "keyreg"

    override fun createDeepLink(payload: DeepLinkPayload): DeepLink =
        with(payload) {
            DeepLink.KeyReg(
                senderAddress = accountAddress ?: host.orEmpty(),
                fee = fee,
                note = note,
                xnote = xnote,
                voteKey = votekey,
                selkey = selkey,
                sprfkey = sprfkey,
                votefst = votefst,
                votelst = votelst,
                votekd = votekd,
                type = type.orEmpty(),
            )
        }
}
