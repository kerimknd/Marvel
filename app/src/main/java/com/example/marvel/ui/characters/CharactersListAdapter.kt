package com.example.marvel.ui.characters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.marvel.R
import com.example.marvel.models.characters.Character
import kotlinx.android.synthetic.main.character_list_item.view.*


class CharactersListAdapter (private val context: Context, private val listener:ClickListener) : PagedListAdapter<Character,RecyclerView.ViewHolder>(
    CharacterDiffCallback()
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
           val layoutInflater:LayoutInflater = LayoutInflater.from(parent.context)
           val view:View = layoutInflater.inflate(R.layout.character_list_item,parent,false)
           return CharacterItemViewHolder(view,listener )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

           (holder as CharacterItemViewHolder).bind(getItem(position),context)

    }



    class CharacterDiffCallback : DiffUtil.ItemCallback<Character>(){
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
          return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem==newItem
        }

    }
    class CharacterItemViewHolder (view: View, private val listener: ClickListener):RecyclerView.ViewHolder(view){
        fun bind(character: Character?, context:Context){

            itemView.character_name.text = character?.name
            itemView.character_modified.text= character?.modified.toString()

            val thumbnail:String = character?.thumbnail?.path + "." + character?.thumbnail?.extension

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
                .centerCrop().into(itemView.character_thumb)
                itemView.setOnClickListener {
                   listener.onClick(character!!.id)
            }
        }
    }

}