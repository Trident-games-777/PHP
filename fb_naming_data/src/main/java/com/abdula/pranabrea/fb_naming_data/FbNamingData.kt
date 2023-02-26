package com.abdula.pranabrea.fb_naming_data

import android.content.Context
import com.abdula.pranabrea.fb_naming_data.model.FbNamingModel
import com.abdula.pranabrea.fb_naming_data.utils.mapFbModelToConversionDataObject
import com.fmb.conversion.ConvClass
import com.trident.media.helper.network.models.postmodel.ConversionDataObject
import org.json.JSONObject

class FbNamingData(private val context: Context): IFbNamingData {

    override suspend fun getFbNamingData(): ConversionDataObject? {
        val jsonString = ConvClass.getAttData(context, DECRYPTION_KEY)
        val jsonObject = jsonString?.let { JSONObject(it) }
        var fbModel: FbNamingModel? = null
        if (jsonString != null) {
            fbModel = jsonObject?.let {
                FbNamingModel(
                    jsonObject.getString("account_id"),
                    jsonObject.getString("ad_id"),
                    jsonObject.getString("ad_objective_name"),
                    jsonObject.getString("adgroup_id"),
                    jsonObject.getString("adgroup_name"),
                    jsonObject.getString("campaign_group_id"),
                    jsonObject.getString("campaign_group_name"),
                    jsonObject.getString("campaign_id"),
                    jsonObject.getString("campaign_name")
                )
            }
            return fbModel?.let { mapFbModelToConversionDataObject(it) }
        }
        return null
    }

    companion object {
        const val DECRYPTION_KEY = "8a23bafd10abba507c04c199b088ef5c78beff46142cad23b93fa291ee0907c5"
    }
}