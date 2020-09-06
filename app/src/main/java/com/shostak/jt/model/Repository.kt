package com.shostak.jt.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import com.shostak.jt.network.JobsAPI
import com.shostak.jt.util.jsonSerializer
import com.shostak.jt.util.log
import com.shostak.jt.util.parseRawModelList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.list
import org.json.JSONArray


class Repository {

    private var jobs: List<ParsedJobModel>? = null
    private val jobsLiveData: MutableLiveData<List<ParsedJobModel>> = MutableLiveData()


    suspend fun loadJobs() = liveData(Dispatchers.IO) {
        if (jobsLiveData.value != null) {
            emitSource(jobsLiveData)
            return@liveData
        }

        val (_, _, result) =
            Fuel.request(JobsAPI.LoadJobsWIthContent())
                .responseJson()

        result.fold(
            success = {
                val jobsJsonArray = it.obj().optJSONArray("jobs")
                jobsJsonArray ?: return@fold //TODO handle error

                val rawModelList = jsonSerializer().parse(
                    RawModel.serializer().list,
                    jobsJsonArray.toString()
                )

                val parsedList = coroutineScope {
                    async {
                        rawModelList.parseRawModelList()
                    }
                }

                jobs = parsedList.await()
                jobsLiveData.postValue(jobs)
                emitSource(jobsLiveData)
            },

            failure = {
                log(it)
                //TODO handle error
            })
    }


    fun getItem(position: Int): ParsedJobModel {
        return jobsLiveData.value!![position]
    }


    suspend fun runTextSearch(query: String) = withContext(Dispatchers.IO) {
        jobs ?: return@withContext

        val result =
            jobs!!.asSequence()
                .map { it }
                .filter {
                    it.roleName.contains(query, ignoreCase = true)
                            || it.departmentName.contains(query, ignoreCase = true)
                            || it.description.mainText.contains(query, ignoreCase = true)
                            || it.description.sections?.find { sec ->
                        sec.title.contains(query, ignoreCase = true)
                                || sec.bullets?.any { bul ->
                            bul.contains(
                                query,
                                ignoreCase = true
                            )
                        } != false
                    } != null
                }.toList()

        jobsLiveData.postValue(result)
    }


    fun getFormattedJson(): JSONArray? {
        jobs ?: return null
        val serializedToJsonString =
            jsonSerializer().stringify(ParsedJobModel.serializer().list, jobs!!)
        return JSONArray(serializedToJsonString)
    }

}


