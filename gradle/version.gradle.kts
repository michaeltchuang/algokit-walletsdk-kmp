import java.io.ByteArrayOutputStream

// Shared version calculation functions
extra["calculateVersionCode"] = fun(): Int {
    val libs = the<org.gradle.accessors.dm.LibrariesForLibs>()
    val calendarQuarter: Int = libs.versions.algokit.walletsdk.calendar.quarter.get().toInt()
    val offset: Int = libs.versions.android.versionCode.offset.get().toInt()
    val githubRunNumber: Int = System.getenv("GITHUB_RUN_NUMBER")?.toIntOrNull() ?: 0
    return if (calendarQuarter > githubRunNumber)
        githubRunNumber + offset
    else
        offset
}

extra["calculateVersionName"] = fun(): String {
    val libs = the<org.gradle.accessors.dm.LibrariesForLibs>()
    val algoKitVersion: Int = libs.versions.algokit.version.get().toInt()
    val calendarQuarter: Int = libs.versions.algokit.walletsdk.calendar.quarter.get().toInt()
    val releaseNumber: Int = libs.versions.algokit.walletsdk.quarter.release.number.get().toInt()
    return "$algoKitVersion.$calendarQuarter.$releaseNumber"
}

extra["getGitHash"] = fun(): String {
    val stdout = ByteArrayOutputStream()
    project.exec {
        commandLine("git", "rev-parse", "--short", "HEAD")
        standardOutput = stdout
    }
    return stdout.toString().trim()
}
