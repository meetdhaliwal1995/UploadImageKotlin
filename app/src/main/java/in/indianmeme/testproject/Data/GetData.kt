package `in`.indianmeme.testproject.Data

import com.google.gson.annotations.SerializedName

data class GetData(

	@field:SerializedName("images")
	val images: List<ImagesItem>,

	@field:SerializedName("status")
	val status: String
)

data class ImagesItem(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("xt_image")
	val xtImage: String
)
