package com.fawwaz.app.todo.external.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fawwaz.app.todo.R
import com.fawwaz.app.todo.data.room.model.ToDoModel
import com.fawwaz.app.todo.external.constant.AppConstant
import com.fawwaz.app.todo.external.extension.getTimeFormat
import com.fawwaz.app.todo.external.helper.clone

class TaskAdapter(var eventListener: EventListener? = null) :
    ListAdapter<ToDoModel, RecyclerView.ViewHolder>(DIFF_UTIL) {

    private val EMPTY_ITEM_ID = -1
    private val DEFAULT_ITEM_ID = 1
    var isCompleted = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val li = LayoutInflater.from(parent.context)
        return if (viewType == EMPTY_ITEM_ID) {
            val view = li.inflate(R.layout.item_empty_task, parent, false)
            EmptyHolder(view, this)
        } else {
            val view = li.inflate(R.layout.item_list_task, parent, false)
            ViewHolder(view, this)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList.isEmpty()) EMPTY_ITEM_ID else DEFAULT_ITEM_ID
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.onBindData(position, getToDoItem(position))
        } else if (holder is EmptyHolder) {
            holder.onBindData(position)
        }
    }

    override fun getItemCount(): Int {
        return Math.max(currentList.size, 1)
    }

    fun getToDoItem(position: Int): ToDoModel? {
        try {
            return getItem(position)
        } catch (e : Exception) {
            return null
        }
    }

    fun submit(it: MutableList<ToDoModel>) {
        super.submitList(it)
        if (it.isEmpty()) {
            @Suppress("notifyDataSetChanged")
            notifyDataSetChanged()
        }
    }

    class ViewHolder(var itemView : View, var adapter: TaskAdapter) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val textTime = itemView.findViewById<TextView>(R.id.text_time)
        private val textTitle = itemView.findViewById<TextView>(R.id.text_title)
        private val textDesc = itemView.findViewById<TextView>(R.id.text_description)
        private val layoutButton = itemView.findViewById<View>(R.id.layout_button)
        private val layoutContent = itemView.findViewById<RelativeLayout>(R.id.layout_content)
        private val icon = itemView.findViewById<ImageView>(R.id.icon)
        private val text = itemView.findViewById<TextView>(R.id.text)

        private var model: ToDoModel? = null

        init {
            layoutButton.setOnClickListener(this)
            layoutContent.setOnClickListener(this)
        }

        fun onBindData(position: Int, model: ToDoModel?) {
            if (model != null) {
                textTime.text = getTimeFormat(model.created)
                textTitle.text = model.title
                textDesc.text = model.description
                this.model = model
                bindButtonCompleted(model)
            }
        }

        private fun bindButtonCompleted(model: ToDoModel) {
            val completed = model.status == AppConstant.Task.TASK_STATUS_COMPLETED
            val color = if (completed) AppConstant.Color.COLOR_RED else AppConstant.Color.COLOR_TEXT
            val resId = if (completed) R.drawable.bg_button_action else R.drawable.bg_button
            icon.setColorFilter(color)
            text.setTextColor(color)
            layoutButton.background = ContextCompat.getDrawable(
                itemView.context, resId
            )
        }

        override fun onClick(view: View?) {
            if (view == layoutContent) {
                adapter.eventListener?.onClick(
                    model, absoluteAdapterPosition, view
                )
            } else if (view == layoutButton) {
                val item = model
                if (item != null) {
                    val status = if (item.status == AppConstant.Task.TASK_STATUS_COMPLETED)
                        AppConstant.Task.TASK_STATUS_NOT_COMPLETED else
                        AppConstant.Task.TASK_STATUS_COMPLETED

                    val newItem = item.clone()
                    newItem.status = status
                    adapter.eventListener?.onCompletedChange(newItem, view)
                }
            }
        }
    }

    class EmptyHolder(var itemView : View, var adapter: TaskAdapter) : RecyclerView.ViewHolder(itemView) {

        private val textEmpty = itemView.findViewById<TextView>(R.id.text_empty)

        fun onBindData(position: Int) {
            if (adapter.isCompleted) {
                textEmpty.text = itemView.context.getString(R.string.message_no_item)
            } else {
                textEmpty.text = itemView.context.getString(R.string.message_progress)
            }
        }
    }

    interface EventListener {
        fun onClick(model: ToDoModel?, position: Int, view: View? = null)
        fun onCompletedChange(model: ToDoModel?, view: View? = null)
    }

    companion object {
        private val DIFF_UTIL: DiffUtil.ItemCallback<ToDoModel> = object : DiffUtil.ItemCallback<ToDoModel>() {
            override fun areItemsTheSame(oldItem: ToDoModel, newItem: ToDoModel): Boolean {
                return oldItem.date == newItem.date &&
                        oldItem.created == newItem.created
            }

            override fun areContentsTheSame(oldItem: ToDoModel, newItem: ToDoModel): Boolean {
                return oldItem.date == newItem.date &&
                        oldItem.created == newItem.created &&
                        oldItem.status == newItem.status &&
                        oldItem.title == newItem.title &&
                        oldItem.description == newItem.description
            }
        }
    }
}
