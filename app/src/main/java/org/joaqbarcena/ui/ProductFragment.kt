package org.joaqbarcena.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import org.joaqbarcena.Fragments
import org.joaqbarcena.MainActivity

import org.joaqbarcena.R
import org.joaqbarcena.viewmodel.MainViewModel
import org.joaqbarcena.viewmodel.ProductViewModel

import com.smarteist.autoimageslider.SliderView
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlinx.android.synthetic.main.item_image.view.*
import kotlinx.android.synthetic.main.product_fragment.*
import kotlinx.android.synthetic.main.product_fragment.view.*
import org.joaqbarcena.model.Pictures
import org.joaqbarcena.model.PicturesResult

class ProductFragment : Fragment() {

    companion object {
        fun newInstance() = ProductFragment()
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var productViewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.product_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        mainViewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)

        //Item cant be null, if it is, then go back
        if(mainViewModel.item == null){
            (activity as MainActivity).showFragment(Fragments.SEARCH)
        } else {
            //Get description and Photos
            productViewModel.getDescriptionOf(mainViewModel.item!!).also {
                productViewModel.description.observe(viewLifecycleOwner, Observer {
                    description.text = it.plainText
                })
            }

            productViewModel.getPictureOf(mainViewModel.item!!).also {
                productViewModel.pictures.observe(viewLifecycleOwner, Observer {
                    image_slider.setSliderAdapter(ImageSliderAdapter(it.pictures))
                    image_slider.setIndicatorAnimation(IndicatorAnimationType.WORM)
                    image_slider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
                })
            }
        }

        title.text = mainViewModel.item?.title
        price.text = "$ ${mainViewModel.item?.price.toString()}"


    }


    class ImageSliderAdapter(
        private val dataSrc: List<Pictures> = mutableListOf()) :
        SliderViewAdapter<ImageSliderAdapter.ViewHolder>(){

        override fun getCount() = dataSrc.size

        override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent?.context).
                inflate(R.layout.item_image, parent, false))
        }

        override fun onBindViewHolder(viewHolder: ViewHolder?, position: Int) {
            viewHolder?.let {
                Glide.with(it.itemView.context)
                    .load(dataSrc[position].secureUrl)
                    .into(it.picture)
            }

        }


        inner class ViewHolder(itemView: View) : SliderViewAdapter.ViewHolder(itemView) {
            val picture : ImageView = itemView.picture as AppCompatImageView
        }

    }



}
