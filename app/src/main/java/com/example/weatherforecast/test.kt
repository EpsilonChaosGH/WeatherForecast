package com.example.weatherforecast

@Volatile
var sharedCounter: Int = 0

class TestT{
    @Synchronized
    fun work(test: TestT){
        Thread.sleep(1000)
        println(this)
        test.work2(this)
        println("asd")
    }

    @Synchronized
    private fun work2(test: TestT){
        println(test)
    }
}

fun main() {

    val a = TestT()
    val b = TestT()

    Thread{
        a.work(b)
    }.apply {
        start()
    }

    Thread{
        b.work(a)
    }.apply {
        start()
        join()
    }



//    runBlocking {
//       // var counter: AtomicInteger = AtomicInteger(0)
//        var counter: Int = 0
//
//        @Synchronized
//        fun count(){
//            counter++
//        }
//
//        val scope = CoroutineScope(newFixedThreadPoolContext(4,"pool"))
//
//        var coroutines = 1.rangeTo(1000).map {
//            scope.launch {
//                for (i in 1..1000){
//                    count()
//                    sharedCounter++
//                }
//            }
//        }
//
//        coroutines.forEach { it.join() }
//
//        println(counter)
//        println(sharedCounter)
//
//
//    }
}

