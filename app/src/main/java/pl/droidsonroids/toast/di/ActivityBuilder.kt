package pl.droidsonroids.toast.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.droidsonroids.toast.app.events.EventDetailsActivity
import pl.droidsonroids.toast.app.events.TalkDetailsActivity
import pl.droidsonroids.toast.app.home.MainActivity
import pl.droidsonroids.toast.app.speakers.SpeakerDetailsActivity
import pl.droidsonroids.toast.app.speakers.SpeakersSearchActivity

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindSpeakersSearchActivity(): SpeakersSearchActivity

    @ContributesAndroidInjector
    abstract fun bindSpeakerDetailsActivity(): SpeakerDetailsActivity

    @ContributesAndroidInjector
    abstract fun bindEventDetailsActivity(): EventDetailsActivity

    @ContributesAndroidInjector
    abstract fun bindTalkDetailsActivity(): TalkDetailsActivity
}