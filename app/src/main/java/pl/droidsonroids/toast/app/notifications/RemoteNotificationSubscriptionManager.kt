package pl.droidsonroids.toast.app.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import com.google.firebase.messaging.FirebaseMessaging
import pl.droidsonroids.toast.R
import javax.inject.Inject
import javax.inject.Singleton

private const val NEW_EVENTS_TOPIC = "new_event"
private const val NEW_TALKS_TOPIC = "new_talk"
private const val NEW_PHOTOS_TOPIC = "new_photos"

@Singleton
class RemoteNotificationSubscriptionManager @Inject constructor(
        private val context: Context,
        private val sharedPrefs: SharedPreferences
) : SharedPreferences.OnSharedPreferenceChangeListener {

    private val messaging by lazy {
        FirebaseMessaging.getInstance()
    }

    fun init() {
        createChannel()
        refreshSubscriptions()
    }


    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            with(context) {
                val channelId = getString(R.string.default_notification_channel_id)
                val channelName = getString(R.string.default_notification_channel_name)
                val notificationManager = getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(NotificationChannel(channelId,
                        channelName, NotificationManager.IMPORTANCE_DEFAULT))
            }
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String) {
        refreshSubscription(key)
    }

    private fun refreshSubscriptions() {
        with(context) {
            refreshSubscription(getString(R.string.pref_key_new_events_notifications))
            refreshSubscription(getString(R.string.pref_key_new_photos_notifications))
            refreshSubscription(getString(R.string.pref_key_new_talks_notifications))
        }
    }

    private fun isNotificationForTopicEnabled(key: String) = sharedPrefs.getBoolean(key, true)

    private fun refreshSubscription(key: String) {
        key.asTopic?.let {
            if (isNotificationForTopicEnabled(key)) {
                messaging.subscribeToTopic(it)
            } else {
                messaging.unsubscribeFromTopic(it)
            }
        }
    }

    private val String.asTopic: String?
        get() = when (this) {
            context.getString(R.string.pref_key_new_events_notifications) -> NEW_EVENTS_TOPIC
            context.getString(R.string.pref_key_new_photos_notifications) -> NEW_PHOTOS_TOPIC
            context.getString(R.string.pref_key_new_talks_notifications) -> NEW_TALKS_TOPIC
            else -> null
        }
}
