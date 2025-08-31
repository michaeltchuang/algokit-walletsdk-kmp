package com.michaeltchuang.walletsdk.deeplink.builder

import com.michaeltchuang.walletsdk.deeplink.model.DeepLink
import com.michaeltchuang.walletsdk.deeplink.model.DeepLinkPayload

internal class KeyRegTransactionDeepLinkBuilder : DeepLinkBuilder {

    override fun doesDeeplinkMeetTheRequirements(payload: DeepLinkPayload): Boolean {
        return payload.type == "keyreg"
    }

    override fun createDeepLink(payload: DeepLinkPayload): DeepLink {
        return with(payload) {
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
                type = type.orEmpty()
            )
        }
    }
}
