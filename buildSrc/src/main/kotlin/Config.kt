import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

object Config {
    const val API_ROOT = "\"https://www.anapioficeandfire.com/api/\""

}

fun Project.configAndroid() = this.extensions.getByType<BaseExtension>().run {
    compileSdkVersion(Versions.Android.sdk)
    defaultConfig {
        minSdk = Versions.Android.minSdk
        targetSdk = Versions.Android.sdk
        versionCode = Versions.App.versionCode
        versionName = Versions.App.versionName

        buildConfigField("String", "API_ROOT", Config.API_ROOT)

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}