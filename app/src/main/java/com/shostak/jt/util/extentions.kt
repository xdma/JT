package com.shostak.jt.util

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.shostak.jt.BuildConfig
import com.shostak.jt.model.ParsedJobModel
import com.shostak.jt.model.RawModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.lang.StringBuilder

fun log(str: Any?) {
    if (!BuildConfig.DEBUG) return
    Log.i("jt_tag", str.toString())
}

fun jsonSerializer() = Json(JsonConfiguration.Stable.copy(ignoreUnknownKeys = true))


fun myFromHtml(htmlString: String): Spanned =
    Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY)

//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//        Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY)
//    } else {
//        @Suppress("DEPRECATION")
//        Html.fromHtml(htmlString)
//    }


fun List<RawModel>.parseRawModelList(): List<ParsedJobModel> {
    val parsedJobsList = mutableListOf<ParsedJobModel>()
    this.forEach { rawJob ->
        val content = myFromHtml(rawJob.content).toString()
        val doc: Document? = Jsoup.parse(content)
        val sectionTitles: Elements = doc?.getElementsByTag("strong") ?: return@forEach
        val sectionParagraph: Elements = doc.getElementsByTag("p")
        val sectionBullets: Elements = doc.getElementsByTag("ul")
        val mainTextBuilder = StringBuilder()

        if (sectionParagraph.size > sectionBullets.size) {
            sectionParagraph
                .asSequence()
                .filterIndexed { index, p -> index < sectionParagraph.size - sectionBullets.size }
                .forEach { p ->
                    mainTextBuilder.append(p.toString())
                }
        }

        val sections = mutableListOf<ParsedJobModel.Description.Section>()

        //checking if number of titles (found by <strong> tag) equal or greater that number of ul li's
        if (sectionTitles.size >= sectionBullets.size) {
            for (i in sectionBullets.size - 1 downTo 0) {
                val bullets: Elements = sectionBullets[i].select("li")
                val section = ParsedJobModel.Description.Section(
                    title = sectionTitles[sectionTitles.size - 1 - i].text()
                )
                val bulletsList = mutableListOf<String>()
                bullets.forEach { li ->
                    bulletsList.add(li.text())
                }
                section.bullets = bulletsList
                sections.add(section)
            }
        }

        val jobModel = ParsedJobModel(
            departmentName = rawJob.departments.first().name,
            roleName = rawJob.title,
            description = ParsedJobModel.Description(
                sections = sections,
                mainText = mainTextBuilder.toString()
            ),
            locationName = rawJob.location.name
        )

        parsedJobsList.add(jobModel)
    }

    return parsedJobsList
}


fun springAnimation(
    view: View?,
    property: DynamicAnimation.ViewProperty,
    stiffnessVal: Float,
    dampingRatioVal: Float
): SpringAnimation {
    return SpringAnimation(view, property).setSpring(SpringForce().apply {
        stiffness = stiffnessVal
        dampingRatio = dampingRatioVal
    })
}


fun Float.toPx(context: Context) = (this * context.resources.displayMetrics.scaledDensity + 0.5F)
