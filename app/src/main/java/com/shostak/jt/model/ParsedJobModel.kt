package com.shostak.jt.model


import com.shostak.jt.util.myFromHtml
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.lang.StringBuilder

@Serializable
data class ParsedJobModel(
    @SerialName("department_name")
    val departmentName: String,
    @SerialName("description")
    val description: Description,
    @SerialName("role_name")
    val roleName: String,
    @SerialName("locationName")
    val locationName: String

    ) {
    @Serializable
    data class Description(
        @SerialName("main_text")
        var mainText: String = "",
        @SerialName("sections")
        val sections: List<Section>? = null
    ) {
        override fun equals(other: Any?): Boolean {
            return other is Description
//                    this.mainText == other.mainText
                    && this.sections?.size == other.sections?.size
        }

        @Serializable
        data class Section(
            @SerialName("bullets")
            var bullets: List<String>? = null,
            @SerialName("title")
            val title: String = ""
        )
    }


    override fun equals(other: Any?): Boolean {
        return other is ParsedJobModel &&
                this.departmentName == other.departmentName &&
                this.description == other.description &&
                this.roleName == other.roleName
    }
}