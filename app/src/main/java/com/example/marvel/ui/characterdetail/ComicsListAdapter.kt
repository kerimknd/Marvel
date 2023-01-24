package com.example.marvel.ui.characterdetail

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.marvel.R
import com.example.marvel.models.comics.Comic
import kotlinx.android.synthetic.main.comics_list_item.view.*


class ComicsListAdapter(private val context: Context, var comicList:List<Comic>) : RecyclerView.Adapter<ComicsListAdapter.ComicItemViewHolder>() {
   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicItemViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.comics_list_item,parent,false)
        return ComicItemViewHolder(view )

    }

    override fun onBindViewHolder(holder: ComicItemViewHolder, position: Int) {
        (holder as ComicItemViewHolder).bind(comicList[position],context)

    }
    override fun getItemCount(): Int {
        return comicList.size
    }

    class ComicItemViewHolder (view: View):RecyclerView.ViewHolder(view){

        fun bind(comic: Comic?, context:Context){
            itemView.detail_comic_name.text = comic?.title
            var dsc = comic?.description ?: "No Description Available"
            itemView.detail_comic_dsc.text= dsc
            var date = comic?.dates?.first()?.date

            itemView.detail_comic_publish.text = "PD: "+getDate(date!!)
            val thumbnail:String = comic?.thumbnail?.path + "." + comic?.thumbnail?.extension

            Glide.with(itemView.context)
                .load(thumbnail)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        // println("Load Failed----------"+e)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        //  println("Load Success----------")
                        return false
                    }
                })
                .centerCrop().into(itemView.detail_comic_thumb)

        }
        private fun getDate(date:String):String {
            var pattern = Regex("(\\d{4})(?=-)")
            val result = pattern.find(date)?.value
            return result!!
        }
    }



}