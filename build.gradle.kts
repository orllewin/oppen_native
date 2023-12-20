plugins {
    kotlin("multiplatform") version "1.4.30"
}

group = "oppen"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val mingw64Path = File(System.getenv("MINGW64_DIR") ?: "C:/msys64/mingw64")
val mingw32Path = File(System.getenv("MINGW32_DIR") ?: "C:/msys64/mingw32")
val kotlinNativeDataPath = System.getenv("KONAN_DATA_DIR")?.let { File(it) } ?: File(System.getProperty("user.home")).resolve(".konan")

kotlin {
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "main"

                when (preset) {
                    presets["macosX64"] -> linkerOpts("-L/opt/local/lib", "-L/usr/local/lib", "-lSDL2")
                    presets["linuxX64"] -> linkerOpts("-L/usr/lib64", "-L/usr/lib/x86_64-linux-gnu", "-lSDL2")
                    presets["mingwX64"] -> linkerOpts(
                        "-L${mingw64Path.resolve("lib")}",
                        "-Wl,-Bstatic",
                        "-lstdc++",
                        "-static",
                        "-lSDL2",
                        "-limm32",
                        "-lole32",
                        "-loleaut32",
                        "-lversion",
                        "-lwinmm",
                        "-lsetupapi",
                        "-mwindows"
                    )
                    presets["mingwX86"] -> linkerOpts(
                        "-L${mingw32Path.resolve("lib")}",
                        "-Wl,-Bstatic",
                        "-lstdc++",
                        "-static",
                        "-lSDL2",
                        "-limm32",
                        "-lole32",
                        "-loleaut32",
                        "-lversion",
                        "-lwinmm",
                        "-lsetupapi",
                        "-mwindows"
                    )
                    presets["linuxArm32Hfp"] -> linkerOpts("-lSDL2")
                }
            }
        }

        compilations["main"].cinterops {
            val sdl by creating {
                when (preset) {
                    presets["macosX64"] -> includeDirs("/opt/local/include/SDL2", "/usr/local/include/SDL2")
                    presets["linuxX64"] -> includeDirs("/usr/include", "/usr/include/x86_64-linux-gnu", "/usr/include/SDL2")
                    presets["mingwX64"] -> includeDirs(mingw64Path.resolve("include/SDL2"))
                    presets["mingwX86"] -> includeDirs(mingw32Path.resolve("include/SDL2"))
                    presets["linuxArm32Hfp"] -> includeDirs(kotlinNativeDataPath.resolve("dependencies/target-sysroot-2-raspberrypi/usr/include/SDL2"))
                }
            }
        }

        compilations["main"].enableEndorsedLibs = true
    }

    sourceSets {
        val nativeMain by getting
        val nativeTest by getting
    }
}


