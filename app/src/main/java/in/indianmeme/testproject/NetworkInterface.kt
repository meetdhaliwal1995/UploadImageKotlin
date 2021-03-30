package `in`.indianmeme.testproject

import `in`.indianmeme.testproject.Data.GetData
import com.google.gson.JsonElement
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface NetworkInterface {

    @POST("{endpoint}")
    @FormUrlEncoded
    fun makeGetDataCall(@Path(value = "endpoint" , encoded = true) endpoint: String, @FieldMap params: Map<String, String>): Call<GetData>

    @Multipart
    @POST("{endpoint}")
    fun uploadImage(
        @Path(value = "endpoint" , encoded = true) endpoint: String,
        @Part first_name: MultipartBody.Part?,
        @Part last_name: MultipartBody.Part?,
        @Part email: MultipartBody.Part?,
        @Part phone: MultipartBody.Part?,
        @Part image: MultipartBody.Part?
        ): Call<JsonElement>
}