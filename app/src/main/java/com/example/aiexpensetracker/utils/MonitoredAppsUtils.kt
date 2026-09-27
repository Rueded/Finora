package com.example.aiexpensetracker.utils

import android.content.Context

/**
 * 🟢 新增：用户可自定义的"监听 App 白名单"
 *
 * 原本 TARGET_PACKAGES 是写死在 NotificationListener.kt 里的固定包名集合，
 * 银行改包名 / 用户装的是别的地区版本 App 时，代码完全没法感知，只能等用户反馈"收不到"再手动加。
 *
 * 现在改成：用户可以在设置里自己勾选要监听哪些已安装的 App，存进 SharedPreferences。
 * 首次运行时会用原来的硬编码列表（DEFAULT_TARGET_PACKAGES）预填，保证老用户无感升级、不用重新设置。
 */
object MonitoredAppsUtils {

    private const val PREFS_NAME = "ai_tracker_prefs"
    private const val KEY_MONITORED_PACKAGES = "monitored_packages"

    // 🟢 原 NotificationListener.kt 里的 TARGET_PACKAGES，搬到这里当"预置默认值"
    val DEFAULT_TARGET_PACKAGES: Set<String> = setOf(
        "my.com.tngdigital.ewallet", "com.grabtaxi.passenger", "com.shopee.my", "com.shopeepay.my",
        "my.com.myboost", "com.airasia.bigpay", "com.maybank2u.life", "com.cimb.octo",
        "com.cimb.clicks.android", "my.com.rhbgroup.mobilebanking", "my.com.rhbgroup.rhbmobilebanking",
        "com.rhbgroup.rhbengineering", "com.rhbgroup.rhbmobilebanking", "com.hongleong.pb",
        "my.com.mybsn", "com.mybsn.mobile", "net.mybsn.secure", "com.ambank.ambank",
        "my.com.publicbank.pbe", "com.alliancebank.allianceonline", "com.bankislam.go",
        "com.bankrakyat.irakyat", "com.sc.breeze.my", "my.com.hsbc.hsbcmobilebanking",
        "com.ocbc.mobile", "com.uob.mighty.my", "com.maybank2u.m2u", "com.cimb.cimbocto",
        "com.cimbmalaysia", "my.com.rhb.mobilebanking", "my.com.hongleongconnect.mobile",
        "com.publicbank.pbengage", "com.ambank.ambankonline", "com.ambank.amonline",
        "com.alliance.online.mobile", "com.bankislam.bimbmobile", "my.com.bankrakyat.irakyat",
        "com.affinbank.affinalways", "com.affinonline.rib", "com.uob.tmrw.my", "com.ocbc.my",
        "com.ocbc.mobilebanking.my", "hk.com.hsbc.hsbcmalaysia", "com.htsu.hsbcpersonalbanking",
        "com.standardchartered.breeze.my", "my.gxbank.my", "my.com.aeonbank.app",
        "com.bankislam.beu", "com.alrajhi.rize", "com.tpa.airasiacard", "com.setel.mobile",
        "com.aeoncredit.wallet.my", "com.lazada.android", "com.google.android.apps.walletnfcrel",
        "com.samsung.android.spay", "com.paypal.android.p2pmobile", "com.transferwise.android",
        "com.eg.android.AlipayGphone"
    )

    /**
     * 读取当前"正在监听"的包名集合。
     * 如果用户从没设置过（老用户升级 / 全新安装），会自动用 DEFAULT_TARGET_PACKAGES 初始化一次并存下来，
     * 保证行为跟升级前一致，不需要用户手动重新勾选。
     */
    fun getMonitoredPackages(context: Context): Set<String> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val saved = prefs.getStringSet(KEY_MONITORED_PACKAGES, null)
        if (saved != null) return saved

        // 第一次运行：用默认列表初始化
        prefs.edit().putStringSet(KEY_MONITORED_PACKAGES, DEFAULT_TARGET_PACKAGES).apply()
        return DEFAULT_TARGET_PACKAGES
    }

    /** 保存用户勾选后的完整包名集合（每次改动都是整份覆盖写入） */
    fun setMonitoredPackages(context: Context, packages: Set<String>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        // SharedPreferences 的 StringSet 有个经典坑：必须存一份新的 Set 实例，
        // 直接改后原地 apply 不一定会被判定为"变了"，这里用 toMutableSet() 保险
        prefs.edit().putStringSet(KEY_MONITORED_PACKAGES, packages.toMutableSet()).apply()
    }

    /** 一键恢复成预置银行列表（设置页里的"恢复默认"按钮用） */
    fun resetToDefault(context: Context) {
        setMonitoredPackages(context, DEFAULT_TARGET_PACKAGES)
    }
}
