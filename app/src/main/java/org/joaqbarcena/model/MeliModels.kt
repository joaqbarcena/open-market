package org.joaqbarcena.model

import com.google.gson.annotations.SerializedName
import java.util.jar.Attributes


//https://developers.mercadolibre.com.ar/es_ar/categorias-y-publicaciones#modal1
data class Site(
    @SerializedName("default_currency_id")
    val defaultCurrencyId: String,
    val id: String,
    val name: String
) {
    fun isEmpty(): Boolean = defaultCurrencyId.isEmpty() && id.isEmpty() && name.isEmpty()
}


data class SearchResult(
    @SerializedName("site_id")
    val siteId: String,
    val query: String,
    val paging: PagingInfo,
    val results: List<Item>
)

data class PagingInfo(
    val total: Int,
    val offset: Int,
    val limit: Int,
    @SerializedName("primary_results")
    val primaryResults: Int
)

data class Item(
    val id: String,
    val site_id: String,
    val title: String,
    val price: Float,
    @SerializedName("currency_id")
    val currencyId: String,
    @SerializedName("available_quantity")
    val availableQuantity: Int,
    @SerializedName("sold_quantity")
    val soldQuantity: Int,
    @SerializedName("buying_mode")
    val buyingMode: String,
    @SerializedName("listing_type_id")
    val listingTypeId: String,
    @SerializedName("stop_time")
    val stopTime: String,
    val condition: String,
    val permalink: String,
    val thumbnail: String,
    @SerializedName("accepts_mercadopago")
    var acceptsMercadopago: Boolean?,
    @SerializedName("original_price")
    var originalPrice: String? = null,
    @SerializedName("category_id")
    val category_id: String,
    @SerializedName("official_store_id")
    val officialStoreId: Int,
    @SerializedName("catalog_product_id")
    val catalogProductId: String,
    @SerializedName("catalog_listing")
    val catalogListing: Boolean,

    //Complex objects
    val seller: Seller,
    val installments: Installments,
    @SerializedName("seller_address")
    val sellerAddress: SellerAddress,
    val shipping: Shipping,
    val attributes: List<Attribute>,
    val tags: List<String>,
    val address: Address

)

data class Seller(
    val id: Int, //: 143125485,
    @SerializedName("power_seller_status")
    val powerSellerStatus: String? = null,
    val carDealer: Boolean,
    val realEstateAgency: Boolean,
    val tags : List<String>
)

data class Address(
    @SerializedName("state_id")
    val stateId: String,
    @SerializedName("state_name")
    val stateName: String,
    @SerializedName("city_id")
    val cityId: String,
    @SerializedName("city_name")
    val cityName: String
)

data class SellerAddress(
    val id: String,
    val comment: String,
    @SerializedName("address_line")
    val addressLine: String,
    @SerializedName("zip_code")
    val zipCode: String,
    val latitude: String,
    val longitude: String,
    val country: Country,
    val state: State,
    val city: City
)

data class Installments(
    val quantity: Int,//12,
    val amount: Float,//2456.41,
    val rate: Float,//63.77,
    @SerializedName("currency_id")
    val currencyId: String//"ARS"
)

data class Shipping(
    @SerializedName("free_shipping")
    val freeShipping: Boolean,
    val mode: String,//"custom",
    val tags: List<String>,
    @SerializedName("logistic_type")
    val logisticType: String,
    @SerializedName("store_pick_up")
    val storePickUp: Boolean
)

data class Country(val id: String, val name: String)

data class State(val id: String, val name: String)

data class City(val id: String, val name: String)


data class Attribute(
    val name: String?,
    @SerializedName("value_id")
    val valueId: String?,
    @SerializedName("value_name")
    val valueName: String?,
    //@SerializedName("value_struct")
    //val valueStruct: String?,
    @SerializedName("attribute_group_id")
    val attributeGroupId: String?,
    @SerializedName("attribute_group_name")
    val attributeGroupName: String?,
    val source: Long?,
    val id: String?
)