package com.debanshu.xcalendar

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.debanshu.xcalendar.di.initKoin
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin()
    ComposeViewport(document.body!!) {
        CalendarApp()
    }
}
