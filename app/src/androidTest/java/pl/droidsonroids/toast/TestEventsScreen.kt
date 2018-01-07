package pl.droidsonroids.toast

import android.support.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import pl.droidsonroids.toast.app.home.MainActivity


class TestEventsScreen {
    @Suppress("unused")
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, true)

    @Test
    fun isToolbarDisplayed() {
        val toolbarTitle = getString(R.string.events_title)
        EventsRobot().checkIfToolbarWithTitleIsDisplayed(toolbarTitle, R.id.toolbar)
    }

    @Test
    fun isEveryElementDisplayedProperlyOnUpcomingEventCard() {
        EventsRobot().checkIfElementWithIdIsDisplayed(R.id.topBar)
                .checkIfElementWithIdIsDisplayed(R.id.upcomingEventImage)
                .checkIfElementWithIdIsDisplayed(R.id.upcomingEventTitleText)
                .checkIfTextIsCorrect(getString(R.string.android_developers_meet_up), R.id.upcomingEventDescriptionText)
                .checkIfElementWithIdIsDisplayed(R.id.upcomingEventLocationImage)
                .checkIfElementWithIdIsDisplayed(R.id.upcomingEventPlaceTitleText)
                .checkIfElementWithIdIsDisplayed(R.id.upcomingEventPlaceLocationText)
                .checkIfElementWithIdIsDisplayed(R.id.upcomingEventCalendarImage)
                .checkIfElementWithIdIsDisplayed(R.id.upcomingEventDateText)
                .checkIfElementWithIdIsDisplayed(R.id.upcomingEventClockImage)
                .checkIfElementWithIdIsDisplayed(R.id.upcomingEventTimeText)

    }

    @Test
    fun isEveryDividerDisplayedOnUpcomingEventCard() {
        EventsRobot().checkIfElementWithIdIsDisplayed(R.id.upcomingEventTitleDivider)
                .checkIfElementWithIdIsDisplayed(R.id.upcomingEventlocationDivider)
                .checkIfElementWithIdIsDisplayed(R.id.upcomingEventTimeDivider)
                .checkIfElementWithIdIsDisplayed(R.id.upcomingEventDateDivider)

    }
}