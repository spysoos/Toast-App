package pl.droidsonroids.toast.data.mapper

import pl.droidsonroids.toast.BuildConfig
import pl.droidsonroids.toast.data.api.ApiImage
import pl.droidsonroids.toast.data.dto.ImageDto

fun ApiImage.toDto(): ImageDto {
    return ImageDto(
            originalSizeUrl.addBaseUrlIfNeeded(),
            thumbSizeUrl.addBaseUrlIfNeeded()
    )
}

// There's inconsistency on backend - sometimes we have full url and sometimes we have just endpoint
private fun String.addBaseUrlIfNeeded(): String {
    return if (startsWith("http", true)) {
        this
    } else BuildConfig.BASE_IMAGES_URL + this
}