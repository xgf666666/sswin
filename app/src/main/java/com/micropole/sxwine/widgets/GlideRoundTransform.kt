package com.micropole.sxwine.widgets

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import java.security.MessageDigest


class GlideRoundTransform @JvmOverloads constructor(dp: Int = 4) : CenterCrop() {
    override fun updateDiskCacheKey(messageDigest: MessageDigest) {

    }

    val id: String
        get() = javaClass.name + Math.round(radius)

    init {
        radius = Resources.getSystem().displayMetrics.density * dp
    }

    protected override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap? {
        return roundCrop(pool, super.transform(pool, toTransform, outWidth, outHeight))
    }

    companion object {

        private var radius = 0f

        private fun roundCrop(pool: BitmapPool, source: Bitmap?): Bitmap? {
            if (source == null) return null

            var result: Bitmap? = pool.get(source.width, source.height, Bitmap.Config.ARGB_8888)
            if (result == null) {
                result = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
            }

            val canvas = Canvas(result)
            val paint = Paint()
            paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            paint.isAntiAlias = true
            val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
            canvas.drawRoundRect(rectF, radius, radius, paint)
            return result
        }
    }
}

