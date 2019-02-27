/*
 * Copyright (c)  2019  SKERNA
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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