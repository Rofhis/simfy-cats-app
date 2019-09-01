package com.rofhiwa.simfycatsapp.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.rofhiwa.simfycatsapp.R
import com.rofhiwa.simfycatsapp.data.db.entity.CatsEntity
import com.rofhiwa.simfycatsapp.databinding.CatListItemBinding
import com.rofhiwa.simfycatsapp.utils.GlideApp

class CatsRecyclerViewAdapter(private var catsEntityList: MutableList<CatsEntity>) :
    RecyclerView.Adapter<CatsRecyclerViewAdapter.CatsViewHolder>() {

  inner class CatsViewHolder(aBinding: ViewDataBinding) : RecyclerView.ViewHolder(aBinding.root) {
    val binding = aBinding as CatListItemBinding
  }

  fun refreshData(newCatsEntityList: MutableList<CatsEntity>?) {
    if (newCatsEntityList != null) {
      this.catsEntityList = newCatsEntityList
      notifyDataSetChanged()
    }
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): CatsViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val binding = CatListItemBinding.inflate(layoutInflater, parent, false)
    return CatsViewHolder(binding)
  }

  override fun onBindViewHolder(
    holder: CatsViewHolder,
    position: Int
  ) {
    holder.setIsRecyclable(false)

    val data = catsEntityList[position]

    holder.binding.imageTitle.text = data.title

    GlideApp.with(holder.binding.root)
        .load(data.imageUrl)
        .centerCrop()
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.placeholder)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .into(holder.binding.imageView)

    holder.binding.root.setOnClickListener {
      val navDirection = DashboardFragmentDirections.actionToCatsInfoFragment(
          data.title,
          data.imageUrl,
          data.description
      )
      it.findNavController()
          .navigate(navDirection)
    }
  }

  override fun getItemId(position: Int): Long {
    return catsEntityList[position].id
  }

  override fun getItemCount(): Int {
    return catsEntityList.size
  }
}