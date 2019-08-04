package com.connect.android.client.modules.recommendations

import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.DefaultItemAnimator
import com.connect.android.client.R
import com.connect.android.client.model.profile.User
import com.yuyakaido.android.cardstackview.*
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RecommendationsHelper(private val adapter: RecommendationsAdapter, private val manager: CardStackLayoutManager) {

    companion object {
        private const val MIN_CARD_COUNT = 5
    }

    private lateinit var cardStackView: CardStackView

    private val connectSubject = PublishSubject.create<User>()

    private val appearSubject = PublishSubject.create<User>()

    private val disconnectSubject = PublishSubject.create<String>()

    private val loadNextSubject = PublishSubject.create<Unit>()

    fun init(cardStackView: CardStackView) {
        this.cardStackView = cardStackView
        with(manager)
        {
            setStackFrom(StackFrom.None)
            setVisibleCount(3)
            setTranslationInterval(8.0f)
            setScaleInterval(0.95f)
            setSwipeThreshold(0.3f)
            setMaxDegree(20.0f)
            setDirections(Direction.HORIZONTAL)
            setCanScrollHorizontal(true)
            setCanScrollVertical(true)
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())
            with(cardStackListener as RecommendationsDelegate)
            {
                swipes().subscribe {
                    if (manager.topPosition == adapter.itemCount - MIN_CARD_COUNT) {
                        loadNextSubject.onNext(Unit)
                    }
                    when (it) {
                        Direction.Left -> {
                            connectSubject.onNext(adapter.getItemByPosition(manager.topPosition))
                        }
                        Direction.Right -> {
                            disconnectSubject.onNext(adapter.getItemByPosition(manager.topPosition).id)
                        }
                        else -> {

                        }
                    }
                    drags().subscribe {
                        val rightAlpha = if (it < 0) -it else 0f
                        val leftAlpha = if (it > 0) it else 0f
                        manager.topView.findViewById<View>(R.id.item_swipe_right_indicator).alpha = rightAlpha
                        manager.topView.findViewById<View>(R.id.item_swipe_left_indicator).alpha = leftAlpha
                    }
                    cardAppeared().map { adapter.getItemByPosition(it) }.subscribeWith(appearSubject)
                }
            }
        }
        with(cardStackView) {
            layoutManager = manager
            adapter = this@RecommendationsHelper.adapter
            itemAnimator.apply {
                if (this is DefaultItemAnimator) {
                    supportsChangeAnimations = false
                }
            }
        }
    }

    fun getCurrentItem(): User {
        return adapter.getItemByPosition(manager.topPosition)
    }

    fun disconnect() {
        if (!::cardStackView.isInitialized) {
            throw IllegalStateException("call init fun previously")
        }
        val setting = SwipeAnimationSetting.Builder()
            .setDirection(Direction.Left)
            .setDuration(Duration.Normal.duration)
            .setInterpolator(AccelerateInterpolator())
            .build()
        manager.setSwipeAnimationSetting(setting)
        cardStackView.swipe()
    }

    fun rewind() {
        if (!::cardStackView.isInitialized) {
            throw IllegalStateException("call init fun previously")
        }
        val setting = RewindAnimationSetting.Builder()
            .setDirection(Direction.Bottom)
            .setDuration(Duration.Normal.duration)
            .setInterpolator(DecelerateInterpolator())
            .build()
        manager.setRewindAnimationSetting(setting)
        cardStackView.rewind()
    }

    fun connect() {
        if (!::cardStackView.isInitialized) {
            throw IllegalStateException("call init fun previously")
        }
        val setting = SwipeAnimationSetting.Builder()
            .setDirection(Direction.Right)
            .setDuration(Duration.Normal.duration)
            .setInterpolator(AccelerateInterpolator())
            .build()
        manager.setSwipeAnimationSetting(setting)
        cardStackView.swipe()
    }

    fun connectToUser(): Observable<User> = connectSubject

    fun userAppeared(): Observable<User> = appearSubject

    fun declineUser(): Observable<String> = disconnectSubject

    fun clicks(): Observable<User> = adapter.clicks()

    fun addRecommendations(items: List<User>) {
        adapter.addItems(items)
    }

    fun loadNext(): Observable<Unit> = loadNextSubject
}

class RecommendationsDelegate : CardStackListener {

    private val swipeSubject = PublishSubject.create<Direction>()

    private val dragSubject = PublishSubject.create<Float>()

    private val cardAppearedSubject = PublishSubject.create<Int>()

    fun swipes(): Observable<Direction> = swipeSubject

    fun drags(): Observable<Float> = dragSubject

    fun cardAppeared(): Observable<Int> = cardAppearedSubject

    override fun onCardDisappeared(view: View?, position: Int) {
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        dragSubject.onNext(ratio)
    }

    override fun onCardSwiped(direction: Direction) {
        swipeSubject.onNext(direction)
    }

    override fun onCardCanceled() {
    }

    override fun onCardAppeared(view: View?, position: Int) {
        cardAppearedSubject.onNext(position)
    }

    override fun onCardRewound() {
    }
}