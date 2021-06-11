package com.fawwaz.app.todo.external.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.fawwaz.app.todo.MainApp
import com.fawwaz.app.todo.R
import com.fawwaz.app.todo.data.room.repo.TodoRepository
import com.fawwaz.app.todo.external.constant.AppConstant
import com.fawwaz.app.todo.external.extension.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class DateAdapter(var eventListener: EventListener? = null) : RecyclerView.Adapter<DateAdapter.ViewHolder>() {

    private val calendar = getCalendar()
    private val mapperCount : HashMap<String, Int> = hashMapOf()
    private val currentTimeInMillis = System.currentTimeMillis()
    var currentTimePosition = currentTimeInMillis
    var lastSelectedPosition : Int = centerPosition()
    var firstItemPosition: Int = lastSelectedPosition

    init {
        eventListener?.onClick(centerPosition(), 0, currentTimePosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_date, parent, false)
        return ViewHolder(view, this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindData(position, centerPosition())
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    fun centerPosition(): Int {
        return Int.MAX_VALUE/2
    }

    fun getCount(date: String): Int {
        val count = mapperCount[date]
        if (count != null && count > 0) {
            return count
        }
        return 0
    }

    fun centerScrollPosition(): Int {
        val centerPosition = centerPosition()
        return if (firstItemPosition > centerPosition) centerPosition - getPositionOffset() else
            centerPosition + getPositionOffset()
    }

    private fun getPositionOffset(): Int {
        return (screenWidth / (MainApp.getDimension(R.dimen.min_date_card_width)
                + convertDpToPixel(14f))).toInt() / 2
    }

    fun setCalendarPosition(position: Int) {
        calendar.timeInMillis = currentTimeInMillis
        if (position != 0) {
            calendar.add(Calendar.DATE, position)
        }
    }

    fun getCalendar(position: Int) : Calendar {
        this.setCalendarPosition(position)
        return calendar
    }

    fun getValidPosition(position: Int): Int {
        return position - centerPosition()
    }

    fun getCurrentDateId(): String {
        val validPosition = getValidPosition(lastSelectedPosition)
        val calendar = getCalendar(validPosition)
        return getDateString(calendar.timeInMillis)
    }

    fun putMapper(dateId: String, count: Int) {
        mapperCount.put(dateId, count)
    }

    fun removeMapper(dateId: String) {
        mapperCount.remove(dateId)
    }

    fun resetSelectedItem() {
        setSelectedItem(centerPosition(), 0, System.currentTimeMillis())
    }

    private fun getWeekChar(calendar: Calendar): String? {
        val weekStr = calendar.getDisplayName(
            Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()
        )
        if (weekStr != null) {
            return weekStr[0].uppercase()
        }
        return null
    }

    private fun setSelectedItem(position: Int, validPosition: Int, timeInMillis: Long = 0) {
        if (lastSelectedPosition != position) {
            eventListener?.onClick(
                position, validPosition,
                if (timeInMillis > 0) timeInMillis else getCalendar(validPosition).timeInMillis
            )
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(position)
            lastSelectedPosition = position
        }
    }

    class ViewHolder(var itemView : View, var adapter: DateAdapter) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val dot = itemView.findViewById<View>(R.id.dot)
        private val textDays = itemView.findViewById<TextView>(R.id.text_days)
        private val textDate = itemView.findViewById<TextView>(R.id.text_date)
        private var dateId : String? = null
        private var timeInMillis = 0L
        private var validPosition = 0

        init {
            itemView.setOnClickListener(this)
        }

        fun onBindData(position: Int, centerPosition: Int) {
            if (position == adapter.lastSelectedPosition) {
                setSelected(true)
            } else {
                setSelected(false)
            }
            validPosition = position - centerPosition
            val calendar = adapter.getCalendar(validPosition)
            val dayLongName = adapter.getWeekChar(calendar)
            timeInMillis = calendar.timeInMillis
            dateId = getDateString(timeInMillis)

            itemView.tag = timeInMillis
            textDays.text = dayLongName
            textDate.text = "${calendar.get(Calendar.DAY_OF_MONTH)}"

            addMapper(itemView.context)
        }

        private fun addMapper(context: Context) {
            val count = adapter.mapperCount[dateId]
            if (count != null && count > 0) {
                dot.alpha = 1f
            } else {
                dot.alpha = 0f
                GlobalScope.launch {
                    val userId = getUserId(context)
                    val countItem = TodoRepository(MainApp.instance).getItemsCount(
                        userId!!, dateId!!
                    )
                    if (countItem > 0) {
                        adapter.mapperCount[dateId!!] = countItem
                        dot.post {
                            dot.alpha = 1f
                        }
                    }
                }
            }
        }

        fun setSelected(selected: Boolean) {
            DrawableCompat.setTint(
                DrawableCompat.wrap(itemView.background),
                if (selected) AppConstant.Color.COLOR_BLUE else AppConstant.Color.COLOR_BG_CARD
            )
            val color = if (selected) AppConstant.Color.COLOR_WHITE else
                AppConstant.Color.COLOR_TEXT
            textDays.setTextColor(color)
            textDate.setTextColor(color)
            if (selected) {
                adapter.lastSelectedPosition = absoluteAdapterPosition
                adapter.currentTimePosition = timeInMillis
            }
        }

        override fun onClick(view: View?) {
            if (view == itemView) {
                adapter.setSelectedItem(absoluteAdapterPosition, validPosition, timeInMillis)
            }
        }
    }

    interface EventListener {
        fun onClick(position: Int, validPosition: Int, timestamp: Long, view: View? = null)
    }
}