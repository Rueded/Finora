package com.example.aiexpensetracker.ui

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.example.aiexpensetracker.R
import com.example.aiexpensetracker.utils.MonitoredAppsUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class InstalledAppInfo(
    val packageName: String,
    val label: String,
    val icon: Drawable?
)

/**
 * 🟢 新增：枚举手机上已安装、有桌面图标的 App。
 * 不需要 QUERY_ALL_PACKAGES 特殊权限，只要 Manifest 里声明了 <queries> 里的 MAIN/LAUNCHER intent 即可，
 * 也不会弹任何运行时权限请求框。
 */
private fun queryInstalledUserApps(context: Context): List<InstalledAppInfo> {
    val pm = context.packageManager
    val intent = Intent(Intent.ACTION_MAIN, null).addCategory(Intent.CATEGORY_LAUNCHER)
    val resolveInfos = pm.queryIntentActivities(intent, PackageManager.MATCH_ALL)

    return resolveInfos
        .map { it.activityInfo.applicationInfo }
        .distinctBy { it.packageName }
        .mapNotNull { appInfo: ApplicationInfo ->
            try {
                InstalledAppInfo(
                    packageName = appInfo.packageName,
                    label = pm.getApplicationLabel(appInfo).toString(),
                    icon = pm.getApplicationIcon(appInfo)
                )
            } catch (e: Exception) {
                null
            }
        }
        .sortedBy { it.label.lowercase() }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonitoredAppsScreen(onBack: () -> Unit) {
    val context = LocalContext.current

    var allApps by remember { mutableStateOf<List<InstalledAppInfo>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var monitored by remember { mutableStateOf(MonitoredAppsUtils.getMonitoredPackages(context)) }
    var searchText by remember { mutableStateOf("") }

    // 扫描已安装 App 比较吃 IO，丢到后台线程，避免卡主线程
    LaunchedEffect(Unit) {
        val list = withContext(Dispatchers.IO) { queryInstalledUserApps(context) }
        allApps = list
        isLoading = false
    }

    fun toggle(pkg: String, checked: Boolean) {
        val updated = if (checked) monitored + pkg else monitored - pkg
        monitored = updated
        MonitoredAppsUtils.setMonitoredPackages(context, updated)
    }

    val filteredApps = remember(allApps, searchText) {
        if (searchText.isBlank()) allApps
        else allApps.filter {
            it.label.contains(searchText, ignoreCase = true) ||
                it.packageName.contains(searchText, ignoreCase = true)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // 顶部说明卡片
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    stringResource(R.string.whitelist_intro_title),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    stringResource(R.string.whitelist_intro_body),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text(stringResource(R.string.whitelist_search_hint)) },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                singleLine = true
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = {
                MonitoredAppsUtils.resetToDefault(context)
                monitored = MonitoredAppsUtils.getMonitoredPackages(context)
            }) {
                Icon(Icons.Default.Refresh, contentDescription = stringResource(R.string.whitelist_reset_desc))
            }
        }

        Text(
            stringResource(R.string.whitelist_selected_count, monitored.size),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        if (isLoading) {
            Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filteredApps, key = { it.packageName }) { app ->
                    val isChecked = monitored.contains(app.packageName)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { toggle(app.packageName, !isChecked) }
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val bitmap = remember(app.packageName) {
                            app.icon?.let { runCatching { it.toBitmap(96, 96).asImageBitmap() }.getOrNull() }
                        }
                        if (bitmap != null) {
                            Image(
                                bitmap = bitmap,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                        } else {
                            Box(modifier = Modifier.size(36.dp))
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(app.label, style = MaterialTheme.typography.bodyMedium)
                            Text(
                                app.packageName,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Checkbox(checked = isChecked, onCheckedChange = { toggle(app.packageName, it) })
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}
