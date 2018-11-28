package com.micropole.sxwine.module.home

import android.content.res.Configuration
import com.example.baseframe.BaseActivity
import com.micropole.sxwine.R
import com.shuyu.gsyvideoplayer.listener.LockClickListener
import com.shuyu.gsyvideoplayer.utils.Debuger
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import android.view.View
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.player.IjkPlayerManager
import com.shuyu.gsyvideoplayer.player.PlayerFactory


class VideoActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_video

    override fun initView() {
        initVideo()
    }

//    private lateinit var mPlayer: JzvdStd
    private lateinit var detailPlayer: StandardGSYVideoPlayer
    private lateinit var orientationUtils:OrientationUtils
    private  var isPlay:Boolean = false
    private  var isPause:Boolean = false

    private fun initVideo() {
//        mPlayer = findViewById(R.id.mVideoPlayer)
//        mPlayer.setUp(getBundle()?.getString("url"), getBundle()?.getString("title"), Jzvd.SCREEN_WINDOW_NORMAL)
        PlayerFactory.setPlayManager( IjkPlayerManager())
        //外部辅助的旋转，帮助全屏
        detailPlayer = findViewById(R.id.detail_player)
        orientationUtils = OrientationUtils(this, detailPlayer)
//初始化不打开外部的旋转
        orientationUtils.setEnable(false)

        val gsyVideoOption = GSYVideoOptionBuilder()
        gsyVideoOption
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl(getBundle()?.getString("url"))
                .setCacheWithPlay(false)
                .setVideoTitle(getBundle()?.getString("title"))
                .setVideoAllCallBack(object : GSYSampleCallBack() {
                    override fun onPrepared(url: String?, vararg objects: Any) {
                        super.onPrepared(url, *objects)
                        //开始播放了才能旋转和全屏
                        orientationUtils.setEnable(true)
                        isPlay = true
                    }

                    override fun onQuitFullscreen(url: String?, vararg objects: Any) {
                        super.onQuitFullscreen(url, *objects)
                        Debuger.printfError("***** onQuitFullscreen **** " + objects[0])//title
                        Debuger.printfError("***** onQuitFullscreen **** " + objects[1])//当前非全屏player
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo()
                        }
                    }
                }).setLockClickListener(object : LockClickListener {
                    override fun onClick(view: View, lock: Boolean) {
                        if (orientationUtils != null) {
                            //配合下方的onConfigurationChanged
                            orientationUtils.setEnable(!lock)
                        }
                    }
                }).build(detailPlayer)

        detailPlayer.getFullscreenButton().setOnClickListener {
            //直接横屏
            orientationUtils.resolveByClick()

            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            detailPlayer.startWindowFullscreen(this@VideoActivity, true, true)
        }
    }

    override fun initListener() {
    }

    override fun initData() {
        initVideo()
    }

//    override fun onPause() {
//        super.onPause();
//        Jzvd.releaseAllVideos();
//    }
//
//    override fun onBackPressed() {
//        if (Jzvd.backPress()) {
//            return;
//        }
//        super.onBackPressed();
//    }

    override fun onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo()
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }


    override fun onPause() {
        detailPlayer.currentPlayer.onVideoPause()
        super.onPause()
        isPause = true
    }

    override fun onResume() {
        detailPlayer.currentPlayer.onVideoResume(false)
        super.onResume()
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isPlay) {
            detailPlayer.currentPlayer.release()
        }
        if (orientationUtils != null)
            orientationUtils.releaseListener()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            detailPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
        }
    }
}
