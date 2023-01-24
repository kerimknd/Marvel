package com.example.marvel.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel.*
import com.example.marvel.api.DBClient
import com.example.marvel.api.DBInterface
import com.example.marvel.repository.characters.CharacterListRepository
import com.example.marvel.repository.NetworkState
import com.example.marvel.ui.characterdetail.CharacterDetailFragment
import com.example.marvel.vm.CharactersViewModel
import kotlinx.android.synthetic.main.fragment_character_list.*


class CharactersListFragment : Fragment(),ClickListener {
    private lateinit var viewModel: CharactersViewModel
    lateinit var characterListRepository: CharacterListRepository
    lateinit var rv_characterlist :RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_character_list, container, false)
        rv_characterlist= view.findViewById(R.id.rv_character_list)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        val apiService : DBInterface = DBClient.getClient()
        characterListRepository = CharacterListRepository(apiService)
        viewModel = getViewModel()
        val characterListAdapter = CharactersListAdapter(requireActivity(),this)
        val gridlayoutManager =  GridLayoutManager(requireActivity(),3)

        rv_characterlist.layoutManager = gridlayoutManager
        rv_characterlist.setHasFixedSize(true)
        rv_characterlist.adapter= characterListAdapter

        rv_characterlist.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if(viewModel.networkState.value == NetworkState.LOADING)
                        ch_progress_bar_list.visibility= View.VISIBLE
                    else  ch_progress_bar_list.visibility= View.GONE
                   }else{
                    ch_progress_bar_list.visibility= View.GONE
                }
            }
        })
        viewModel.characterPagedList.observe(getViewLifecycleOwner(),Observer{
            characterListAdapter.submitList(it)
        })
        viewModel.networkState.observe(getViewLifecycleOwner(), Observer {
            ch_progress_bar.visibility = if(viewModel.listIsEmty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            ch_txt_error.visibility = if(viewModel.listIsEmty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE
            if(viewModel.networkState.value== NetworkState.LOADED)  ch_progress_bar_list.visibility= View.GONE

        })
        return view

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            backPressedCallback.handleOnBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val toolbar = (activity as AppCompatActivity).supportActionBar
            toolbar?.setDisplayHomeAsUpEnabled(false)
            toolbar?.setDisplayShowHomeEnabled(false)
            requireActivity().onBackPressedDispatcher.onBackPressed()

        }
    }
   override fun onClick(characterId:Int){

       val fragment:CharacterDetailFragment= CharacterDetailFragment()
       val bundle:Bundle = Bundle()
       bundle.putString("id",characterId.toString())
       fragment.arguments = bundle
       val transaction = requireActivity().supportFragmentManager.beginTransaction()
       transaction.replace(R.id.activity_frame_layout,fragment)
       transaction.addToBackStack(null)
       transaction.commit()

    }

    private fun getViewModel(): CharactersViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return CharactersViewModel(characterListRepository) as T
            }
        })[CharactersViewModel::class.java]
    }
}
interface ClickListener {
    fun onClick(characterId: Int)

}
