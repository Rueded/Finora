<div align="center">

# Finora

*Every ringgit, quietly accounted for.*

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-blue.svg)](https://kotlinlang.org/)
[![Compose](https://img.shields.io/badge/Jetpack_Compose-Latest-green.svg)](https://developer.android.com/jetpack/compose)
[![AI Powered](https://img.shields.io/badge/Powered_by-Google_Gemini-orange.svg)](https://ai.google.dev/)
[![Platform](https://img.shields.io/badge/Platform-Android_10+-brightgreen.svg)]()

**[English](#english)** &nbsp;·&nbsp; **[中文](#中文)**

</div>

<br>

---

<a name="english"></a>

## English

Finora is a personal finance app built for how money actually moves in Malaysia — bank transfers, e-wallets, QR payments — without asking you to type a single transaction by hand. It reads the payment notification you already get, understands it with AI, and puts it in the right place before you've even opened the app.

### What it does

**Reads your notifications, not your mind.**
A three-layer detection pipeline — fingerprint matching, then AI classification, then a regex fallback — turns raw bank and e-wallet notifications into clean, categorized transactions. Deep support for TNG, GrabPay, Maybank, CIMB, Google Wallet, and more. You choose exactly which installed apps it's allowed to read from a plain checklist; nothing is monitored by default that you haven't approved yourself.

**Talks back.**
Yunnuo (云糯), Finora's built-in assistant, answers questions about your own spending in plain language — *"how much did I spend on food last week"*, *"show me everything from Grab this month"* — and can log a transaction the same way you'd casually mention it to a friend.

**Understands a receipt photo.**
Share a screenshot from any app, or snap a photo of a physical receipt, and Finora pulls out the merchant, line items, tax, and total on its own.

**Notices what repeats.**
Recurring charges — Netflix, Spotify, insurance — get flagged automatically as subscriptions, with a running due-date view instead of a surprise on your statement.

**Backs up on your terms.**
Everything lives in an encrypted local database first. Cloud backup is optional, encrypted, goes to your own Google Drive, and nowhere else.

### Built with

| | |
|---|---|
| UI | Jetpack Compose |
| Storage | Room (local, encrypted) |
| AI | Google Gemini — classification, OCR, chat |
| Cloud | Google Drive API, Firebase (Realtime Database, Authentication) |
| Architecture | MVVM · Coroutines · StateFlow |

<details>
<summary><strong>Release history</strong></summary>
<br>

| Version | Highlights |
|---|---|
| v5.2.3 / v5.2.0 | Improved AI detection for transactions |
| v5.1.0 | Share-to-Track, Smart Prediction, upgraded AI intent engine, Clone Mode |
| v3.4.x | Notification coverage expansion, model upgrades, background downloads |

Full changelog → [Releases](https://github.com/Rueded/Finora/releases)

</details>

### Status

Actively developed and used daily. Localized in English and Chinese, with detection tuned specifically for Malaysian banks and e-wallets.

### Feedback

This is a solo project, built by finding my own friction points and fixing them, with AI-assisted development throughout. Bug reports and feature ideas are welcome as an Issue or a PR.

<br>

---

<a name="中文"></a>

## 中文

Finora 是一款为大马用户打造的记账 App——银行转账、电子钱包、QR 支付，不用手动输入任何一笔账。它会读取你手机本来就会收到的付款通知，用 AI 理解内容，在你打开 App 之前就把账记在该在的地方。

### 功能

**读取通知，不用你操心。**
三层识别机制——先精确指纹匹配，再交给 AI 判断，最后有正则兜底——把原始的银行/钱包通知变成干净、已分类的账目。深度支持 TNG、GrabPay、Maybank、CIMB、Google Wallet 等。你可以从一个简单的勾选列表里，自己决定哪些已安装的 App 允许被读取；没有经过你同意的 App，默认不会被监听。

**会跟你对话。**
云糯是 Finora 内置的记账管家，能用大白话回答你关于消费的问题——"上周吃饭花了多少"、"这个月 Grab 的账单都有哪些"——也能像跟朋友随口说一句一样，直接帮你记一笔账。

**看得懂收据。**
在任意 App 里分享一张截图，或者直接拍一张实体收据，Finora 会自己提取出商户、明细项目、税费和总额。

**认得出重复扣款。**
Netflix、Spotify、保险这类周期性扣款，会被自动识别成订阅，有一个持续更新的到期日视图，不会等到账单出来才发现被扣钱。

**备份完全由你做主。**
所有数据首先都存在本地的加密数据库里。云端备份是可选的，加密后只会传到你自己的 Google Drive，不会去任何其他地方。

### 技术栈

| | |
|---|---|
| 界面 | Jetpack Compose |
| 本地存储 | Room（本地，加密） |
| AI | Google Gemini — 分类识别 / OCR / 对话 |
| 云端 | Google Drive API, Firebase（Realtime Database, Authentication） |
| 架构 | MVVM · Coroutines · StateFlow |

<details>
<summary><strong>版本历史</strong></summary>
<br>

| 版本 | 更新重点 |
|---|---|
| v5.2.3 / v5.2.0 | 更新 AI 识别消费信息 |
| v5.1.0 | 分享截图极速记账、智能习惯预测、AI 意图引擎升级、克隆模式 |
| v3.4.x | 扩大通知识别覆盖范围、模型升级、支持系统级后台下载 |

完整更新日志 → [Releases](https://github.com/Rueded/Finora/releases)

</details>

### 项目状态

持续开发中，日常在用。支持中英双语，识别逻辑专门针对马来西亚的银行与电子钱包做过调优。

### 反馈

这是一个人做的项目，来自我自己生活里遇到的真实痛点，开发过程中大量用了 AI 辅助。欢迎提 Issue 或 PR，报 bug 或者提功能建议都可以。

<br>

---

<div align="center">

*Built by 白开水.*

</div>
