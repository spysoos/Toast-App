package pl.droidsonroids.toast.viewmodels.speaker

import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.assertThat
import org.junit.Test
import org.mockito.Mock
import pl.droidsonroids.toast.RxTestBase
import pl.droidsonroids.toast.data.State
import pl.droidsonroids.toast.data.api.speaker.ApiSpeaker
import pl.droidsonroids.toast.data.mapper.toDto
import pl.droidsonroids.toast.repositories.speaker.SpeakersRepository
import pl.droidsonroids.toast.testSpeakers
import pl.droidsonroids.toast.testSpeakersPage
import pl.droidsonroids.toast.utils.LoadingStatus

class SpeakersViewModelTest : RxTestBase() {

    @Mock
    lateinit var speakersRepository: SpeakersRepository
    lateinit var speakersViewModel: SpeakersViewModel

    @Test
    fun shouldLoadFirstPage() {
        whenever(speakersRepository.getSpeakersPage()).thenReturn(Single.just(testSpeakersPage))
        speakersViewModel = SpeakersViewModel(speakersRepository)

        checkIsFirstPageLoaded()
    }

    private fun checkIsFirstPageLoaded() {
        val speakerItemViewModelList = speakersViewModel.speakersSubject.value
        val testSpeaker = testSpeakers.first()
        val resultSpeakerViewModel = (speakerItemViewModelList.first() as? State.Item)?.item

        assertSpeaker(resultSpeakerViewModel, testSpeaker)
        assertThat(speakersViewModel.loadingStatus.get(), equalTo(LoadingStatus.SUCCESS))
    }

    private fun assertSpeaker(resultSpeakerViewModel: SpeakerItemViewModel?, testSpeaker: ApiSpeaker) {
        assertThat(resultSpeakerViewModel?.id, equalTo(testSpeaker.id))
        assertThat(resultSpeakerViewModel?.name, equalTo(testSpeaker.name))
        assertThat(resultSpeakerViewModel?.avatar, equalTo(testSpeaker.avatar.toDto()))
    }


    @Test
    fun shouldFailLoadFirstPage() {
        whenever(speakersRepository.getSpeakersPage()).thenReturn(Single.error(Exception()))
        speakersViewModel = SpeakersViewModel(speakersRepository)

        val speakerItemViewModelList: List<State<SpeakerItemViewModel>>? = speakersViewModel.speakersSubject.value

        assertThat(speakerItemViewModelList, nullValue())
        assertThat(speakersViewModel.loadingStatus.get(), equalTo(LoadingStatus.ERROR))
    }

    @Test
    fun shouldLoadFirstPageAfterRetry() {
        whenever(speakersRepository.getSpeakersPage()).thenReturn(Single.error(Exception()))
        speakersViewModel = SpeakersViewModel(speakersRepository)

        whenever(speakersRepository.getSpeakersPage()).thenReturn(Single.just(testSpeakersPage))

        speakersViewModel.retryLoading()

        checkIsFirstPageLoaded()
    }

    @Test
    fun shouldHaveLoadingItemWhenNextPageAvailable() {
        val testSpeakersPageWithNextPageAvailable = testSpeakersPage.copy(allPagesCount = 2)
        whenever(speakersRepository.getSpeakersPage()).thenReturn(Single.just(testSpeakersPageWithNextPageAvailable))
        speakersViewModel = SpeakersViewModel(speakersRepository)

        val speakerItemViewModelList = speakersViewModel.speakersSubject.value
        checkIsFirstPageLoaded()
        assertThat(speakerItemViewModelList.last(), equalTo(State.Loading as State<SpeakerItemViewModel>))
    }

    @Test
    fun shouldHaveErrorItemWhenNextPageLoadFailed() {
        val testSpeakersPageWithNextPageAvailable = testSpeakersPage.copy(allPagesCount = 2)
        whenever(speakersRepository.getSpeakersPage()).thenReturn(Single.just(testSpeakersPageWithNextPageAvailable))
        whenever(speakersRepository.getSpeakersPage(2)).thenReturn(Single.error(Exception()))
        speakersViewModel = SpeakersViewModel(speakersRepository)

        speakersViewModel.loadNextPage()

        val speakerItemViewModelList = speakersViewModel.speakersSubject.value
        assertThat(speakerItemViewModelList.last() is State.Error, `is`(true))
    }

    @Test
    fun shouldLoadNextPage() {
        val itemsCountOnAllPages = 2
        val testSpeakersPageWithNextPageAvailable = testSpeakersPage.copy(allPagesCount = 2)
        whenever(speakersRepository.getSpeakersPage()).thenReturn(Single.just(testSpeakersPageWithNextPageAvailable))
        val secondSpeakersPage = testSpeakersPage.copy(pageNumber = 2, allPagesCount = 2)
        whenever(speakersRepository.getSpeakersPage(2)).thenReturn(Single.just(secondSpeakersPage))
        speakersViewModel = SpeakersViewModel(speakersRepository)

        speakersViewModel.loadNextPage()

        val speakerItemViewModelList = speakersViewModel.speakersSubject.value
        assertThat(speakerItemViewModelList.size, equalTo(itemsCountOnAllPages))
        assertSpeaker((speakerItemViewModelList[1] as? State.Item)?.item, testSpeakers.first())
    }
}