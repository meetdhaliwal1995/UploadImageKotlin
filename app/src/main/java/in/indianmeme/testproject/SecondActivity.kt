package `in`.indianmeme.testproject

import `in`.indianmeme.testproject.Data.GetDataViewModel
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.second_activity.*
import java.io.File
import java.io.FileOutputStream

class SecondActivity : AppCompatActivity() {

    val imageSuffix = "_test.jpg"

    private lateinit var dir: File
    private lateinit var getDataViewModel: GetDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)

        dir = File(cacheDir, System.currentTimeMillis().toString() + imageSuffix)

        getImage()

        getDataViewModel = ViewModelProvider(this).get(GetDataViewModel::class.java)
        //getDataViewModel.uploadImage()
        getDataViewModel.readResponse.observe(this, androidx.lifecycle.Observer {
            Snackbar.make(
                submit_text.rootView,
                "Image Uploaded successfully!!!",
                Snackbar.LENGTH_SHORT
            ).show()
        })

        submit_text.setOnClickListener {
            val fName: String = first_name.text.toString()
            val lName: String = last_name.text.toString()
            val eMail: String = email.text.toString()
            val pHone: String = phone.text.toString()

            if (fName.isEmpty() || lName.isEmpty() || eMail.isEmpty() || pHone.isEmpty()) {
                Snackbar.make(
                    submit_text.rootView,
                    "All fields are important",
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (!eMail.isEmailValid()) {
                Snackbar.make(
                    submit_text.rootView,
                    "Please enter valid email address",
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (pHone.length < 10) {
                Snackbar.make(
                    submit_text.rootView,
                    "Please enter a 10 digit number",
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            getDataViewModel.uploadImage(fName, lName, eMail, pHone, dir)
        }
    }

    fun getImage() {
        var data: String? = intent.action

        Glide.with(this)
            .load(data)
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    val bitmap: Bitmap = (resource as BitmapDrawable).bitmap

                    if (dir.exists()) {
                        dir.delete()
                    }

                    val outputStream = FileOutputStream(dir)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                    outputStream.close()

                    Log.e("DONE", dir.absolutePath)

                    imz_view.setImageBitmap(bitmap)

                    return true
                }
            })
            .into(imz_view)
    }

    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this)
            .matches()
    }
}