package com.mocktest.relifemedicare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mocktest.relifemedicare.R
import com.mocktest.relifemedicare.interfaces.Click
import com.mocktest.relifemedicare.interfaces.Download
import com.mocktest.relifemedicare.models.MedicareModelItem
import com.mocktest.relifemedicare.view.activities.MainActivity
import kotlinx.android.synthetic.main.medicare_list_row.view.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.collections.ArrayList

class MedicareAdapter(private val activity: MainActivity):
    RecyclerView.Adapter<MedicareAdapter.MyViewHolder>() {

    var items = ArrayList<MedicareModelItem>()
    private val download: Download = activity as MainActivity
    private val click: Click = activity as MainActivity

    fun setMedicareList(data: ArrayList<MedicareModelItem>)
    {
        this.items = data;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicareAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.medicare_list_row, parent, false)
        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MedicareAdapter.MyViewHolder, position: Int) {
        holder.bind(items[position])
        holder.ivDownload.setOnClickListener(View.OnClickListener {
            download.downloadImg(items[position])
        })

        holder.view.setOnClickListener(View.OnClickListener {
            click.openDetailPage(items[position])
        })
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MyViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        val tvLikeTitle = view.tvLikeTitle
        val tvLikePublisher = view.tvLikePublisher
        val imgLike = view.imgLike
        val ivDownload = view.ivDownload

        fun bind(data: MedicareModelItem) {
            tvLikeTitle.text = data.title

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                val date: String = LocalDateTime
                    .parse( "2018-04-13T20:00:00.0400" )
                    .toLocalDate()
                    .format(DateTimeFormatter.ofLocalizedDate( FormatStyle.LONG ).withLocale( Locale.US ))
                tvLikePublisher.text = date
            }
            else
            {
                val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val formatter = SimpleDateFormat("dd.MM.yyyy")
                val formattedDate = formatter.format(parser.parse(data.publishedAt))
                tvLikePublisher.text = formattedDate
            }

            Glide.with(imgLike)
                .load(data.imageUrl)
                .into(imgLike)
        }
    }
}