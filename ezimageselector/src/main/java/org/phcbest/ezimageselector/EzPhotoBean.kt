package org.phcbest.ezimageselector

/**
 * @author phcbest
 * @date 2023/1/122:36
 * @github https://github.com/phcbest
 */
data class EzPhotoBean(
    val path: String,
    var folderName: String = "",
    val displayName: String,
    val dateAdded: String,
    val id: String,
    val mimeType: String,
    val size: String
)