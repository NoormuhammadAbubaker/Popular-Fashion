package com.example.popularfashion.ui.fragments.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.popularfashion.databinding.FashionItemBinding
import com.example.popularfashion.models.ProductResponse
import com.example.popularfashion.ui.fragments.dashboard.interfaces.FashionAction
import com.example.popularfashion.utils.Extension.invisible
import com.example.popularfashion.utils.Extension.visible
import com.example.popularfashion.utils.Utils.calculateOffPercentage
import com.example.popularfashion.utils.Utils.loadImage

class FashionAdapter(
    private val callBack:FashionAction
) : ListAdapter<ProductResponse, RecyclerView.ViewHolder>(FashionDiffCallBack()) {

    private var buttonsClickable = true

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): RecyclerView.ViewHolder {
        val binding =
            FashionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
       return FashionViewHolder(binding)


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = getItem(position)
        when (holder) {
            is FashionViewHolder -> holder.bind(model,position)
        }
    }


    inner class FashionViewHolder(private val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductResponse,position: Int) {
            if (binding is FashionItemBinding) {


                binding.title.text=item.title
                binding.price.text="RS.${item.price}"

                binding.offPercentage.text="${"%.2f".format(calculateOffPercentage(item.oldPrice.toDouble(), item.price))}% off"

                if (item.isNew) binding.newItem.visible() else binding.newItem.invisible()

                loadImage(item.image,binding.itemIcon)



                itemView.setOnClickListener {
                    if (buttonsClickable) {
                        buttonsClickable = false
                        singleClickHandler(it)
                        callBack.callToDetail(position)
                    }
                }


        }
    }

    private fun singleClickHandler(view: View) {
        view.postDelayed({
            buttonsClickable = true
        }, 100)
    }

}

class FashionDiffCallBack : DiffUtil.ItemCallback<ProductResponse>() {
    override fun areItemsTheSame(oldItem: ProductResponse, newItem: ProductResponse): Boolean {
        return oldItem._id == newItem._id
    }

    override fun areContentsTheSame(oldItem: ProductResponse, newItem: ProductResponse): Boolean {
        return oldItem == newItem
    }
}
}