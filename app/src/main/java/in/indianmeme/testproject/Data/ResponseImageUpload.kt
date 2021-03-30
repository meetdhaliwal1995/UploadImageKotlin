package `in`.indianmeme.testproject.Data

import com.google.gson.annotations.SerializedName

data class ResponseImageUpload(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)
