package nz.co.weather.ui.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nz.co.weather.R
import nz.co.weather.dto.Location

/**
 * View holder to show location search results.
 */
class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var titleTextView: TextView = itemView.findViewById(R.id.locationListItem_name)

    fun bind(location: Location, locationSelectedListener: LocationAdapter.LocationSelectedListener?) {
        titleTextView.text = "%s, %s".format(location.name, location.sys?.country)
        if (locationSelectedListener != null) itemView.setOnClickListener {
            locationSelectedListener.onLocationSelected(location)
        }

    }

    fun showEmpty() {
        titleTextView.text = itemView.context.getString(R.string.empty_message)
    }
}

/**
 * Adapter to show location search results.
 */
class LocationAdapter : RecyclerView.Adapter<LocationViewHolder>(), BindableAdapter<List<Location>> {
    private var locations = mutableListOf<Location>()

    private var locationSelectedListener: LocationSelectedListener? = null

    fun setLocationSelectedListener(listener: LocationSelectedListener) {
        this.locationSelectedListener = listener
    }

    override fun setData(data: List<Location>) {
        this.locations.clear()
        this.locations.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.location_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return if (locations.isEmpty()) 0
        else 1
    }

    override fun getItemCount(): Int {
        return if (locations.isEmpty()) 1 else locations.size
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        if (position == 0 && locations.isEmpty()) holder.showEmpty()
        else holder.bind(locations[position], locationSelectedListener)
    }

    interface LocationSelectedListener {
        fun onLocationSelected(location: Location)
    }
}