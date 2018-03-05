package com.example.roy.habittrainer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_single_habit.view.*

/**
 * Created by Roy on 3/5/18.
 */

class HabitsAdapter(val habits: List<Habit>) : RecyclerView.Adapter<HabitsAdapter.HabitViewHolder>() {

    override fun onBindViewHolder(holder: HabitViewHolder?, position: Int) {
        if(holder != null){
            val habit = habits[position]
            holder.card.tv_title.text = habit.title
            holder.card.tv_description.text = habit.description
            holder.card.iv_icon.setImageResource(habit.image)
        }
    }

    override fun getItemCount() = habits.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_single_habit, parent, false)
        return HabitViewHolder(view)
    }

    class HabitViewHolder(val card: View) : RecyclerView.ViewHolder(card)
}