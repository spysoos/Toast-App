package pl.droidsonroids.toast.data.api.event

import com.google.gson.annotations.SerializedName

data class EventsResponse(
        @SerializedName("events")
        val events: List<ApiEvent>,
        @SerializedName("page_count")
        val allPagesCount: Int
)