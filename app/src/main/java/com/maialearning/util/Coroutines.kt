package com.maialearning.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Coroutines {

    fun ioWorker(work: (suspend () -> Unit)) {
        CoroutineScope(Dispatchers.IO).launch {
            work()
        }
    }

    fun mainWorker(work: (suspend () -> Unit)) {
        CoroutineScope(Dispatchers.Main).launch {
            work()
        }
    }

}