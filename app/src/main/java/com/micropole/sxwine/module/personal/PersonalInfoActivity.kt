package com.micropole.sxwine.module.personal

import android.Manifest
import android.content.Intent
import android.os.Environment
import android.text.TextUtils
import android.view.View
import com.blankj.utilcode.util.PermissionUtils
import com.darkhorse.preferencesmanager.PreferencesHelper
import com.dgshanger.sanhxiaofeisc.dialog.ChooseImageDialogWrapper
import com.example.mvpframe.BaseMvpActivity
import com.micropole.sxwine.BuildConfig
import com.micropole.sxwine.R
import com.micropole.sxwine.base.*
import com.micropole.sxwine.common.Constants
import com.micropole.sxwine.module.personal.Bean.UploadPriceEntity
import com.micropole.sxwine.module.personal.Bean.UserInfoEntity
import com.micropole.sxwine.module.personal.mvp.contract.PersonalInfoContract
import com.micropole.sxwine.module.personal.mvp.presenter.PersonalInfoPresenter
import com.micropole.sxwine.utils.XxImageChooseHelper
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.widgets.SelectSexDialog
import kotlinx.android.synthetic.main.activity_personal_ino.*
import kotlinx.android.synthetic.main.item_toolbar.*

/**
 * Created by JohnnyH on 2018/6/14.
 * 个人信息
 */
class PersonalInfoActivity : BaseMvpActivity<PersonalInfoContract.Model,PersonalInfoContract.View,PersonalInfoPresenter>(),PersonalInfoContract.View, View.OnClickListener {

    private lateinit var imageChooseHelper: XxImageChooseHelper

    override fun createPresenter(): PersonalInfoPresenter = PersonalInfoPresenter()
    override fun getLayoutId(): Int = R.layout.activity_personal_ino

    override fun initView() {
        initToolBar(getString(R.string.personal_info))
        toolbar.setNavigationOnClickListener { finish() }
        val userInfo = PreferencesHelper.get(Constants.USER_INFO, UserInfoEntity()) as UserInfoEntity
        iv_avatar.loadImg(mContext,API.HOST+userInfo.avatar,R.mipmap.toubuone)
        if (!TextUtils.isEmpty(userInfo.nickname)){
            tv_nickname.text=userInfo.nickname
        }else{
            if (!TextUtils.isEmpty(userInfo.username)){
                tv_nickname.text=userInfo.username
            }else{
                tv_nickname.text=userInfo.mobile
            }
        }
        if (userInfo.sex=="1"){
            tv_sex.text=getString(R.string.tv_man)
        }else if (userInfo.sex=="2"){
            tv_sex.text=getString(R.string.tv_women)
        }else {
            tv_sex.text=getString(R.string.tv_secrecy)
        }
        ll_avatar.setOnClickListener(this)
        ll_sex.setOnClickListener(this)
        ll_nickname.setOnClickListener(this)
    }

    override fun initData() {

    }


    override fun initListener() {
        imageChooseHelper = XxImageChooseHelper.Builder()
                .setUpActivity(this)
                .setAuthority("${BuildConfig.APPLICATION_ID}.fileprovider")//设置文件提供者
                .setDirPath(Environment.getExternalStorageDirectory().absolutePath + "/" + BuildConfig.APPLICATION_ID)//设置文件存储路径
                .isCrop(true)//开启裁剪
                .setCompressQuality(100)//压缩质量[1,100]
                .setSize(120, 120)//裁剪尺寸
                .setOnFinishChooseAndCropImageListener { bitmap, file ->
                    //显示选好得图片
                    showLoadingDialog()
                    //上传头像
                    mPresenter.uploadAvatar(file)
                }
                .create()
    }

    private lateinit var mSexType : String

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.ll_avatar->{//头像
                PermissionUtils.permission(android.Manifest.permission.CAMERA,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission_group.STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).callback(object : PermissionUtils.SimpleCallback{
                    override fun onGranted() {
                        //被给予权限,调起选图弹窗
                        ChooseImageDialogWrapper(imageChooseHelper)
                                .showChooseImage()
                    }

                    override fun onDenied() {
                        toast(getString(R.string.tv_avater_detials))
                    }

                }).rationale { shouldRequest -> shouldRequest!!.again(true) }
                        .request()
            }
            R.id.ll_nickname->{//昵称
                val intent = Intent(mContext, SettingNicknameActivity::class.java)
                intent.putExtra("nickname",tv_nickname.text.toString().trim())
                startActivityForResult(intent,0)
            }
            R.id.ll_sex->{//性别
                val selectSexDialog = SelectSexDialog(mContext)
                selectSexDialog.setOnSelectSexListener {
                    showLoadingDialog()
                    mPresenter.alterSex(it)
                    mSexType=it
                    selectSexDialog.dismiss()
                }
                selectSexDialog.show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==0&&resultCode==SettingNicknameActivity.NICKNAME_RESULT_CODE){
            tv_nickname.text=data!!.getStringExtra("nickname")
            return
        }
        imageChooseHelper.onActivityResult(requestCode, resultCode, data)

    }

    override fun onAlterSexSuccess(msg: String?) {
        hideLoadingDialog()
        toast(msg!!)
        if (mSexType=="1"){
            tv_sex.text=getString(R.string.tv_man)
        }else if (mSexType=="2"){
            tv_sex.text=getString(R.string.tv_women)
        }else {
            tv_sex.text=getString(R.string.tv_secrecy)
        }

    }

    override fun onAlterSexFailure(err: String?) {
        hideLoadingDialog()
        toast(err!!)
    }

    override fun onUploadAvatarSuccess(data: UploadPriceEntity?) {
        hideLoadingDialog()
        iv_avatar.loadImg(mContext,API.HOST+data!!.new_avatar,R.mipmap.toubuone)
    }

    override fun onUploadAvatarFailure(err: String?) {
        hideLoadingDialog()
        toast(err!!)
    }



}