package pl.droidsonroids.toast.viewmodels.event

import pl.droidsonroids.toast.viewmodels.speaker.SpeakerItemViewModel

class EventSpeakerItemViewModel(
        val id: Long,
        val title: String,
        val description: String,
        val speakerItemViewModel: SpeakerItemViewModel,
        private val readMoreAction: (EventSpeakerItemViewModel) -> Unit
) {
    fun onReadMore() {
        readMoreAction(this)
    }
}