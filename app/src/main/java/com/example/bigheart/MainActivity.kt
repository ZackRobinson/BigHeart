package com.example.bigheart

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    val tag = "MainActivity"

    private var loveButton: Button? = null
    private var loveFrame: FrameLayout? = null

    private val wikiApiServe by lazy {
        WikiApiService.create()
    }

    private val zrApiService by lazy {
        ZRApiService.create()
    }

    private var disposable: Disposable? = null

    private var otherDisposable: Disposable? = null

    private var yetAnotherDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mediaPlayer = MediaPlayer.create(this, R.raw.baby_shark)
        loveButton = findViewById(R.id.love_button)
        loveFrame = findViewById(R.id.love_frame)
        loveButton!!.setOnClickListener {
            loveFrame!!.visibility = View.VISIBLE
            loveButton!!.visibility = View.GONE
//            mediaPlayer.start()
//            beginSearch("Trump")
            getMyName()
            getMyNames()
        }
    }

    private fun beginSearch(srsearch: String) {
        disposable =
                wikiApiServe.hitCountCheck("query", "json", "search", srsearch)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result -> showSearchResult("${result.query.searchinfo} results found") },
                                { error -> showSearchError(error.message) }
                        )
    }

    private fun getMyName() {
        otherDisposable =
                zrApiService.nameCheck()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { result -> showMyName("${result.name} + /end") }


    }

    private fun getMyNames() {
        yetAnotherDisposable =
                zrApiService.childCheck()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { result -> showMyNames(result.children) }
    }

    private fun showSearchResult(totalHits: String?) {
        Log.d(tag, "" + totalHits)

    }

    private fun showSearchError(message: String?) {
        Log.d(tag, message)
    }

    private fun showMyName(name: String?) {
        Log.d(tag, name)
    }

    private fun showMyNames(list: List<QualificationsModel.Children>) {
        list.forEach {
            Log.d(tag, it.name)
            if(it.children != null)
                showMyNames(it.children)
        }
    }

}
