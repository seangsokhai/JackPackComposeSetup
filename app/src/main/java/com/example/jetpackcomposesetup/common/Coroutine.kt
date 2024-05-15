package com.example.jetpackcomposesetup.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private var job: Job? = null
fun CoroutineScope.launchCancelDelay(delayDuration: Long = 200L, block: () -> Unit) {
    job?.cancel()
    job = launch {
        delay(delayDuration)
        block()
    }
}