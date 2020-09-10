package org.joaqbarcena.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.country_selection_fragment.*
import kotlinx.android.synthetic.main.site_card.view.*
import org.joaqbarcena.Fragments
import org.joaqbarcena.MainActivity
import org.joaqbarcena.R
import org.joaqbarcena.model.Site
import org.joaqbarcena.toFlagEmoji
import org.joaqbarcena.viewmodel.MainViewModel

class CountrySelectionFragment : Fragment() {
    private val TAG = CountrySelectionFragment::class.java.name
    private lateinit var mainViewModel: MainViewModel

    companion object {
        fun newInstance() = CountrySelectionFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.country_selection_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mainViewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)

        mainViewModel.availableSites.observe(viewLifecycleOwner, Observer<List<Site>> { sites ->
            sites.forEach { d(TAG, "$it") }
            available_sites_view.apply {
                setHasFixedSize(true)
                layoutAnimation = AnimationUtils.loadLayoutAnimation(context,R.anim.layout_animation_from_bottom)
                layoutManager = LinearLayoutManager(context)
                adapter = SitesAdapter(sites) {
                    mainViewModel.site = it
                    (activity as MainActivity).showFragment(Fragments.SEARCH)
                }
            }
        })

    }

}

class SitesAdapter(private val dataSrc: List<Site>,
                   private val onClick: (Site) -> Unit)
    : RecyclerView.Adapter<SitesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.site_card, parent, false), onClick)

    override fun getItemCount(): Int = dataSrc.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.flagIcon.text = dataSrc[position].name.toFlagEmoji()
        holder.flagName.text = dataSrc[position].name
        holder.site = dataSrc[position]
    }

    inner class ViewHolder(itemView: View, onClick: (Site) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val flagIcon : MaterialTextView = itemView.flag_emoji as MaterialTextView
        val flagName : MaterialTextView = itemView.flag_name as MaterialTextView
        lateinit var site: Site

        init {
            itemView.setOnClickListener { onClick(site) }
        }
    }

}
