// This test is similar to kt42397, just everything is doubled: in "package knlibrary.subpackage" and the root package

// FILE: SecondLevelPackage.kt
package knlibrary.subpackage

import kotlin.native.Platform

// The following 2 singletons are unused. However, since we are generating C bindings for them,
// they should be marked as used, so that the code generator emits their deinitialization.

object A {}

class B {
    companion object {}
}

fun enableMemoryChecker() {
    Platform.isMemoryLeakCheckerActive = true
}

// FILE: rootPackage.kt
object A {}

class B {
    companion object {}
}

fun enableMemoryChecker() {
    Platform.isMemoryLeakCheckerActive = true
}
