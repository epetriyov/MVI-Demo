package com.connect.android.client.modules.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import com.connect.android.client.R
import com.connect.android.client.modules.base.BaseMviView
import com.jakewharton.rxbinding2.support.design.widget.itemSelections
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.view_main.view.*
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

@SuppressLint("ViewConstructor")
class MainView(context: Context, initialState: MainVS) :
    BaseMviView<MainVIA, MainVOA, MainVS>(context, initialState) {

    val mainViewModel: MainViewModel by inject { parametersOf(initialState) }

    private val tabSelectionSubject = PublishSubject.create<Int>()

    override fun layoutId() = R.layout.view_main

    override fun viewModel() = mainViewModel

    override fun initView(savedViewState: Bundle?) {

    }

    override fun getChildContainer(): ViewGroup = tab_container

    override fun inputActions() =
        listOf(
            Observable.merge(tabSelectionSubject,
                bottom_navigation.itemSelections().distinctUntilChanged().skip(1).map { it.itemId }).map {
                MainVIA.TabClicked(
                    it
                )
            })

    override fun outputActions(): List<Observable<out MainVOA>> = emptyList()

    override fun loadAction() = MainVIA.Init

    override fun bindState(viewState: MainVS) {
        viewState.selectedTabId.bind {
            bottom_navigation.selectedItemId = it
            tabSelectionSubject.onNext(it)
        }
        viewState.navigateToTab.bind {
            val action = when (viewState.selectedTabId.peekContent()) {
                R.id.menu_recommendations -> MainVOA.Recommendations
                R.id.menu_events -> MainVOA.Events
                R.id.menu_contacts -> MainVOA.Contacts
                R.id.menu_chats -> MainVOA.Chats
                R.id.menu_profile -> MainVOA.Profile
                else -> throw IllegalStateException("unknown menuId: ${viewState.selectedTabId.peekContent()}")
            }
            outcomingAction(action)
        }
        viewState.selectFirstTab.bind {
            bottom_navigation.selectedItemId = R.id.menu_recommendations
            tabSelectionSubject.onNext(R.id.menu_recommendations)
        }
    }

}