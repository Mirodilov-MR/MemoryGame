package com.example.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {
    private val imageList = arrayOf(
        R.drawable.pictire1, R.drawable.pictire2, R.drawable.pictire3,
        R.drawable.pictire1, R.drawable.pictire2, R.drawable.pictire3,
        R.drawable.pictire4, R.drawable.pictire5, R.drawable.pictire6,
        R.drawable.pictire5, R.drawable.pictire4, R.drawable.pictire6
    )

    private val imageViews = arrayOfNulls<ImageView>(12)
    private val imageStatus = BooleanArray(12)
    private var openImageCount = 0
    private var resultProg = 0
    private var animationInProgress = false
    private lateinit var countdownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setOnClickListeners()
        startCountdown()
    }

    private fun initViews() {
        imageViews[0] = image1
        imageViews[1] = image2
        imageViews[2] = image3
        imageViews[3] = image4
        imageViews[4] = image5
        imageViews[5] = image6
        imageViews[6] = image7
        imageViews[7] = image8
        imageViews[8] = image9
        imageViews[9] = image10
        imageViews[10] = image11
        imageViews[11] = image12
    }

    private fun setOnClickListeners() {
        for (i in imageViews.indices) {
            val imageView = imageViews[i]
            imageView?.setOnClickListener {
                if (!animationInProgress && !imageStatus[i]) {
                    if (openImageCount < 2) {
                        flipImage(imageView, i)
                    } else {

                    }
                }
            }
        }
    }

    private fun flipImage(imageView: ImageView?, index: Int) {
        imageView?.apply {
            val animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.anim_1)
            startAnimation(animation)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    animationInProgress = true
                }

                override fun onAnimationEnd(animation: Animation?) {
                    val animation2 = AnimationUtils.loadAnimation(this@MainActivity, R.anim.anim_2)
                    startAnimation(animation2)
                    setImageResource(imageList[index])
                    animation2.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(animation: Animation?) {}

                        override fun onAnimationEnd(animation: Animation?) {
                            imageStatus[index] = true
                            openImageCount++
                            if (openImageCount == 2) {
                                checkImagesMatch()
                            }
                            animationInProgress = false
                        }

                        override fun onAnimationRepeat(animation: Animation?) {}
                    })
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
        }
    }

    private fun checkImagesMatch() {
        val openImages = mutableListOf<Int>()
        for (i in imageStatus.indices) {
            if (imageStatus[i]) {
                openImages.add(i)
            }
        }

        if (openImages.size == 2) {
            val index1 = openImages[0]
            val index2 = openImages[1]
            if (imageList[index1] == imageList[index2]) {
                hideImage(index1)
                hideImage(index2)
                resultProg++
                if (resultProg == 6) {
                    // All pictures found, navigate to the second screen
                    finishGame()
                }
            } else {
                flipImageBack(index1)
                flipImageBack(index2)
            }
            openImageCount = 0
        }
    }

    private fun hideImage(index: Int) {
        c1.visibility = View.GONE
        imageViews[index]?.visibility = View.INVISIBLE
        imageStatus[index] = false
    }

    private fun flipImageBack(index: Int) {
        val imageView = imageViews[index]
        imageView?.apply {
            val animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.anim_1)
            startAnimation(animation)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    animationInProgress = true
                }

                override fun onAnimationEnd(animation: Animation?) {
                    val animation2 = AnimationUtils.loadAnimation(this@MainActivity, R.anim.anim_2)
                    startAnimation(animation2)
                    setImageResource(R.drawable.asd)
                    animation2.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(animation: Animation?) {}

                        override fun onAnimationEnd(animation: Animation?) {
                            animationInProgress = false
                        }

                        override fun onAnimationRepeat(animation: Animation?) {}
                    })
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
        }
        imageStatus[index] = false
        openImageCount--
    }

    private fun startCountdown() {
        countdownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minute = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                timer_txt.text = String.format("%02d:%02d", minute, seconds)
                appCompatSeekBar.progress = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                finishGame()
            }
        }
        countdownTimer.start()
    }

    private fun finishGame() {
        countdownTimer.cancel()
        this.finish()
        val intent = Intent(applicationContext, MainActivity2::class.java)
        intent.putExtra(Constants.getInt, resultProg)
        startActivity(intent)
        Toast.makeText(applicationContext, "Game finished", Toast.LENGTH_SHORT).show()
    }
}

