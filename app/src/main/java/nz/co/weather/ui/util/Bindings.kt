package nz.co.weather.ui.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import nz.co.weather.dto.Location


interface BindableAdapter<T> {
    fun setData(data: T)
}

@BindingAdapter("data")
fun setRecyclerViewProperties(recyclerView: RecyclerView, items: ObservableList<Location>) {
    if (recyclerView.adapter is BindableAdapter<*>) {
        (recyclerView.adapter as LocationAdapter).setData(items)
    }
}

@BindingAdapter("android:visibility")
fun setVisibility(view: View, value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.GONE
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String) {
    Glide.with(view.context)
        .load(imageUrl).apply(RequestOptions())
        .into(view)
}


