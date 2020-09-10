package org.joaqbarcena.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.item_card.view.*
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.android.synthetic.main.site_card.view.*
import org.joaqbarcena.MainActivity

import org.joaqbarcena.R
import org.joaqbarcena.model.Item
import org.joaqbarcena.model.SearchResult
import org.joaqbarcena.model.Site
import org.joaqbarcena.toFlagEmoji
import org.joaqbarcena.viewmodel.MainViewModel
import org.joaqbarcena.viewmodel.SearchViewModel
import android.app.Activity
import androidx.core.content.ContextCompat.getSystemService
//import android.R
import android.view.inputmethod.InputMethodManager


class SearchFragment : Fragment() {
    private val TAG = SearchFragment::class.java.name

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainViewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        searchViewModel.searchResult.observe(viewLifecycleOwner, Observer {
                searchResult ->
                if(searchResult.paging.total > 0){
                    no_products.visibility = View.INVISIBLE
                    loading_iv.visibility = View.GONE
                    results.apply {
                        results.setHasFixedSize(true)
                        results.visibility = View.VISIBLE
                        results.layoutManager = LinearLayoutManager(context)
                        results.adapter = SearchResultAdapter(searchResult.results) {
                            d(TAG, "Item pressed ${it.title} - ${it.id}")
                        }
                    }
                } else {
                    no_products.visibility = View.VISIBLE
                    results.visibility = View.INVISIBLE
                    loading_iv.visibility = View.GONE
                }
        })

        search_text.setOnEditorActionListener { v, actionId, event ->
            var result = false
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                searchViewModel.search(search_text.text.toString()) //TODO check emptyness and sanitize
                results.visibility = View.INVISIBLE
                no_products.visibility = View.INVISIBLE
                hideKeyboard()
                Glide.with(this)
                    .asGif()
                    .load(R.drawable.loading)
                    .into(loading_iv.also { it.visibility = View.VISIBLE })
                result = true
            }
            result
        }

    }

    fun hideKeyboard() {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.rootView?.windowToken, 0)
    }

}


class SearchResultAdapter(private val dataSrc: List<Item>,
                          private val onClick: (Item) -> Unit)
    : RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_card, parent, false), onClick)

    override fun getItemCount(): Int = dataSrc.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemPrice.text = dataSrc[position].price.toString()
        holder.itemTitle.text = dataSrc[position].title
        Glide.with(holder.itemView.context)
            .load(dataSrc[position].thumbnail.replaceFirst("http","https"))
            .into(holder.itemThumbnail)
        holder.item = dataSrc[position]
    }

    inner class ViewHolder(itemView: View, onClick: (Item) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val itemThumbnail : AppCompatImageView = itemView.item_thumbnail as AppCompatImageView
        val itemTitle : MaterialTextView = itemView.item_title as MaterialTextView
        val itemPrice : MaterialTextView = itemView.item_price as MaterialTextView
        lateinit var item: Item

        init {
            itemView.setOnClickListener { onClick(item) }
        }
    }

}