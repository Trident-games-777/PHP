package com.abdula.pranabrea.fb_naming_data.utils

import com.abdula.pranabrea.fb_naming_data.model.FbNamingModel
import com.trident.media.helper.network.models.postmodel.ConversionDataObject
import java.util.UUID

fun mapFbModelToConversionDataObject(fbNamingModel: FbNamingModel): ConversionDataObject {
    return ConversionDataObject(
        adEventId = "null",
        adGroupId = fbNamingModel.adgroup_id.toString(),
        adGroupName = fbNamingModel.adgroup_name.toString(),
        adType = fbNamingModel.ad_objective_name.toString(),
        attributed = true,
        campaignId = fbNamingModel.campaign_id.toString(),
        campaignName = fbNamingModel.campaign_group_name.toString(),
        errors = emptyList(),
        externalId = UUID.randomUUID().toString(),
        networkSubtype = "null",
        networkType = "null",
        source = "Facebook Naming",
        timestamp = "null"
    )
}