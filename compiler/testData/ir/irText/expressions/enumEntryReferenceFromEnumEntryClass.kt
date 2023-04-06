// IGNORE_BACKEND: JS_IR
// IGNORE_BACKEND: JS_IR_ES6

// SKIP_SIGNATURE_DUMP
// ^ Types of properties of anonymous classes generated by K1 and K2 are different

enum class MyEnum {
    Z {
        var counter = 0
        fun foo() {}

        fun bar() {
            counter = 1
            foo()
        }

        val aLambda = {
            counter = 1
            foo()
        }

        val anObject = object {
            init {
                counter = 1
                foo()
            }

            fun test() {
                counter = 1
                foo()
            }
        }
    }
}
