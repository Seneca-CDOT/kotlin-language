// !LANGUAGE: +IntrinsicConstEvaluation
// TARGET_BACKEND: JVM_IR
// WITH_STDLIB

fun <T> T.id() = this

class A {
    val a = ""
    fun b() = ""

    init {
        println("A init")
    }

    fun test() {
        val a = A::a.<!EVALUATED("a")!>name<!>
        val b = A::b.<!EVALUATED("b")!>name<!>

        val c = ::A.<!EVALUATED("<init>")!>name<!>
        val d = this::a.<!EVALUATED("a")!>name<!>

        val e = A()::b.<!EVALUATED("b")!>name<!>
        val f = getA()::b.<!EVALUATED("b")!>name<!>

        val temp = A()
        val g = temp::b.<!EVALUATED("b")!>name<!>
        val insideStringConcat = "${temp::b.<!EVALUATED("b")!>name<!>}"

        val complexExpression1 = A()::a.<!EVALUATED("a")!>name<!> + A()::b.<!EVALUATED("b")!>name<!>
        val complexExpression2 = A::a.<!EVALUATED("a")!>name<!> <!EVALUATED("ab")!>+ A::b.<!EVALUATED("b")!>name<!><!>

        var recursive = ::test.<!EVALUATED("test")!>name<!>
    }

    fun getA(): A = A()
}

// STOP_EVALUATION_CHECKS
fun box(): String {
    A().test()
    return "OK"
}
