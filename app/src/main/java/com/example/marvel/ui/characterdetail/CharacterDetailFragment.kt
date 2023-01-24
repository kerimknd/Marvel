package com.example.marvel.ui.characterdetail



import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.activity.OnBackPressedCallback
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.marvel.R
import com.example.marvel.api.DBClient
import com.example.marvel.api.DBInterface
import com.example.marvel.models.characters.*
import com.example.marvel.models.comics.Comic
import com.example.marvel.repository.characterdetail.CharacterDetailRepository
import com.example.marvel.repository.comics.ComicsListRepository
import com.example.marvel.repository.NetworkState
import com.example.marvel.ui.MainActivity
import com.example.marvel.vm.ComicsViewModel
import com.example.marvel.vm.SingleCharacterViewModel
import kotlinx.android.synthetic.main.character_list_item.view.*

import kotlinx.android.synthetic.main.fragment_character_detail.*

import java.util.*
import kotlin.collections.ArrayList

class CharacterDetailFragment : Fragment() {
    private lateinit var characterViewModel: SingleCharacterViewModel
    private lateinit var characterRepository: CharacterDetailRepository
    private lateinit var comicsListRepository: ComicsListRepository
    private lateinit var comicsViewModel:ComicsViewModel
    lateinit var characterId:String
    lateinit var comic_list : RecyclerView
    lateinit var comicListAdapter :ComicsListAdapter
    var comicData:List<Comic> = listOf()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_character_detail, container, false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        comic_list = view.findViewById(R.id.rv_comics_list)
        characterId = arguments?.getString("id").toString()
        val apiService:DBInterface=DBClient.getClient()
        characterRepository= CharacterDetailRepository(apiService)
        characterViewModel = getCharacterViewModel(characterId)
        comicsListRepository = ComicsListRepository(apiService)
        comicsViewModel = getComicsViewModel(characterId)

        comicListAdapter = ComicsListAdapter(requireActivity(),comicData)
        val linearLayoutManager = LinearLayoutManager(requireActivity())
        comic_list.layoutManager = linearLayoutManager
        comic_list.setHasFixedSize(true)
        comic_list.adapter = comicListAdapter

        comicsViewModel.comicPagedList.observe(viewLifecycleOwner, Observer { data ->
          updateList(data)

        })
        comicsViewModel.networkState.observe(viewLifecycleOwner, Observer {
            detail_progress_bar.visibility = if(comicsViewModel.listIsEmty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_detail.visibility = if(comicsViewModel.listIsEmty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })

        characterViewModel.characterDetail.observe(viewLifecycleOwner, Observer {
            bindUI(it)
        })

        return view

    }
    private fun getDate(date:String):Int {
      var pattern = Regex("(\\d{4})(?=-)")
      val result = pattern.find(date)?.value
      return result!!.toInt()
  }

   private fun updateList(comicList:List<Comic>){
       var filterList =ArrayList<Comic>()
       comicList.sortedBy { it.dates.first().date }
       comicList.forEach { comic ->
       var date = comic.dates.first().date
           if(getDate(date)>=2005){
              filterList.add(comic)
           }
       }
       var list = filterList.take(10)
       comicListAdapter.comicList = list
       comicListAdapter.notifyDataSetChanged()
   }

    private fun bindUI(data:Character){
        detail_character_name.text= data.name
        if(data.description=="")
        detail_character_dsc.text ="No Description Available"
        else detail_character_dsc.text = data.description
        val thumbnail:String = data?.thumbnail?.path + "." + data?.thumbnail?.extension
         Glide.with(requireActivity())
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
            .centerCrop().into(detail_character_thumb)

    }


    private fun getCharacterViewModel(characterId:String): SingleCharacterViewModel {
        return ViewModelProviders.of(this,object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleCharacterViewModel(characterRepository,characterId) as T
            }
        })[SingleCharacterViewModel::class.java]
    }
    private fun getComicsViewModel(characterId: String): ComicsViewModel {
        return ViewModelProviders.of(this,object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ComicsViewModel(comicsListRepository,characterId) as T
            }
        })[ComicsViewModel::class.java]
    }

}

