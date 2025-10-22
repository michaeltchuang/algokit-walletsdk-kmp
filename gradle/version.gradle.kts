import java.io.ByteArrayOutputStream

// Shared version calculation functions
extra["calculateVersionCode"] = fun(): Int {
    val libs = the<org.gradle.accessors.dm.LibrariesForLibs>()
    val calendarQuarter: Int = libs.versions.algokit.walletsdk.calendar.quarter.get().toInt()
    val githubRunNumber: Int = System.getenv("GITHUB_RUN_NUMBER")?.toIntOrNull() ?: libs.versions.android.versionCode.get().toInt()
    print("Version code: $githubRunNumber")
    return if (calendarQuarter > githubRunNumber)
        githubRunNumber
    else
        libs.versions.android.versionCode.get().toInt()
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
