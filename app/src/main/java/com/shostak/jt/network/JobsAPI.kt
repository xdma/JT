package com.shostak.jt.network

import com.github.kittinunf.fuel.core.HeaderValues
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Parameters
import com.github.kittinunf.fuel.util.FuelRouting

sealed class JobsAPI : FuelRouting {

    override val basePath =
        "https://boards-api.greenhouse.io/v1"

    class LoadJobsWIthContent() : JobsAPI()

    override val method: Method
        get() {
            return when (this) {
                is LoadJobsWIthContent -> Method.GET
            }
        }

    override val path: String
        get() {
            return when (this) {
                is LoadJobsWIthContent -> "/boards/joytunes/jobs"
            }
        }

    override val params: Parameters?
        get() {
            return when (this) {
                is LoadJobsWIthContent -> listOf("content" to true)
            }
        }

    override val body: String?
        get() {
            return when (this) {
                is LoadJobsWIthContent -> null
            }
        }


    override val bytes: ByteArray?
        get() = null

    override val headers: Map<String, HeaderValues>?
        get() = null
//        mapOf(
//            "Content-Type" to listOf("application/json")
//        )
}