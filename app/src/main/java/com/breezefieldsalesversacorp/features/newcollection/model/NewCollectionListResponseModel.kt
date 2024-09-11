package com.breezefieldsalesversacorp.features.newcollection.model

import com.breezefieldsalesversacorp.app.domain.CollectionDetailsEntity
import com.breezefieldsalesversacorp.base.BaseResponse
import com.breezefieldsalesversacorp.features.shopdetail.presentation.model.collectionlist.CollectionListDataModel

/**
 * Created by Saikat on 15-02-2019.
 */
class NewCollectionListResponseModel : BaseResponse() {
    //var collection_list: ArrayList<CollectionListDataModel>? = null
    var collection_list: ArrayList<CollectionDetailsEntity>? = null
}