package com.breezefieldsalesversacorp.features.mylearning

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieAnimationView
import com.breezefieldsalesversacorp.CustomStatic
import com.breezefieldsalesversacorp.R
import com.breezefieldsalesversacorp.app.FileUtils
import com.breezefieldsalesversacorp.app.NetworkConstant
import com.breezefieldsalesversacorp.app.Pref
import com.breezefieldsalesversacorp.app.types.FragType
import com.breezefieldsalesversacorp.app.utils.AppUtils
import com.breezefieldsalesversacorp.base.BaseResponse
import com.breezefieldsalesversacorp.base.presentation.BaseActivity
import com.breezefieldsalesversacorp.base.presentation.BaseFragment
import com.breezefieldsalesversacorp.features.dashboard.presentation.DashboardActivity
import com.breezefieldsalesversacorp.features.location.LocationWizard
import com.breezefieldsalesversacorp.features.mylearning.apiCall.LMSRepoProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.row_beat_custom_sub_list.ll_row_beat_custom_bub_root
import timber.log.Timber
import java.io.File


class VideoPlayLMS : BaseFragment() {
    private var content_comment_point: Int = 0
    private var content_share_point: Int = 0
    private var content_like_point: Int = 0
    private var content_watch_point: Int = 0
    private lateinit var response: VideoTopicWiseResponse
    private lateinit var mContext: Context
    private lateinit var adapter: VideoAdapter
    private lateinit var adapter1: VideoAdapter1

    //private val videos = ArrayList<VideoLMS>()
    private val videos = ArrayList<VideoLMS>()
    private val exoPlayerItems = ArrayList<ExoPlayerItem>()
    private val exoPlayerItems_ = ArrayList<ExoPlayerItem1>()
    lateinit var viewPager2: ViewPager2

    private lateinit var progressWheel: ProgressBar
    private lateinit var ll_vdo_ply_like: LinearLayout
    private lateinit var iv_vdo_ply_like: ImageView
    private lateinit var iv_vdo_ply_bookmark: ImageView
    private lateinit var tv_frag_vid_bookmark_text: TextView
    private lateinit var ll_frag_vid_bookmark: LinearLayout
    private lateinit var ll_book: LinearLayout
    private lateinit var ll_like: LinearLayout
    private lateinit var ll_comment: LinearLayout
    private lateinit var lottie_bookmark: LottieAnimationView
    private lateinit var ll_vdo_ply_cmmnt: LinearLayout
    private lateinit var ll_vdo_ply_share: LinearLayout
    private lateinit var ll_video_not_found: LinearLayout
    private lateinit var ll_frag_video_play_comments: LinearLayout
    private lateinit var iv_frag_video_comment_hide: ImageView
    private lateinit var et_frag_video_comment: EditText
    private lateinit var iv_frag_video_comment_save: ImageView
    //var lastvideo:Boolean = false
    private lateinit var cmtAdapter: AdapterComment


    private lateinit var rvComments: RecyclerView

    var contentL: ArrayList<ContentL> = ArrayList()

    var commentL: ArrayList<CommentL> = ArrayList()

    var Obj_LMS_CONTENT_INFO: LMS_CONTENT_INFO = LMS_CONTENT_INFO()

    lateinit var currentVideoObj : ContentL

    var current_lms_video_obj: LMS_CONTENT_INFO = LMS_CONTENT_INFO()

    var like_flag = false

    private lateinit var popupWindow: PopupWindow

    private lateinit var question_ans_setL :ArrayList<QuestionL>

    var onActivityStateChanged: VideoAdapter.OnActivityStateChanged? = null
    private lateinit var screenOffReceiver: BroadcastReceiver


    companion object {
        const val REQUEST_CODE_SHARE = 123

        var sequenceQuestionL :ArrayList<SequenceQuestion> = ArrayList()

        var loadedFrom:String = ""
        var topic_id: String = ""
        var topic_name: String = ""
        var previousFrag: String = ""
        var content_position: Int = 0
        var lastvideo: Boolean = false
        var lastvideo_: Boolean = false
        fun getInstance(objects: Any): VideoPlayLMS {
            val videoPlayLMS = VideoPlayLMS()

            if (!TextUtils.isEmpty(objects.toString())) {
                val parts = objects.toString().split("~")
                topic_id = parts[0]
                topic_name = parts[1]
               // content_position = parts[2].toInt()
            } else {
                topic_id = ""
                topic_name = ""
              //  content_position = 0

            }
            println("tag_topic_id" + topic_id)


            val bundle = Bundle()
            bundle.putBoolean("LAST_VIDEO", lastvideo)
            videoPlayLMS.arguments = bundle
            return videoPlayLMS
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater!!.inflate(R.layout.fragment_video_play_l_m_s, container, false)
        initView(view)
        requireActivity().getWindow()
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return view
    }

    private fun initView(view: View) {
        progressWheel = view.findViewById(R.id.pb_frag_video_player)
        ll_vdo_ply_like = view.findViewById(R.id.ll_vdo_ply_like)
        iv_vdo_ply_like = view.findViewById(R.id.iv_vdo_ply_like)
        iv_vdo_ply_bookmark = view.findViewById(R.id.iv_vdo_ply_bookmark)
        tv_frag_vid_bookmark_text = view.findViewById(R.id.tv_frag_vid_bookmark_text)
        ll_frag_vid_bookmark = view.findViewById(R.id.ll_frag_vid_bookmark)
        lottie_bookmark = view.findViewById(R.id.lottie_bookmark)
        ll_vdo_ply_cmmnt = view.findViewById(R.id.ll_vdo_ply_cmmnt)
        ll_vdo_ply_share = view.findViewById(R.id.ll_vdo_ply_share)
        ll_video_not_found = view.findViewById(R.id.ll_video_not_found)
        ll_frag_video_play_comments = view.findViewById(R.id.ll_frag_video_play_comments)
        iv_frag_video_comment_hide = view.findViewById(R.id.iv_frag_video_comment_hide)
        rvComments = view.findViewById(R.id.rv_frag_video_play_comment)
        et_frag_video_comment = view.findViewById(R.id.et_frag_video_comment)
        iv_frag_video_comment_save = view.findViewById(R.id.iv_frag_video_comment_save)
        ll_book = view.findViewById(R.id.ll_book)
        ll_like = view.findViewById(R.id.ll_like)
        ll_comment = view.findViewById(R.id.ll_comment)

        progressWheel.visibility = View.GONE
        ll_video_not_found.visibility = View.GONE
        ll_frag_video_play_comments.visibility = View.GONE
        ll_frag_vid_bookmark.visibility = View.VISIBLE
        lottie_bookmark.visibility = View.GONE

        getPointsAPICalling()

        viewPager2 = view.findViewById(R.id.viewPager2)

        contentL = ArrayList()

        iv_frag_video_comment_hide.setOnClickListener {
            ll_frag_video_play_comments.visibility = View.GONE
        }

        if (topic_id != "") {
            getVideoTopicWise()
            return
        }
        adapter1 = VideoAdapter1(
            mContext,
            videos,
            ll_vdo_ply_like,
            ll_vdo_ply_cmmnt,
            ll_vdo_ply_share,
            object : VideoAdapter1.OnVideoPreparedListener {
                override fun onVideoPrepared(exoPlayerItem: ExoPlayerItem1) {
                    exoPlayerItems_.add(exoPlayerItem)
                }

                override fun onNonVideo() {
                    //Toaster.msgShort(mContext,"Not video")
                }
            })

        viewPager2.adapter = adapter1

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val previousIndex = exoPlayerItems.indexOfFirst { it.exoPlayer.isPlaying }
                if (previousIndex != -1) {
                    val player = exoPlayerItems[previousIndex].exoPlayer
                    player.pause()
                    player.playWhenReady = false
                }
                val newIndex = exoPlayerItems.indexOfFirst { it.position == position }
                if (newIndex != -1) {
                    val player = exoPlayerItems[newIndex].exoPlayer
                    player.playWhenReady = true
                    player.play()
                    //progressWheel.visibility = View.GONE
                }
            }
        })

        /*  adapter = VideoAdapter(mContext, videos, object : VideoAdapter.OnVideoPreparedListener {
              override fun onVideoPrepared(exoPlayerItem: ExoPlayerItem) {
                  exoPlayerItems.add(exoPlayerItem)
              }

              override fun onNonVideo() {
                  //Toaster.msgShort(mContext,"Not video")
              }
          })

          viewPager2.adapter = adapter */




        ll_vdo_ply_share.setOnClickListener {
            //share
        }

    }

    fun getVideoTopicWise() {
        try {
            progressWheel.visibility = View.VISIBLE
            Timber.d("deleteImei call" + AppUtils.getCurrentDateTime())
            val repository = LMSRepoProvider.getTopicList()
            BaseActivity.compositeDisposable.add(
                repository.getTopicsWiseVideo(Pref.user_id!!,topic_id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ result ->
                        response = result as VideoTopicWiseResponse
                        if (response.status == NetworkConstant.SUCCESS) {
                            progressWheel.visibility = View.GONE
                            try {
                                if (response.content_list != null && response.content_list.size > 0) {
                                    ll_video_not_found.visibility = View.GONE
                                    var temp  = response.content_list.distinctBy { it.content_play_sequence.toString() }
                                    contentL = temp as ArrayList<ContentL>
                                    // Sort the content list by content_play_sequence
                                    val sortedList = contentL.sortedBy { it.content_play_sequence.toInt() }.toCollection(ArrayList())
                                    Log.d("sortedList", "" + sortedList)
                                    sequenceQuestionL = ArrayList()
                                    try {
                                        for (i in 0.. sortedList.size-1){
                                            var rootObj : SequenceQuestion = SequenceQuestion()
                                            rootObj.index = i+1
                                            rootObj.completionStatus = sortedList.get(i).CompletionStatus
                                            rootObj.question_list = sortedList.get(i).question_list
                                            sequenceQuestionL.add(rootObj)
                                        }
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                        sequenceQuestionL = ArrayList()
                                    }
                                    var kk = sequenceQuestionL

                                    setVideoAdapter(
                                        sortedList, topic_id, topic_name, content_position,
                                        ll_vdo_ply_like,
                                        ll_vdo_ply_cmmnt,
                                        ll_vdo_ply_share,
                                        iv_vdo_ply_like,
                                        content_watch_point
                                    )



                                } else {
                                    Toast.makeText(mContext, "No video found", Toast.LENGTH_SHORT)
                                        .show()
                                    ll_video_not_found.visibility = View.VISIBLE
                                }
                            } catch (ex: Exception) {
                                ex.printStackTrace()
                            }
                        } else {
                            progressWheel.visibility = View.GONE
                            ll_video_not_found.visibility = View.VISIBLE
                            (mContext as DashboardActivity).showSnackMessage(getString(R.string.no_data_found))

                        }
                    }, { error ->
                        println("errortopicwiselist"+error.message)
                        progressWheel.visibility = View.GONE
                        ll_video_not_found.visibility = View.GONE
                        (mContext as DashboardActivity).showSnackMessage(getString(R.string.something_went_wrong))
                    })
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            progressWheel.visibility = View.GONE
            ll_video_not_found.visibility = View.GONE
            (mContext as DashboardActivity).showSnackMessage(getString(R.string.something_went_wrong))
        }
    }

    fun setVideoAdapter(
        contentL: ArrayList<ContentL>,
        topic_id: String,
        topic_name: String,
        content_position: Int,
        ll_vdo_ply_like: LinearLayout,
        ll_vdo_ply_cmmnt: LinearLayout,
        ll_vdo_ply_share: LinearLayout,
        iv_vdo_ply_like: ImageView,
        content_watch_point: Int,
    ) {
        adapter = VideoAdapter(
            viewPager2,
            mContext,
            contentL,
            topic_id,
            topic_name,
            Companion.content_position,
            ll_vdo_ply_like,
            ll_vdo_ply_cmmnt,
            ll_vdo_ply_share,
            iv_vdo_ply_like,
            iv_vdo_ply_bookmark,
/*
            like_flag,
*/
            object : VideoAdapter.OnVideoPreparedListener {

                override fun onLikeClick(isLike:Boolean) {
                    contentL.filter { it.content_id.equals(currentVideoObj.content_id) }.first()./*isLiked*/ like_flag = isLike
                    //adapter.notifyDataSetChanged()
                    println("tag_like_check $isLike for ${currentVideoObj.content_title}")
                    for(i in 0..contentL.size-1){
                        println("tag_like_check loop name : ${contentL.get(i).content_title} isLike :  ${contentL.get(i).isLiked}")
                    }
                }

                override fun onBookmarkClick() {



                    var obj = VidBookmark()
                    obj.topic_id = topic_id
                    obj.topic_name = topic_name
                    obj.content_id = currentVideoObj.content_id
                    obj.content_name = currentVideoObj.content_title
                    obj.content_desc = currentVideoObj.content_description
                    obj.content_bitmap = currentVideoObj.content_thumbnail
                    obj.content_url = currentVideoObj.content_url
                    try {
                        if(currentVideoObj.isBookmarked == null){
                            currentVideoObj.isBookmarked = "0"
                        }
                    } catch (e: Exception) {
                        currentVideoObj.isBookmarked = "0"
                    }
                    if(currentVideoObj.isBookmarked.equals("1")){
                        obj.isBookmarked = "0"
                        contentL.filter { it.content_id.toString().equals(currentVideoObj.content_id.toString()) }.first().isBookmarked = "0"
                    }else{
                        obj.isBookmarked = "1"
                        contentL.filter { it.content_id.toString().equals(currentVideoObj.content_id.toString()) }.first().isBookmarked = "1"
                    }
                    if (obj.isBookmarked.equals("1")) {
                        ll_book.visibility =View.VISIBLE
                        ll_book.setBackground(mContext.getResources().getDrawable(R.drawable.back_round_corner_lms_round_white));
                        iv_vdo_ply_bookmark.setImageResource(R.drawable.bookmark_green)
                        //iv_vdo_ply_bookmark.setColorFilter(ContextCompat.getColor(mContext, R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY)
                    } else {
                        ll_book.visibility =View.VISIBLE
                        ll_book.setBackground(mContext.getResources().getDrawable(R.drawable.back_round_corner_lms_round));
                        iv_vdo_ply_bookmark.setImageResource(R.drawable.save_instagram)
                       // iv_vdo_ply_bookmark.setColorFilter(ContextCompat.getColor(mContext, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY)

                    }



                    bookmarkApi(obj)
                    if(obj.isBookmarked.equals("1")) {
                        lottie_bookmark.visibility = View.VISIBLE

                        val animator = ValueAnimator.ofFloat(0f, .5f)
                        animator.addUpdateListener { animation: ValueAnimator ->
                            lottie_bookmark.setProgress(animation.animatedValue as Float)
                        }
                        animator.start()
                        tv_frag_vid_bookmark_text.text = "Saved"
                    }else{
                        tv_frag_vid_bookmark_text.text = "Save"
                    }
                    Handler().postDelayed(Runnable {
                        lottie_bookmark.visibility = View.GONE
                    }, 1000)


                }

                override fun onEndofVidForCountUpdate() {
                    contentCountSaveAPICalling()
                }

                override fun onVideoPrepared(exoPlayerItem: ExoPlayerItem) {
                    exoPlayerItems.add(exoPlayerItem)
                }

                override fun onNonVideo() {
                    //Toaster.msgShort(mContext,"Not video")
                }

                override fun onContentInfoAPICalling(obj: LMS_CONTENT_INFO) {

                   // showWatchPointPopup(content_watch_point)

                    var filterResForCompleteStatus = response.content_list.filter { it.content_id.toInt() == obj.content_id }.first()

                    obj.like_flag = contentL.filter { it.content_id.toString().equals(obj.content_id.toString()) }.first().like_flag
                    if(contentL.filter { it.content_id.toString().equals(obj.content_id.toString()) }.first().content_watch_completed){
                        obj.content_watch_completed = true
                        obj.Watch_Percentage = 100
                    }


                    obj.CompletionStatus = false
                    obj.QuizScores = 0
                    obj.comment_list = commentL.filter {
                        it.content_id.toString().equals(obj.content_id.toString())
                    } as ArrayList<CommentL>
                    Obj_LMS_CONTENT_INFO = obj
                    println("onContentInfoAPICallingComment" + commentL.filter {
                        it.content_id.equals(
                            obj.content_id
                        )
                    } as ArrayList<CommentL>)
                    println("onContentInfoAPICalling" + obj.comment_list)
                    println("onContentInfoAPICalling_obj" + obj)

                    println(
                        "tag_video_save_api contentID ${obj.content_id} ${
                            commentL.filter {
                                it.content_id.toString().equals(obj.content_id.toString())
                            } as ArrayList<CommentL>
                        }")
                    obj.comment_list = ArrayList()
                    saveContentWiseInfo(obj)

                    /*if (filterResForCompleteStatus.content_watch_completed == true) {
                        obj.CompletionStatus = false
                        obj.content_watch_completed = true
                        obj.Watch_Percentage = 100
                        obj.QuizScores = 0
                        obj.comment_list = commentL.filter {
                            it.content_id.toString().equals(obj.content_id.toString())
                        } as ArrayList<CommentL>
                        Obj_LMS_CONTENT_INFO = obj
                        println("onContentInfoAPICallingComment" + commentL.filter {
                            it.content_id.equals(
                                obj.content_id
                            )
                        } as ArrayList<CommentL>)
                        println("onContentInfoAPICalling" + obj.comment_list)
                        println("onContentInfoAPICalling_obj" + obj)

                        println(
                            "tag_video_save_api contentID ${obj.content_id} ${
                                commentL.filter {
                                    it.content_id.toString().equals(obj.content_id.toString())
                                } as ArrayList<CommentL>
                            }")
                        obj.comment_list = ArrayList()
                        saveContentWiseInfo(obj)
                    }
                    else {
                        if ( *//*!obj.content_length.equals("0") &&*//* obj.content_watch_length .equals("0") ||  obj.content_watch_length .equals("00:00:00")) {
                            obj.CompletionStatus = false
                            obj.like_flag = like_flag
                            obj.QuizScores = 0
                            obj.comment_list = commentL.filter {
                                it.content_id.toString().equals(obj.content_id.toString())
                            } as ArrayList<CommentL>
                            Obj_LMS_CONTENT_INFO = obj
                            println("onContentInfoAPICallingComment" + commentL.filter {
                                it.content_id.equals(
                                    obj.content_id
                                )
                            } as ArrayList<CommentL>)
                            println("onContentInfoAPICalling" + obj.comment_list)
                            println("onContentInfoAPICalling_obj" + obj)

                            println(
                                "tag_video_save_api contentID ${obj.content_id} ${
                                    commentL.filter {
                                        it.content_id.toString().equals(obj.content_id.toString())
                                    } as ArrayList<CommentL>
                                }")
                            obj.comment_list = ArrayList()
                            saveContentWiseInfo(obj)
                        }
                        else if (!obj.content_length.equals("0") && !obj.content_watch_length.equals("0")){
                            obj.CompletionStatus = false
                            obj.QuizScores = 0
                            obj.comment_list = commentL.filter {
                                it.content_id.toString().equals(obj.content_id.toString())
                            } as ArrayList<CommentL>
                            Obj_LMS_CONTENT_INFO = obj
                            println("onContentInfoAPICallingComment" + commentL.filter {
                                it.content_id.equals(
                                    obj.content_id
                                )
                            } as ArrayList<CommentL>)
                            println("onContentInfoAPICalling" + obj.comment_list)
                            println("onContentInfoAPICalling_obj" + obj)

                            println(
                                "tag_video_save_api contentID ${obj.content_id} ${
                                    commentL.filter {
                                        it.content_id.toString().equals(obj.content_id.toString())
                                    } as ArrayList<CommentL>
                                }")
                            obj.comment_list = ArrayList()
                            saveContentWiseInfo(obj)
                        }
                    }*/
                }

                override fun onCommentCLick(obj: ContentL) {

                    //onCommentClick(obj.content_id)
                }

                override fun onShareClick(obj: ContentL) {

                }

                @SuppressLint("SuspiciousIndentation")
                override fun onQuestionAnswerSetPageLoad(obj: ArrayList<QuestionL>, position: Int) {

                    try {

                        println("tag_value_contentLSize"+contentL.size)
                        println("tag_value_position"+position)

                        if (contentL.size -1 == position)
                            lastvideo = true
                        else
                            lastvideo = false

                        LmsQuestionAnswerSet.lastVideo = /*true*/ lastvideo
                        println("tag_value_set setting value"+lastvideo)

                        //(context as DashboardActivity).loadFragment(FragType.LmsQuestionAnswerSet, true, obj/*+"~"+lastvideo*/)


                        //Pref.QuestionAfterNoOfContentForLMS = "1" //testing code
                        //Pref.videoCompleteCount = (Pref.videoCompleteCount.toString().toInt() + 1).toString()
                        if(Pref.videoCompleteCount.toInt() % Pref.QuestionAfterNoOfContentForLMS.toInt() ==0) {
                            //load question
                            question_ans_setL = ArrayList()
                            //for (i in 0..Pref.QuestionAfterNoOfContentForLMS.toInt() - 1) {8
                            for (i in Pref.videoCompleteCount.toInt()-1 downTo (Pref.videoCompleteCount.toInt()-Pref.QuestionAfterNoOfContentForLMS.toInt())) {
                                if(sequenceQuestionL.get(i).completionStatus == false){
                                    var questionRootObj = sequenceQuestionL.get(i).question_list
                                    question_ans_setL.addAll(questionRootObj)
                                }
                            }
                            if (question_ans_setL.size > 0) {
                                LmsQuestionAnswerSet.topic_name = topic_name
                                    (context as DashboardActivity).loadFragment(
                                        FragType.LmsQuestionAnswerSet,
                                        true,
                                        question_ans_setL/*+"~"+lastvideo*/
                                    )

                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }


                }
            },
            object : VideoAdapter.OnLastVideoCompleteListener {
                override fun onLastVideoComplete() {
                    lastvideo = true

                    lastvideo_ = true

                    if (lastvideo_ == true){
                        (context as DashboardActivity).onBackPressed()
                    }
                }
            },
            content_watch_point
        )

        viewPager2.adapter = adapter
       // viewPager2.setUserInputEnabled(false)
        onActivityStateChanged  = adapter.registerActivityState()

        // Register the screen off receiver
        screenOffReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == Intent.ACTION_SCREEN_OFF) {
                    adapter.pauseCurrentVideo()
                }
            }
        }

        val filter = IntentFilter(Intent.ACTION_SCREEN_OFF)
        requireActivity().registerReceiver(screenOffReceiver, filter)

       /* try {
            viewPager2.setCurrentItem(viewPager2.currentItem + 2, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }*/



        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                // api call with currentVideoObj if currentVideoObj!=null
                Pref.videoCompleteCount = (position+1).toString()
                try {
                    Pref.LastVideoPlay_TopicID = topic_id
                    Pref.LastVideoPlay_TopicName = topic_name
                    Pref.LastVideoPlay_VidPosition = position.toString()
                    Pref.LastVideoPlay_BitmapURL = contentL.get(position).content_thumbnail.toString()
                    Pref.LastVideoPlay_ContentID = contentL.get(position).content_id
                    Pref.LastVideoPlay_ContentName = contentL.get(position).content_title
                    Pref.LastVideoPlay_ContentDesc = contentL.get(position).content_description
                    Pref.LastVideoPlay_ContentParcent = contentL.get(position).Watch_Percentage

                } catch (e: Exception) {
                    e.printStackTrace()
                }



                currentVideoObj = contentL.get(position)
                println("Position CompanionValue:"+ contentL.get(position).content_watch_length)

                try {
                    if(currentVideoObj.isBookmarked == null){
                        currentVideoObj.isBookmarked = "0"
                    }
                } catch (e: Exception) {
                    currentVideoObj.isBookmarked = "0"
                }

                try {
                    if (currentVideoObj.isBookmarked.equals("1")) {
                        ll_book.visibility = View.VISIBLE
                        ll_book.setBackground(mContext.getResources().getDrawable(R.drawable.back_round_corner_lms_round_white));
                        iv_vdo_ply_bookmark.setImageResource(R.drawable.bookmark_green)
                        //iv_vdo_ply_bookmark.setColorFilter(ContextCompat.getColor(mContext, R.color.approved_green), android.graphics.PorterDuff.Mode.MULTIPLY)
                        tv_frag_vid_bookmark_text.text = "Saved"
                    } else {
                        ll_book.visibility = View.VISIBLE
                        ll_book.setBackground(mContext.getResources().getDrawable(R.drawable.back_round_corner_lms_round));
                        iv_vdo_ply_bookmark.setImageResource(R.drawable.save_instagram)
                        //iv_vdo_ply_bookmark.setColorFilter(ContextCompat.getColor(mContext, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY)
                        tv_frag_vid_bookmark_text.text = "Save"
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                if (contentL.get(position).isAllowLike) {
                    ll_vdo_ply_like.visibility = View.VISIBLE
                } else {
                    ll_vdo_ply_like.visibility = View.GONE
                }
                if (contentL.get(position).isAllowComment) {
                    ll_comment.visibility =View.VISIBLE
                    ll_vdo_ply_cmmnt.visibility = View.VISIBLE
                } else {
                    ll_comment.visibility =View.INVISIBLE
                    ll_vdo_ply_cmmnt.visibility = View.GONE
                }
               /* if (contentL.get(position).isAllowShare) {
                    ll_vdo_ply_share.visibility = View.VISIBLE
                } else {
                    ll_vdo_ply_share.visibility = View.GONE
                }*/

                ll_vdo_ply_like.setOnClickListener {
                    if (like_flag) {
                        ll_like.visibility = View.VISIBLE
                        ll_like.setBackground(mContext.getResources().getDrawable(R.drawable.back_round_corner_lms_round))
                        iv_vdo_ply_like.setImageResource(R.drawable.like_white)
                        like_flag = false
                        contentL.get(position).isLiked = false
                        contentL.filter { it.content_id.equals(currentVideoObj.content_id) }.first()./*isLiked*/ like_flag = false
                    } else {
                        ll_like.visibility = View.VISIBLE
                        like_flag = true
                        ll_like.setBackground(mContext.getResources().getDrawable(R.drawable.back_round_corner_lms_round_white))
                        iv_vdo_ply_like.setImageResource(R.drawable.heart_red)
                        contentL.get(position).isLiked = true
                        contentL.filter { it.content_id.equals(currentVideoObj.content_id) }.first()./*isLiked*/ like_flag = true
                        showLikePointPopup(content_like_point)
                        Pref.like_count = (Pref.like_count + 1)
                        contentCountSaveAPICalling()
                    }

                    contentL.filter { it.content_id.toString().equals(Pref.LastVideoPlay_ContentID.toString()) }.first().like_flag = like_flag

                    var obj: LMS_CONTENT_INFO = LMS_CONTENT_INFO()
                    obj.user_id = Pref.user_id.toString()
                    obj.topic_id = Pref.LastVideoPlay_TopicID.toInt()
                    obj.topic_name = Pref.LastVideoPlay_TopicName
                    obj.content_id = Pref.LastVideoPlay_ContentID.toInt()
                    obj.like_flag = like_flag
                    obj.share_count = 0
                    obj.no_of_comment = 0
                    obj.content_length = Obj_LMS_CONTENT_INFO.content_length//"00:00:01"
                    obj.content_watch_length = Obj_LMS_CONTENT_INFO.content_watch_length//"00:00:01"
                    obj.content_watch_start_date = AppUtils.getCurrentDateyymmdd()
                    obj.content_watch_end_date = AppUtils.getCurrentDateyymmdd()
                    obj.content_watch_completed = Obj_LMS_CONTENT_INFO.content_watch_completed//false
                    obj.content_last_view_date_time = AppUtils.getCurrentDateTimeNew()
                    obj.WatchStartTime = Obj_LMS_CONTENT_INFO.WatchStartTime//"00:00:00"
                    obj.WatchEndTime = Obj_LMS_CONTENT_INFO.WatchEndTime//"00:00:00"
                    obj.WatchedDuration = Obj_LMS_CONTENT_INFO.WatchedDuration//"00:00:00"
                    obj.Timestamp = AppUtils.getCurrentDateTimeNew()
                    obj.DeviceType = "Mobile"
                    obj.Operating_System = "Android"
                    obj.Location = LocationWizard.getNewLocationName(mContext, Pref.current_latitude.toDouble(), Pref.current_longitude.toDouble())
                    obj.PlaybackSpeed = LocationWizard.getNewLocationName(mContext, Pref.current_latitude.toDouble(), Pref.current_longitude.toDouble())
                    obj.Watch_Percentage = Obj_LMS_CONTENT_INFO.Watch_Percentage
                    obj.QuizAttemptsNo = 0
                    obj.QuizScores = 0
                    obj.CompletionStatus = false
                    val comment_listL: ArrayList<CommentL> = ArrayList()
                    obj.comment_list = comment_listL

                    saveContentWiseInfo(obj)
                }




                try {
                    if(contentL.get(position)./*isLiked*/ like_flag == true){
                        like_flag = true

                        ll_like.visibility = View.VISIBLE
                        ll_like.setBackground(mContext.getResources().getDrawable(R.drawable.back_round_corner_lms_round_white))
                        iv_vdo_ply_like.setImageResource(R.drawable.heart_red)
                    }else{
                       // Pref.like_count = (Pref.like_count -1)
                        ll_like.visibility = View.VISIBLE
                        like_flag = false
                        ll_like.setBackground(mContext.getResources().getDrawable(R.drawable.back_round_corner_lms_round))
                        iv_vdo_ply_like.setImageResource(R.drawable.like_white)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                ll_vdo_ply_cmmnt.setOnClickListener {
                    onCommentClick(contentL.get(position).content_id)
                }

                ll_vdo_ply_share.setOnClickListener {
                    println("tag_share from ll_vdo_ply_share.setOnClickListener")
                    //onShareClickMdodify(contentL.get(position))

                    val intent = Intent(Intent.ACTION_SEND)
                    intent.putExtra(Intent.EXTRA_TEXT, currentVideoObj.content_url)
                    intent.type = "text/plain"
                    context!!.startActivity(Intent.createChooser(intent, "Share Via"))
                }

                val comment_list: ArrayList<CommentL> = ArrayList()

                current_lms_video_obj.user_id = Pref.user_id.toString()
                current_lms_video_obj.topic_id = Companion.topic_id.toInt()
                current_lms_video_obj.topic_name = Companion.topic_name
                current_lms_video_obj.content_id = contentL.get(position).content_id.toInt()
                current_lms_video_obj.like_flag = false
                current_lms_video_obj.share_count = 0
                current_lms_video_obj.no_of_comment = 0
                current_lms_video_obj.content_length = "00:00:00"
                current_lms_video_obj.content_watch_length = "00:00:00"
                current_lms_video_obj.content_watch_start_date = AppUtils.getCurrentDateyymmdd()
                current_lms_video_obj.content_watch_end_date = AppUtils.getCurrentDateyymmdd()
                current_lms_video_obj.content_watch_completed = false
                current_lms_video_obj.content_last_view_date_time = AppUtils.getCurrentDateTimeNew()
                current_lms_video_obj.WatchStartTime = "00:00:00"
                current_lms_video_obj.WatchEndTime = "00:00:00"
                current_lms_video_obj.WatchedDuration = "00:00:00"
                current_lms_video_obj.Timestamp = AppUtils.getCurrentDateTimeNew()
                current_lms_video_obj.DeviceType = "Mobile"
                current_lms_video_obj.Operating_System = "Android"
                current_lms_video_obj.Location = LocationWizard.getNewLocationName(mContext, Pref.current_latitude.toDouble(), Pref.current_longitude.toDouble())
                current_lms_video_obj.PlaybackSpeed = LocationWizard.getNewLocationName(mContext, Pref.current_latitude.toDouble(), Pref.current_longitude.toDouble())
                current_lms_video_obj.Watch_Percentage = 0
                current_lms_video_obj.QuizAttemptsNo = 0
                current_lms_video_obj.QuizScores = 0
                current_lms_video_obj.CompletionStatus = false
                current_lms_video_obj.comment_list = comment_list


                println("onPageSelected1 +++++" + position)
                println("video_start_time"+AppUtils.getCurrentDateTimeNew())
                ll_frag_video_play_comments.visibility = View.GONE
                AppUtils.hideSoftKeyboard(mContext as DashboardActivity)
                //progressWheel.visibility = View.VISIBLE
                val previousIndex = exoPlayerItems.indexOfFirst { it.exoPlayer.isPlaying }
                if (previousIndex != -1) {
                    val player = exoPlayerItems[previousIndex].exoPlayer
                    player.pause()
                    player.playWhenReady = false
                }
                val newIndex = exoPlayerItems.indexOfFirst { it.position == position }
                if (newIndex != -1) {
                    val player = exoPlayerItems[newIndex].exoPlayer
                    player.playWhenReady = true
                    player.play()
                    //progressWheel.visibility = View.GONE
                }


            }
        })

        //Handler().postDelayed(Runnable {
            try {
                if(CustomStatic.VideoPosition!=-1)
                    viewPager2.setCurrentItem(CustomStatic.VideoPosition,false)
                CustomStatic.VideoPosition = -1
                println("tag_val ${CustomStatic.VideoPosition}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        //}, 300)

    }

    private fun showWatchPointPopup( content_watch_point: Int) {

        val inflater: LayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.popup_layout_congratulation_, null)
        popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            true
        )
        // val close_button: TextView = popupView.findViewById(R.id.close_button)
        val popup_image: LottieAnimationView = popupView.findViewById(R.id.popup_image)
        // val popup_title: TextView = popupView.findViewById(R.id.popup_title)
        val popup_message: TextView = popupView.findViewById(R.id.popup_message)
        // popup_title.setText("Congratulation"/*+Pref.user_name*/)
        var typeFace: Typeface? = ResourcesCompat.getFont(mContext, R.font.remachinescript_personal_use)
        // popup_title.setTypeface(typeFace)
        popup_message.setText("+$content_watch_point")

        // close_button.setOnClickListener {

        //  }
        popupWindow.setBackgroundDrawable(ColorDrawable())
        popupWindow.isOutsideTouchable = false
        popupWindow.isFocusable = false
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)

        Handler().postDelayed(Runnable {

            popup_image.visibility =View.VISIBLE
            popup_message.visibility = View.VISIBLE
            popupWindow.dismiss()
            viewPager2.setCurrentItem(viewPager2.currentItem + 1, true)
        }, 1)
    }

    @SuppressLint("MissingInflatedId")
    private fun showLikePointPopup(content_like_point: Int) {
        val inflater: LayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.popup_layout_like, null)
        popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            true
        )
        //val close_button: TextView = popupView.findViewById(R.id.close_button)
        val popup_image: LottieAnimationView = popupView.findViewById(R.id.popup_image)
        //val popup_title: TextView = popupView.findViewById(R.id.popup_title)
        val popup_message: TextView = popupView.findViewById(R.id.popup_message)
        //popup_title.setText("Congratulation"/*+Pref.user_name*/)
        var typeFace: Typeface? = ResourcesCompat.getFont(mContext, R.font.remachinescript_personal_use)
        //popup_title.setTypeface(typeFace)
        popup_message.setText("+$content_like_point")
        /*close_button.setOnClickListener {
            popupWindow.dismiss()
        }*/

        println("tag_animate anim")
        val a: Animation = AnimationUtils.loadAnimation(mContext, com.breezefieldsalesversacorp.R.anim.scale)
        a.reset()
        popup_message.clearAnimation()
        popup_message.startAnimation(a)

        popup_image.visibility =View.VISIBLE
        popupWindow.setBackgroundDrawable(ColorDrawable())
        popupWindow.isOutsideTouchable = false
        popupWindow.isFocusable = false
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)

        Handler().postDelayed(Runnable {
            popupWindow.dismiss()
        }, 1700)

    }

    private fun onShareClickMdodify(obj: ContentL) {

        try {
            val Download_Uri = Uri.parse(obj.content_url)
            val request = DownloadManager.Request(Download_Uri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            request.setAllowedOverRoaming(false)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setTitle("Downloading")
            request.setDescription("Downloading File")
            var dir = "${AppUtils.getCurrentDateTime().replace(" ", "").replace("-", "").replace(":", "")}.mp4"
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, dir)

            val downloadManager = mContext!!.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
            var downloadID = downloadManager!!.enqueue(request) // enqueue puts the download request in the queue.

            Handler().postDelayed(Runnable {
                if (downloadID != null) {
                    try {
                        val fileDownloadedUri: Uri =
                            downloadManager.getUriForDownloadedFile(downloadID)
                        val fileDownloadedPath =
                            FileUtils.getPath(mContext, fileDownloadedUri)

                        val shareIntent = Intent(Intent.ACTION_SEND)
                        val fileUrl = Uri.parse(fileDownloadedPath)
                        val file = File(fileUrl.path)
                        //val uri = Uri.fromFile(file)
                        val uri: Uri = FileProvider.getUriForFile(
                            mContext,
                            mContext!!.applicationContext.packageName.toString() + ".provider",
                            file
                        )
                        shareIntent.type = "image/png"
                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
                        //requireActivity().startActivityForResult(Intent.createChooser(shareIntent, "Share using"), REQUEST_CODE_SHARE)

                        startActivity(Intent.createChooser(shareIntent, "Share using"))
                        Pref.share_count = (Pref.share_count + 1)
                        //showSharePointPopup()

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }, 1100)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun saveContentWiseInfo(obj: LMS_CONTENT_INFO) {
        try {
            progressWheel.visibility = View.VISIBLE
            Timber.d("saveContentWiseInfo call" + obj)
            val repository = LMSRepoProvider.getTopicList()
            BaseActivity.compositeDisposable.add(
                repository.saveContentInfoApi(obj)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ result ->
                        val response = result as BaseResponse
                        if (response.status == NetworkConstant.SUCCESS) {
                            progressWheel.visibility = View.GONE

                        } else {
                            progressWheel.visibility = View.GONE
                            (mContext as DashboardActivity).showSnackMessage(getString(R.string.no_data_found))

                        }
                    }, { error ->
                        progressWheel.visibility = View.GONE
                        (mContext as DashboardActivity).showSnackMessage(getString(R.string.something_went_wrong))
                    })
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            progressWheel.visibility = View.GONE
            (mContext as DashboardActivity).showSnackMessage(getString(R.string.something_went_wrong))
        }
    }

    override fun onPause() {
        super.onPause()

        val index = exoPlayerItems.indexOfFirst { it.position == viewPager2.currentItem }
        if (index != -1) {
            val player = exoPlayerItems[index].exoPlayer

            player.playWhenReady = false
            player.pause()
            player.release()
        }

        adapter.pauseCurrentVideo()

    }

    override fun onStop() {
        super.onStop()
        val index = exoPlayerItems.indexOfFirst { it.position == viewPager2.currentItem }
        if (index != -1) {
            val player = exoPlayerItems[index].exoPlayer

            player.playWhenReady = false
            player.stop()
        }
    }

    override fun onResume() {
        super.onResume()

        val index = exoPlayerItems.indexOfFirst { it.position == viewPager2.currentItem }
        if (index != -1) {
            val player = exoPlayerItems[index].exoPlayer
            player.playWhenReady = true
            player.play()
        }
        println("tag_onResume VideoPLayLMs")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (exoPlayerItems.isNotEmpty()) {
            for (item in exoPlayerItems) {
                val player = item.exoPlayer
                player.stop()
                player.clearMediaItems()
            }
        }

        requireActivity().unregisterReceiver(screenOffReceiver)
    }


    fun callDestroy(){
        try {
            super.onDestroy()
            if (exoPlayerItems.isNotEmpty()) {
                for (item in exoPlayerItems) {
                    val player = item.exoPlayer
                    player.stop()
                    player.clearMediaItems()
                }
            }
        } catch (e: Exception) {
           e.printStackTrace()
        }
    }


    fun onCommentClick(content_id: String) {

        commentAPICalling(content_id)

        println("onCommentCLick++++" + content_id)

            iv_frag_video_comment_save.setOnClickListener {

                if (!et_frag_video_comment.text.toString().equals("")) {

                AppUtils.hideSoftKeyboard(mContext as DashboardActivity)
                var obj: CommentL = CommentL()
                obj.topic_id = topic_id
                obj.content_id = content_id
                obj.commented_user_id = Pref.user_id.toString()
                obj.commented_user_name = Pref.user_name.toString()
                obj.comment_id = Pref.user_id + "_" + AppUtils.getCurrentDateTime()
                obj.comment_description = et_frag_video_comment.text.toString()
                obj.comment_date_time = AppUtils.getCurrentDateTime()
                commentL.add(obj)

                et_frag_video_comment.setText("")

                current_lms_video_obj.comment_list = ArrayList()
                current_lms_video_obj.comment_list.add(obj)

                saveContentWiseInfoFOrComment(current_lms_video_obj)
            }
                else{
                    Toast.makeText(mContext, "Please write any comment", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun commentAPICalling(content_id: String) {
            try {
                Timber.d("deleteImei call" + AppUtils.getCurrentDateTime())
                val repository = LMSRepoProvider.getTopicList()
                BaseActivity.compositeDisposable.add(
                    repository.getCommentInfo(topic_id,content_id )
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ result ->
                            val response = result as MyCommentListResponse
                            try {
                                if (response.status == NetworkConstant.SUCCESS) {
                                    Pref.comment_count = (Pref.comment_count + 1)
                                    commentL = ArrayList<CommentL>()
                                    ll_frag_video_play_comments.visibility = View.VISIBLE
                                    loadCommentData(response.comment_list.filter { it.content_id.toString().equals(content_id.toString()) } as ArrayList<CommentL>)

                                }else{
                                    //(mContext as DashboardActivity).showSnackMessage(getString(R.string.something_went_wrong))
                                    var blankL : ArrayList<CommentL> = ArrayList()
                                    loadCommentData(blankL)
                                }
                            } catch (e: Exception) {
                                var blankL : ArrayList<CommentL> = ArrayList()
                                loadCommentData(blankL)
                            }
                        }, { error ->
                            (mContext as DashboardActivity).showSnackMessage(getString(R.string.something_went_wrong))
                        })
                )
            } catch (ex: Exception) {
                ex.printStackTrace()
                (mContext as DashboardActivity).showSnackMessage(getString(R.string.something_went_wrong))
            }
    }

    fun loadCommentData(comL: ArrayList<CommentL>) {
        ll_frag_video_play_comments.visibility = View.VISIBLE
        try {
            if(comL.size>0) {
                cmtAdapter = AdapterComment(mContext, comL)
                rvComments.adapter = cmtAdapter
                rvComments.smoothScrollToPosition(comL.size-1)
            }else{
                if (cmtAdapter != null) {
                    cmtAdapter.clear() // Clear the adapter's data
                    cmtAdapter.notifyDataSetChanged()
                } else {
                    cmtAdapter = AdapterComment(mContext, comL)
                    rvComments.adapter = cmtAdapter
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        contentCountSaveAPICalling()
    }

    private fun saveContentWiseInfoFOrComment(obj: LMS_CONTENT_INFO) {
        try {
            progressWheel.visibility = View.VISIBLE
            Timber.d("saveContentWiseInfo call" + obj)
            val repository = LMSRepoProvider.getTopicList()
            BaseActivity.compositeDisposable.add(
                repository.saveContentInfoApi(obj)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ result ->
                        val response = result as BaseResponse
                        if (response.status == NetworkConstant.SUCCESS) {

                            progressWheel.visibility = View.GONE
                            onCommentClick(obj.content_id.toString())
                            showCommentPointPopup(content_comment_point)
                        } else {
                            progressWheel.visibility = View.GONE
                            (mContext as DashboardActivity).showSnackMessage(getString(R.string.no_data_found))

                        }
                    }, { error ->
                        progressWheel.visibility = View.GONE
                        (mContext as DashboardActivity).showSnackMessage(getString(R.string.something_went_wrong))
                    })
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            progressWheel.visibility = View.GONE
            (mContext as DashboardActivity).showSnackMessage(getString(R.string.something_went_wrong))
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showCommentPointPopup(content_comment_point: Int) {

        val inflater: LayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.popup_layout_congratulation_, null)
        popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            true
        )
        //val close_button: TextView = popupView.findViewById(R.id.close_button)
        val popup_image: LottieAnimationView = popupView.findViewById(R.id.popup_image)
        //val popup_title: TextView = popupView.findViewById(R.id.popup_title)
        val popup_message: TextView = popupView.findViewById(R.id.popup_message)
        //popup_title.setText("Congratulation"/*+Pref.user_name*/)
        var typeFace: Typeface? = ResourcesCompat.getFont(mContext, R.font.remachinescript_personal_use)
        //popup_title.setTypeface(typeFace)
        popup_message.setText("+$content_comment_point")
        /*close_button.setOnClickListener {
            popupWindow.dismiss()
        }*/

        println("tag_animate anim")
        val a: Animation = AnimationUtils.loadAnimation(mContext, com.breezefieldsalesversacorp.R.anim.scale)
        a.reset()
        popup_message.clearAnimation()
        popup_message.startAnimation(a)

        popup_image.visibility =View.VISIBLE
        popupWindow.setBackgroundDrawable(ColorDrawable())
        popupWindow.isOutsideTouchable = false
        popupWindow.isFocusable = false
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)

        Handler().postDelayed(Runnable {
            popupWindow.dismiss()
        }, 1500)

    }

   /* // Override onActivityResult to handle the result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SHARE) {
            if (resultCode == Activity.RESULT_OK) {
                // User has returned, sharing might be completed
                Log.d("Share", "Sharing completed")
                Pref.share_count = (Pref.share_count + 1)
                showSharePointPopup()
            }else {
                // User canceled the sharing
                Log.d("Share", "Sharing canceled")
            }
        }
    }*/

    @SuppressLint("MissingInflatedId")
    /*private fun showSharePointPopup() {
    private fun showSharePointPopup() {
        val inflater: LayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.popup_layout_congratulation_, null)
        popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            true
        )
        val close_button: TextView = popupView.findViewById(R.id.close_button)
        val popup_image: LottieAnimationView = popupView.findViewById(R.id.popup_image)
        val popup_title: TextView = popupView.findViewById(R.id.popup_title)
        val popup_message: TextView = popupView.findViewById(R.id.popup_message)
        popup_title.setText("Congratulation"*//*+Pref.user_name*//*)
        var typeFace: Typeface? = ResourcesCompat.getFont(mContext, R.font.remachinescript_personal_use)
        popup_title.setTypeface(typeFace)
        popup_message.setText("You got 5 points")
        close_button.setOnClickListener {
            popupWindow.dismiss()
        }
        popup_image.visibility =View.VISIBLE
        popupWindow.setBackgroundDrawable(ColorDrawable())
        popupWindow.isOutsideTouchable = false
        popupWindow.isFocusable = false
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)

        Handler().postDelayed(Runnable {
            popupWindow.dismiss()
        }, 2500)

    }*/

    private fun getPointsAPICalling() {
        try {
            Timber.d("deleteImei call" + AppUtils.getCurrentDateTime())
            val repository = LMSRepoProvider.getTopicList()
            BaseActivity.compositeDisposable.add(
                repository.overAllDatalist(Pref.session_token!!)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ result ->
                        val response = result as SectionsPointsList
                        if (response.status == NetworkConstant.SUCCESS) {
                            content_watch_point = response.content_watch_point
                            content_like_point = response.content_like_point
                            content_share_point = response.content_share_point
                            content_comment_point = response.content_comment_point

                        }else{

                        }
                    }, { error ->

                    })
            )
        }
        catch (ex: Exception) {
            ex.printStackTrace()

        }
    }

    private fun bookmarkApi(obj:VidBookmark){
        var apiObj : BookmarkResponse = BookmarkResponse()
        apiObj.user_id = Pref.user_id.toString()
        apiObj.topic_id = obj.topic_id
        apiObj.topic_name = obj.topic_name
        apiObj.content_id = obj.content_id
        apiObj.content_name = obj.content_name
        apiObj.content_desc = obj.content_desc
        apiObj.content_bitmap = obj.content_bitmap
        apiObj.content_url = obj.content_url
        apiObj.addBookmark = obj.isBookmarked

        try {
            val repository = LMSRepoProvider.getTopicList()
            BaseActivity.compositeDisposable.add(
                repository.bookmarkApiCall(apiObj)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ result ->
                        var response = result as BaseResponse
                        if (response.status == NetworkConstant.SUCCESS) {
                            try {
                                (mContext as DashboardActivity).updateBookmarkCnt()
                            } catch (e: Exception) {
                                Pref.CurrentBookmarkCount = 0
                            }
                        } else {

                        }
                    }, { error ->
                       error.printStackTrace()
                    })
            )
        } catch (ex: Exception) {

            ex.printStackTrace()
        }        }

    fun excute(){
        try {
            val repository = LMSRepoProvider.getTopicList()
            BaseActivity.compositeDisposable.add(
                repository.getTopicsWiseVideo(Pref.user_id!!,topic_id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ result ->
                        response = result as VideoTopicWiseResponse
                        if (response.status == NetworkConstant.SUCCESS) {
                            try {
                                if (response.content_list != null && response.content_list.size > 0) {
                                    var temp  = response.content_list.distinctBy { it.content_play_sequence.toString() }
                                    contentL = temp as ArrayList<ContentL>
                                    val sortedList = contentL.sortedBy { it.content_play_sequence.toInt() }.toCollection(ArrayList())
                                    sequenceQuestionL = ArrayList()
                                    try {
                                        for (i in 0.. sortedList.size-1){
                                            var rootObj : SequenceQuestion = SequenceQuestion()
                                            rootObj.index = i+1
                                            rootObj.completionStatus = sortedList.get(i).CompletionStatus
                                            rootObj.question_list = sortedList.get(i).question_list
                                            sequenceQuestionL.add(rootObj)
                                        }
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                        sequenceQuestionL = ArrayList()
                                    }
                                } else {

                                }
                            } catch (ex: Exception) {
                                ex.printStackTrace()
                            }
                        } else {
                        }
                    }, { error ->
                        error.printStackTrace()
                    })
            )
        }
        catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun contentCountSaveAPICalling() {

        try {
            Timber.d("contentCountSaveAPICalling call" + AppUtils.getCurrentDateTime())

            var obj = ContentCountSave_Data()
            obj.user_id = Pref.user_id.toString()
            obj.save_date = AppUtils.getCurrentDateyymmdd()
            obj.like_count = Pref.like_count
            obj.comment_count = Pref.comment_count
            obj.share_count = Pref.share_count
            obj.correct_answer_count = Pref.correct_answer_count
            obj.wrong_answer_count = Pref.wrong_answer_count
            obj.content_watch_count = Pref.content_watch_count
            println("tag_count_api call for $obj")
            val repository = LMSRepoProvider.getTopicList()
            BaseActivity.compositeDisposable.add(
                repository.saveContentCount(obj)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ result ->
                        val response = result as BaseResponse
                        println("tag_count_api response ${response.status}")
                        if (response.status == NetworkConstant.SUCCESS) {
                            try {
                                Pref.like_count = 0
                                Pref.comment_count = 0
                                Pref.share_count = 0
                                Pref.correct_answer_count = 0
                                Pref.wrong_answer_count = 0
                                Pref.content_watch_count = 0
                            }catch (ex:Exception){
                                ex.printStackTrace()
                            }
                        }else{
                            (mContext as DashboardActivity).showSnackMessage(getString(R.string.no_data_found))
                        }
                    }, { error ->
                        println("tag_count_api response error")
                        (mContext as DashboardActivity).showSnackMessage(getString(R.string.something_went_wrong))
                    })
            )
        }
        catch (ex: Exception) {
            ex.printStackTrace()
            println("tag_count_api response error")
            (mContext as DashboardActivity).showSnackMessage(getString(R.string.something_went_wrong))
        }

    }
}