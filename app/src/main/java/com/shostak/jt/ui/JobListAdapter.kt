package com.shostak.jt.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shostak.jt.R
import com.shostak.jt.model.ParsedJobModel

class JobListAdapter : ListAdapter<ParsedJobModel, JobListAdapter.JobViewHolder>(
    DiffCallback()
) {

    var onItemClicked: ((position: Int) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val contactView = inflater.inflate(R.layout.job_item_row, parent, false)
        return JobViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val jobItem = getItem(position)
        holder.role.text = jobItem.roleName
        holder.locationName.text = jobItem.locationName
        ViewCompat.setTransitionName(holder.role, "role$position")
    }

    inner class JobViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val role: TextView = itemView.findViewById(R.id.role)
        val locationName: TextView = itemView.findViewById(R.id.locationName)
        private val card: View = itemView.findViewById(R.id.card)

        init {
            card.setOnClickListener(this@JobViewHolder)
            ViewCompat.setTransitionName(role, "role$adapterPosition")

        }


        override fun onClick(v: View?) {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                ViewCompat.setTransitionName(role, "role$adapterPosition")
                onItemClicked?.invoke(adapterPosition)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ParsedJobModel>() {
        override fun areItemsTheSame(
            oldItem: ParsedJobModel,
            newItem: ParsedJobModel
        ): Boolean {
            return oldItem.roleName == newItem.roleName
        }

        override fun areContentsTheSame(
            oldItem: ParsedJobModel,
            newItem: ParsedJobModel
        ): Boolean {
            return oldItem == newItem
        }
    }

}
