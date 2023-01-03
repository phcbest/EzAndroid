package org.phcbest.ezimageselector

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher

/**
 * @author phcbest
 * @date 2023/1/3 00:06
 * @github https://github.com/phcbest
 */
private const val TAG = "EzImageLauncher"

object EzImageLauncher {



    fun startSingleImageSelect(
        activityResultLauncher: ActivityResultLauncher<Intent>, context: Context
    ) {
        val intent = Intent(context, EzSingleImageSelectorActivity::class.java)
        activityResultLauncher.launch(intent)
    }
}