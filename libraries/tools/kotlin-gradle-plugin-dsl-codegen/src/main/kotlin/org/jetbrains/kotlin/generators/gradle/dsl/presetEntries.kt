/*
 * Copyright 2010-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.generators.gradle.dsl

import org.jetbrains.kotlin.generators.gradle.dsl.NativeFQNames.Presets
import org.jetbrains.kotlin.generators.gradle.dsl.NativeFQNames.Targets
import org.jetbrains.kotlin.konan.target.HostManager
import org.jetbrains.kotlin.konan.target.KonanTarget
import org.jetbrains.kotlin.konan.target.presetName

internal class KotlinPresetEntry(
    val presetName: String,
    val presetType: TypeName,
    val targetType: TypeName,
    val deprecation: String? = null
)

internal fun KotlinPresetEntry.typeNames(): Set<TypeName> = setOf(presetType, targetType)

internal const val MPP_PACKAGE = "org.jetbrains.kotlin.gradle.plugin.mpp"

internal object NativeFQNames {
    object Targets {
        const val base = "$MPP_PACKAGE.KotlinNativeTarget"
        const val withHostTests = "$MPP_PACKAGE.KotlinNativeTargetWithHostTests"
        const val withSimulatorTests = "$MPP_PACKAGE.KotlinNativeTargetWithSimulatorTests"
    }

    object Presets {
        const val simple = "$MPP_PACKAGE.KotlinNativeTargetPreset"
        const val withHostTests = "$MPP_PACKAGE.KotlinNativeTargetWithHostTestsPreset"
        const val withSimulatorTests = "$MPP_PACKAGE.KotlinNativeTargetWithSimulatorTestsPreset"
    }
}

// Note: modifying these sets should also be reflected in the MPP plugin code, see 'setupDefaultPresets'
private val nativeTargetsWithHostTests =
    setOf(KonanTarget.LINUX_X64, KonanTarget.MACOS_X64, KonanTarget.MACOS_ARM64, KonanTarget.MINGW_X64)
private val nativeTargetsWithSimulatorTests =
    setOf(
        KonanTarget.IOS_X64,
        KonanTarget.IOS_SIMULATOR_ARM64,

        KonanTarget.WATCHOS_X86,
        KonanTarget.WATCHOS_X64,
        KonanTarget.WATCHOS_SIMULATOR_ARM64,

        KonanTarget.TVOS_X64,
        KonanTarget.TVOS_SIMULATOR_ARM64
    )

internal val nativePresetEntries = HostManager().targets
    .map { (_, target) ->

        val (presetType, targetType) = when (target) {
            in nativeTargetsWithHostTests ->
                Presets.withHostTests to Targets.withHostTests
            in nativeTargetsWithSimulatorTests ->
                Presets.withSimulatorTests to Targets.withSimulatorTests
            else ->
                Presets.simple to Targets.base
        }
        val deprecation = "@Deprecated(DEPRECATED_TARGET_MESSAGE, level = DeprecationLevel.ERROR)".takeIf { target in KonanTarget.deprecatedTargets }

        KotlinPresetEntry(target.presetName, typeName(presetType), typeName(targetType), deprecation)
    }
