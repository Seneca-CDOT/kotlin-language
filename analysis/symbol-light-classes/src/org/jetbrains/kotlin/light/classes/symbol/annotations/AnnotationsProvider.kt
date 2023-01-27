/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.light.classes.symbol.annotations

import org.jetbrains.kotlin.analysis.api.annotations.KtAnnotationApplicationInfo
import org.jetbrains.kotlin.analysis.api.annotations.KtAnnotationApplicationWithArgumentsInfo
import org.jetbrains.kotlin.name.ClassId

internal sealed interface AnnotationsProvider {
    fun annotationInfos(): List<KtAnnotationApplicationInfo>
    operator fun get(classId: ClassId): Collection<KtAnnotationApplicationWithArgumentsInfo>
    operator fun contains(classId: ClassId): Boolean
    fun isTheSameAs(other: Any?): Boolean
    fun ownerClassId(): ClassId?
}
