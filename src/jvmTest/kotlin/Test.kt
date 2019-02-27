package io.skerna.lbase

import org.junit.Assert.assertThat
import java.util.concurrent.TimeUnit
import java.util.concurrent.Executors

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.Test


class Test{
    private val NUM_THREADS = 10
    private val NUM_ITERATIONS = 5000

    @Test
    fun test(){
        val executor = Executors.newFixedThreadPool(NUM_THREADS)
        val sync = Counter()
        val notSync = Counter()

        for (i in 0 until NUM_THREADS) {
            executor.submit {
                for (i in 0 until NUM_ITERATIONS) {
                    sync.incSync()
                    notSync.inc()
                }
            }
        }

        println("Result Value sync: " + sync.value)
        println("Result Value no sync: " + notSync.value)
        executor.shutdown()
        executor.awaitTermination(5, TimeUnit.SECONDS)
        assertThat(sync.value, `is`(NUM_THREADS * NUM_ITERATIONS))
        assertThat(notSync.value, `is`(not(NUM_THREADS * NUM_ITERATIONS)))
    }


    private class Counter {
        var value = 0
            private set

        fun incSync() {
            synchronized(this){
                value++
            }
        }

        fun inc() {
            value++
        }
    }
}