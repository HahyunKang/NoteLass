package com.app.note_lass.module.note

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.note_lass.databinding.ItemViewPagerBinding


class ViewPagerAdapter(
    private val context: NoteActivity,
    val pdf_page: ArrayList<Bitmap>,
    val drawpage: ArrayList<Bitmap>,
    val pdfUri : Uri
) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>(){

    var isFirst = true
    var pre_position = 0
    inner class ViewPagerViewHolder(private var binding : ItemViewPagerBinding) : RecyclerView.ViewHolder(binding.root){

        @SuppressLint("ClickableViewAccessibility")
        fun bind(context : Context, page : Bitmap, position: Int, draw_page: ArrayList<Bitmap>){


            binding.pdfView.setImageBitmap(page)

            //binding.drawingTest.setBitmap(page)


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding = ItemViewPagerBinding.inflate(LayoutInflater.from(context), parent, false)

        return ViewPagerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return pdf_page.size
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bind(context, pdf_page[position],position,drawpage)
    }


}
