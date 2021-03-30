package `in`.indianmeme.testproject.Data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonElement
import java.io.File

abstract class GetDataViewModel : ViewModel() {

    val readAllData: MutableLiveData<List<ImagesItem>>
    val readResponse: MutableLiveData<JsonElement>


    private var repo: GetDataRepo = GetDataRepo()

    init {
        readAllData = repo.getPhotosData
        readResponse = repo.phoneUploadData
    }

    fun fetchImageList(offset: Int) {
        repo.getDataCall(offset)
    }

    fun uploadImage(fName: String, lName: String, e: String, p: String, file: File) {
        repo.uploadImage(fName, lName, e, p, file)
    }
}