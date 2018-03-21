package com.doctalk

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.jakewharton.rxbinding.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    var disposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val usersList = mutableListOf<Model.Item>()
        recyclerView.adapter = UsersListAdapter(usersList)
        val searchObservable = RxTextView.textChanges(search_bar).filter { charSequence -> charSequence.length > 3 }
                .debounce(300, TimeUnit.MILLISECONDS).map<Any> { charSequence -> charSequence.toString() }

        searchObservable.observeOn(rx.android.schedulers.AndroidSchedulers.mainThread()).subscribe { string ->
            loader.visible()
            disposable?.dispose()
            disposable = RestClient.service().getUsers(string.toString() + "+in:name+type:user").subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally {
                        loader.invisible()
                    }
                    .subscribe({
                        if (it.isSuccessful) {
                            usersList.clear()
                            it.body()?.let {
                                usersList.addAll(it.items)
                                recyclerView.adapter.notifyDataSetChanged()
                                if (usersList.isEmpty()) {
                                    empty_view.visible()
                                } else {
                                    empty_view.invisible()
                                }
                            }
                        }
                    })
        }


    }
}
