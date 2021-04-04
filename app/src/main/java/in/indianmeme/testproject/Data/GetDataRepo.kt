package `in`.indianmeme.testproject.Data

import `in`.indianmeme.testproject.MyApp
import `in`.indianmeme.testproject.NetworkInterface
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonElement
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class GetDataRepo {

    val getPhotosData: MutableLiveData<List<ImagesItem>> = MutableLiveData()
    val phoneUploadData: MutableLiveData<JsonElement> = MutableLiveData()

    fun getDataCall(offset: Int): MutableLiveData<List<ImagesItem>> {
        val list = MutableLiveData<List<ImagesItem>>()

        val map = HashMap<String, String>()

        map["user_id"] = "108"
        map["offset"] = offset.toString()
        map["type"] = "popular"

        val networkInterface = MyApp.getRetrofit().create(NetworkInterface::class.java)
        networkInterface.makeGetDataCall("/xttest/getdata.php", map).enqueue(object :
            Callback<GetData> {
            override fun onFailure(call: Call<GetData>, t: Throwable) {
                Log.e("hello", t.localizedMessage)
            }

            override fun onResponse(call: Call<GetData>, response: Response<GetData>) {
                Log.e("hello", "working")
                getPhotosData.value = response.body()?.images
            }
        })

        return list
    }

    fun uploadImage(fName: String, lName: String, e: String, p: String, file: File): MutableLiveData<JsonElement> {
        val data = MutableLiveData<JsonElement>()

        val requestBodyImage: RequestBody = RequestBody.create("*/*".toMediaTypeOrNull(), file)

        val image = MultipartBody.Part.createFormData("user_image", file.name, requestBodyImage)
        val firstName = MultipartBody.Part.createFormData("first_name", fName)
        val lastName = MultipartBody.Part.createFormData("last_name", lName)
        val email = MultipartBody.Part.createFormData("email", e)
        val phone = MultipartBody.Part.createFormData("phone", p)

        val networkInterface = MyApp.getRetrofit().create(NetworkInterface::class.java)
        networkInterface.uploadImage("/xttest/savedata.php", firstName, lastName, email, phone, image)
            .enqueue(object : Callback<JsonElement> {
                override fun onFailure(call: Call<JsonElement>, t: Throwable) {

                }

                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                    phoneUploadData.value = response.body()
                }
            })

        return data
    }
}