package quickclickgames.magicaT.utils

import android.content.Context
import com.appsflyer.AppsFlyerLib
import okhttp3.HttpUrl
import java.util.*

class HttpUrlBuilder {

    fun build(
        deep: String,
        apps: MutableMap<String, Any>? = null,
        gadid: String,
        context: Context
    ): String {
        return HttpUrl.Builder()
            .scheme("https")
            .host(StringParams.ROOT)
            .addPathSegment(StringParams.PACKAGE)
            .addQueryParameter(StringParams.SECURE_GET_PARAM, StringParams.SECURE_KEY)
            .addQueryParameter(StringParams.TMZ_KEY, TimeZone.getDefault().id)
            .addQueryParameter(StringParams.GADID_KEY, gadid)
            .addQueryParameter(StringParams.DEEP_KEY, deep)
            .addQueryParameter(
                StringParams.SOURCE_KEY,
                when (deep) {
                    "null" -> apps?.get("media_source").toString()
                    else -> "deeplink"
                }
            )
            .addQueryParameter(
                StringParams.AF_KEY,
                when (deep) {
                    "null" -> AppsFlyerLib.getInstance().getAppsFlyerUID(context)
                    else -> "null"
                }
            )
            .addQueryParameter(StringParams.ADSET_ID_KEY, apps?.get("adset_id").toString())
            .addQueryParameter(StringParams.CAMPAIGN_ID_KEY, apps?.get("campaign_id").toString())
            .addQueryParameter(StringParams.APP_CAMPAIGN_KEY, apps?.get("campaign").toString())
            .addQueryParameter(StringParams.ADSET_KEY, apps?.get("adset").toString())
            .addQueryParameter(StringParams.ADGROUP_KEY, apps?.get("adgroup").toString())
            .addQueryParameter(StringParams.ORIG_COST_KEY, apps?.get("orig_cost").toString())
            .addQueryParameter(StringParams.AF_SITEID_KEY, apps?.get("af_siteid").toString())
            .build()
            .toString()
    }
}