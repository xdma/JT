package com.shostak.jt.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RawModel(
    @SerialName("absolute_url")
    val absoluteUrl: String,
    @SerialName("content")
    val content: String,
    @SerialName("departments")
    val departments: List<Department>,
    @SerialName("id")
    val id: Long,
    @SerialName("internal_job_id")
    val internalJobId: Long,
    @SerialName("location")
    val location: Location,
    @SerialName("offices")
    val offices: List<Office>,
    @SerialName("title")
    val title: String,
    @SerialName("updated_at")
    val updatedAt: String
) {


    @Serializable
    data class Department(
        @SerialName("child_ids")
        val childIds: List<Long>?,
        @SerialName("id")
        val id: Long,
        @SerialName("name")
        val name: String,
        @SerialName("parent_id")
        val parentId: Long?
    )

    @Serializable
    data class Location(
        @SerialName("name")
        val name: String
    )

    @Serializable
    data class Office(
        @SerialName("child_ids")
        val childIds: List<Long>?,
        @SerialName("id")
        val id: Long,
        @SerialName("location")
        val location: String,
        @SerialName("name")
        val name: String,
        @SerialName("parent_id")
        val parentId: Long?
    )
}