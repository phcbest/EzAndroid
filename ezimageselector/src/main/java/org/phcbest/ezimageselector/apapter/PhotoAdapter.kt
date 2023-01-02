package org.phcbest.ezimageselector.apapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import org.phcbest.ezimageselector.EzPhotoBean
import org.phcbest.ezimageselector.R

/**
 * @author phcbest
 * @date 2023/1/214:42
 * @github https://github.com/phcbest
 */
private const val TAG = "PhotoAdapter"

abstract class PhotoAdapter(
    private var mPhotoCategoryMap: MutableMap<String, List<EzPhotoBean>>
) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    private var mMargin: Int = 3

    inner class ViewHolder(parent: ViewGroup, itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var parentWidth = parent.width
        private var spanCount =
            ((parent as RecyclerView).layoutManager as StaggeredGridLayoutManager).spanCount
        val mIvImage: ImageView = itemView.findViewById<ImageView?>(R.id.iv_image).apply {

            this.layoutParams.width = (parentWidth - mMargin * spanCount * 2) / spanCount
            this.layoutParams.height = (parentWidth - mMargin * spanCount * 2) / spanCount
            (this.layoutParams as MarginLayoutParams).setMargins(mMargin, mMargin, mMargin, mMargin)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(parent, view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryName = mPhotoCategoryMap.keys.toTypedArray()[mCategoryIndex]
        val ezPhotoBeans = mPhotoCategoryMap[categoryName]

        if (position == 0) {
            holder.mIvImage.setImageDrawable(
                ResourcesCompat.getDrawable(
                    holder.itemView.resources, R.drawable.camera, holder.itemView.context.theme
                )
            )
        } else {
            ezPhotoBeans?.let {
                Glide.with(holder.itemView).load(it[position - 1].path).into(holder.mIvImage)
            }
        }
        //点击事件
        holder.mIvImage.setOnClickListener {
            onSelect(position, ezPhotoBeans)
        }
    }

    abstract fun onSelect(position: Int, ezPhotoBeans: List<EzPhotoBean>?)

    override fun getItemCount(): Int {
        val categoryName = mPhotoCategoryMap.keys.toTypedArray()[mCategoryIndex]
        return mPhotoCategoryMap[categoryName]!!.size + 1
    }

    private var mCategoryIndex = 0
    fun switchCategory(categoryIndex: Int) {
        mCategoryIndex = categoryIndex
        Log.i(TAG, "switchCategory: 切换为${categoryIndex}")
        notifyDataSetChanged()
    }
}