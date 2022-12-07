package com.example.android.marsphotos.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsphotos.databinding.GridViewItemBinding
import com.example.android.marsphotos.domain.DomainMars

class PhotoGridAdapter(private val onClickListener: OnClickListener) : ListAdapter<DomainMars,
        PhotoGridAdapter.MarsPhotoViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsPhotoViewHolder {
        return MarsPhotoViewHolder(GridViewItemBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MarsPhotoViewHolder, position: Int) {
        val marsProperty = getItem(position)
        holder.bind(marsProperty, onClickListener)
    }

    class MarsPhotoViewHolder(private var binding:
                              GridViewItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(marsProperty: DomainMars, onClickListener: OnClickListener) {
            binding.photo = marsProperty
            binding.root.setOnClickListener { onClickListener.onClick(marsProperty) }
            binding.executePendingBindings()
        }

    }


    companion object DiffCallback : DiffUtil.ItemCallback<DomainMars>() {
        override fun areItemsTheSame(oldItem: DomainMars, newItem: DomainMars): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DomainMars, newItem: DomainMars): Boolean {
        return oldItem == newItem
        }
    }
    class OnClickListener(val clickListener: (marsProperty: DomainMars) -> Unit) {
        fun onClick(marsProperty:DomainMars) = clickListener(marsProperty)
    }
}
