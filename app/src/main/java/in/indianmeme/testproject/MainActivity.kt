package `in`.indianmeme.testproject

import `in`.indianmeme.testproject.Data.GetDataViewModel
import `in`.indianmeme.testproject.Data.ImagesItem
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterImage.ItemCallBack {

    private lateinit var getDataViewModel: GetDataViewModel
    var offset = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = AdapterImage(this, mutableListOf(), this)

        val linearLayoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = linearLayoutManager
        recycler_view.adapter = adapter

        val endless = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                offset++
                Snackbar.make(recycler_view, "Loading more...", Snackbar.LENGTH_SHORT).show()
                getDataViewModel.fetchImageList(offset)
            }
        }

        recycler_view.addOnScrollListener(endless)

        getDataViewModel = ViewModelProvider(this).get(GetDataViewModel::class.java)
        getDataViewModel.fetchImageList(offset)
        getDataViewModel.readAllData.observe(this, androidx.lifecycle.Observer {
            it?.let {
                adapter.updateData(it)
            }
        })
    }

    override fun onBackActivity(item: ImagesItem) {
        val intent = Intent(this, SecondActivity::class.java)
        intent.action = item.xtImage
        startActivity(intent)
    }
}