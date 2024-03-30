package com.example.github.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.github.databinding.ItemListUserBinding
import com.example.github.model.User
import com.example.github.ui.detail.DetailActivity
import com.example.github.util.Constanta.EXTRA_USER

class UserAdapter: RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    val listUser = ArrayList<User>()

    fun setAllData(data: List<User>) {
        val diffResult = DiffUtil.calculateDiff(UserDiffCallback(listUser, data))
        listUser.clear()
        listUser.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class UserDiffCallback(private val oldList: List<User>, private val newList: List<User>) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class UserViewHolder(private val view: ItemListUserBinding): RecyclerView.ViewHolder(view.root) {

        fun bind(user: User) {

            view.apply {
                tvUsername.text = user.username
            }

            Glide.with(itemView.context)
                .load(user.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(view.ivImgUser)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(EXTRA_USER, user.username)
                itemView.context.startActivity(intent)
            }
        }
    }
}