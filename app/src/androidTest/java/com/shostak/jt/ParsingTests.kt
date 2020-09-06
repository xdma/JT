package com.shostak.jt

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shostak.jt.model.RawModel
import com.shostak.jt.util.jsonSerializer
import com.shostak.jt.util.parseRawModelList
import kotlinx.serialization.builtins.list
import org.json.JSONObject

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ParsingTests {

    @Test
    fun checkParsingOneItemTest() {
        val rawJson =
            "{\"jobs\":[{\"absolute_url\":\"https://www.joytunes.com/careers?gh_jid=4723773002\",\"data_compliance\":[{\"type\":\"gdpr\",\"requires_consent\":false,\"retention_period\":null}],\"internal_job_id\":4554142002,\"location\":{\"name\":\"Tel Aviv\"},\"metadata\":[],\"id\":4723773002,\"updated_at\":\"2020-07-29T13:19:31-04:00\",\"requisition_id\":null,\"title\":\"DSP/ML Algorithms Engineer\",\"content\":\"&lt;p&gt;&lt;strong&gt;About us:&lt;/strong&gt;&lt;/p&gt;\\n&lt;p&gt;&lt;span style=&quot;font-weight: 400;&quot;&gt;JoyTunes is reinventing the way we learn, play and experience music, making it possible for anyone to play a musical instrument. With our top grossing piano apps and the recently launched guitar learning app, JoyTunes is on the fast track to building a global consumer subscription company, becoming the music education center of every household worldwide across instruments and demographics. With over a million monthly downloads and hundreds of thousands of daily learners around the world, we are proud to be helping everyone achieve their musical dreams.&amp;nbsp;&lt;/span&gt;&lt;/p&gt;\\n&lt;p&gt;&lt;strong&gt;About the position:&lt;/strong&gt;&lt;/p&gt;\\n&lt;p&gt;&lt;span style=&quot;font-weight: 400;&quot;&gt;The base of all JoyTunes products is our acoustic recognition engines, which allow us to give our users real-time feedback on their musical playing. In this role, you’ll be delivering an order of magnitude improvement in our current capabilities. You will have full, end-to-end responsibility for cutting edge algorithms, signal processing solutions, and machine learning systems. You will lead the way from idea, through research and development, to &lt;/span&gt;&lt;span style=&quot;font-weight: 400;&quot;&gt;implementation in JoyTunes products, directly shaping the experience of millions of users.&lt;/span&gt;&lt;/p&gt;\\n&lt;p&gt;&lt;strong&gt;About you:&lt;/strong&gt;&lt;/p&gt;\\n&lt;ul&gt;\\n&lt;li style=&quot;font-weight: 400;&quot;&gt;&lt;span style=&quot;font-weight: 400;&quot;&gt;Love to tinker with signals :)&lt;/span&gt;&lt;/li&gt;\\n&lt;li style=&quot;font-weight: 400;&quot;&gt;&lt;span style=&quot;font-weight: 400;&quot;&gt;BSc Electrical Engineering / &lt;/span&gt;&lt;span style=&quot;font-weight: 400;&quot;&gt;Physics / &lt;/span&gt;&lt;span style=&quot;font-weight: 400;&quot;&gt;Computer Science or other relevant degree&lt;/span&gt;&lt;/li&gt;\\n&lt;li style=&quot;font-weight: 400;&quot;&gt;&lt;span style=&quot;font-weight: 400;&quot;&gt;3+ years’ experience applying algorithms / signal processing to real world problems&lt;/span&gt;&lt;/li&gt;\\n&lt;li style=&quot;font-weight: 400;&quot;&gt;&lt;span style=&quot;font-weight: 400;&quot;&gt;Good problem solving and analytical abilities&lt;/span&gt;&lt;/li&gt;\\n&lt;li style=&quot;font-weight: 400;&quot;&gt;&lt;span style=&quot;font-weight: 400;&quot;&gt;Fast learner, willing to try things out of your comfort zone&lt;/span&gt;&lt;/li&gt;\\n&lt;li style=&quot;font-weight: 400;&quot;&gt;&lt;span style=&quot;font-weight: 400;&quot;&gt;Team player, with strong verbal and written communication skills&lt;/span&gt;&lt;/li&gt;\\n&lt;li style=&quot;font-weight: 400;&quot;&gt;&lt;span style=&quot;font-weight: 400;&quot;&gt;Machine learning experience - Advantage&lt;/span&gt;&lt;/li&gt;\\n&lt;li style=&quot;font-weight: 400;&quot;&gt;&lt;span style=&quot;font-weight: 400;&quot;&gt;Love music (who doesn’t) - Advantage&lt;/span&gt;&lt;/li&gt;\\n&lt;/ul&gt;\\n&lt;h4&gt;&lt;br&gt;&lt;br&gt;&lt;/h4&gt;\",\"departments\":[{\"id\":4013786002,\"name\":\"Dev\",\"child_ids\":[],\"parent_id\":null}],\"offices\":[]}],\"meta\":{\"total\":7}}"
        val jobsJsonArray = JSONObject(rawJson).optJSONArray("jobs")

        val rawModelList = jsonSerializer().parse(
            RawModel.serializer().list,
            jobsJsonArray!!.toString()
        )
        val parsedList = rawModelList.parseRawModelList()
        assertEquals(1, parsedList.size)
    }


    @Test
    fun checkParsing_8_Bullets() {
        val rawJson =
            "{\"jobs\":[{\"absolute_url\":\"https://www.joytunes.com/careers?gh_jid=4723773002\",\"data_compliance\":[{\"type\":\"gdpr\",\"requires_consent\":false,\"retention_period\":null}],\"internal_job_id\":4554142002,\"location\":{\"name\":\"Tel Aviv\"},\"metadata\":[],\"id\":4723773002,\"updated_at\":\"2020-07-29T13:19:31-04:00\",\"requisition_id\":null,\"title\":\"DSP/ML Algorithms Engineer\",\"content\":\"&lt;p&gt;&lt;strong&gt;About us:&lt;/strong&gt;&lt;/p&gt;\\n&lt;p&gt;&lt;span style=&quot;font-weight: 400;&quot;&gt;JoyTunes is reinventing the way we learn, play and experience music, making it possible for anyone to play a musical instrument. With our top grossing piano apps and the recently launched guitar learning app, JoyTunes is on the fast track to building a global consumer subscription company, becoming the music education center of every household worldwide across instruments and demographics. With over a million monthly downloads and hundreds of thousands of daily learners around the world, we are proud to be helping everyone achieve their musical dreams.&amp;nbsp;&lt;/span&gt;&lt;/p&gt;\\n&lt;p&gt;&lt;strong&gt;About the position:&lt;/strong&gt;&lt;/p&gt;\\n&lt;p&gt;&lt;span style=&quot;font-weight: 400;&quot;&gt;The base of all JoyTunes products is our acoustic recognition engines, which allow us to give our users real-time feedback on their musical playing. In this role, you’ll be delivering an order of magnitude improvement in our current capabilities. You will have full, end-to-end responsibility for cutting edge algorithms, signal processing solutions, and machine learning systems. You will lead the way from idea, through research and development, to &lt;/span&gt;&lt;span style=&quot;font-weight: 400;&quot;&gt;implementation in JoyTunes products, directly shaping the experience of millions of users.&lt;/span&gt;&lt;/p&gt;\\n&lt;p&gt;&lt;strong&gt;About you:&lt;/strong&gt;&lt;/p&gt;\\n&lt;ul&gt;\\n&lt;li style=&quot;font-weight: 400;&quot;&gt;&lt;span style=&quot;font-weight: 400;&quot;&gt;Love to tinker with signals :)&lt;/span&gt;&lt;/li&gt;\\n&lt;li style=&quot;font-weight: 400;&quot;&gt;&lt;span style=&quot;font-weight: 400;&quot;&gt;BSc Electrical Engineering / &lt;/span&gt;&lt;span style=&quot;font-weight: 400;&quot;&gt;Physics / &lt;/span&gt;&lt;span style=&quot;font-weight: 400;&quot;&gt;Computer Science or other relevant degree&lt;/span&gt;&lt;/li&gt;\\n&lt;li style=&quot;font-weight: 400;&quot;&gt;&lt;span style=&quot;font-weight: 400;&quot;&gt;3+ years’ experience applying algorithms / signal processing to real world problems&lt;/span&gt;&lt;/li&gt;\\n&lt;li style=&quot;font-weight: 400;&quot;&gt;&lt;span style=&quot;font-weight: 400;&quot;&gt;Good problem solving and analytical abilities&lt;/span&gt;&lt;/li&gt;\\n&lt;li style=&quot;font-weight: 400;&quot;&gt;&lt;span style=&quot;font-weight: 400;&quot;&gt;Fast learner, willing to try things out of your comfort zone&lt;/span&gt;&lt;/li&gt;\\n&lt;li style=&quot;font-weight: 400;&quot;&gt;&lt;span style=&quot;font-weight: 400;&quot;&gt;Team player, with strong verbal and written communication skills&lt;/span&gt;&lt;/li&gt;\\n&lt;li style=&quot;font-weight: 400;&quot;&gt;&lt;span style=&quot;font-weight: 400;&quot;&gt;Machine learning experience - Advantage&lt;/span&gt;&lt;/li&gt;\\n&lt;li style=&quot;font-weight: 400;&quot;&gt;&lt;span style=&quot;font-weight: 400;&quot;&gt;Love music (who doesn’t) - Advantage&lt;/span&gt;&lt;/li&gt;\\n&lt;/ul&gt;\\n&lt;h4&gt;&lt;br&gt;&lt;br&gt;&lt;/h4&gt;\",\"departments\":[{\"id\":4013786002,\"name\":\"Dev\",\"child_ids\":[],\"parent_id\":null}],\"offices\":[]}],\"meta\":{\"total\":7}}"
        val jobsJsonArray = JSONObject(rawJson).optJSONArray("jobs")

        val rawModelList = jsonSerializer().parse(
            RawModel.serializer().list,
            jobsJsonArray!!.toString()
        )
        val parsedList = rawModelList.parseRawModelList()
        assertEquals(8, parsedList.first().description.sections!!.first().bullets!!.size)
    }

    @Test
    fun checkParsing_No_Bullets() {
        val rawJson =
            "{\"jobs\":[{\"absolute_url\":\"https://www.joytunes.com/careers?gh_jid=4723773002\",\"data_compliance\":[{\"type\":\"gdpr\",\"requires_consent\":false,\"retention_period\":null}],\"internal_job_id\":4554142002,\"location\":{\"name\":\"Tel Aviv\"},\"metadata\":[],\"id\":4723773002,\"updated_at\":\"2020-07-29T13:19:31-04:00\",\"requisition_id\":null,\"title\":\"DSP/ML Algorithms Engineer\",\"content\":\"\",\"departments\":[{\"id\":4013786002,\"name\":\"Dev\",\"child_ids\":[],\"parent_id\":null}],\"offices\":[]}],\"meta\":{\"total\":7}}"
        val jobsJsonArray = JSONObject(rawJson).optJSONArray("jobs")

        val rawModelList = jsonSerializer().parse(
            RawModel.serializer().list,
            jobsJsonArray!!.toString()
        )
        val parsedList = rawModelList.parseRawModelList()
        assertEquals(0, parsedList.first().description.sections?.size ?: 0)
    }
}