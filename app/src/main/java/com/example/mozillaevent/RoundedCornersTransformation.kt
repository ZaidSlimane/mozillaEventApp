package com.example.mozillaevent
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

class RoundedCornersTransformation(private val radius: Int, private val margin: Int) : BitmapTransformation() {

    companion object {
        private const val ID = "com.example.app.RoundedCornersTransformation"
        private val ID_BYTES = ID.toByteArray(Charsets.UTF_8)
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val width = toTransform.width
        val height = toTransform.height

        val bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint().apply {
            isAntiAlias = true
            shader = BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        }
        val rect = RectF(margin.toFloat(), margin.toFloat(), (width - margin).toFloat(), (height - margin).toFloat())
        canvas.drawRoundRect(rect, radius.toFloat(), radius.toFloat(), paint)

        return bitmap
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }
}
