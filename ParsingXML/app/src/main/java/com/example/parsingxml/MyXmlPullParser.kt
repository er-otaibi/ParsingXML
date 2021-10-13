package com.example.parsingxml

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream

class MyXmlPullParserHandler {
    private val studentList = ArrayList<Students>()
    private var text: String? = null

    private var studentName = ""
    private var studentGrade = 0f

    fun parse(inputStream: InputStream): List<Students> {
        try {
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setInput(inputStream, null)
            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagName = parser.name
                when (eventType) {
                    XmlPullParser.TEXT -> text = parser.text
                    XmlPullParser.END_TAG -> when {
                        tagName.equals("name", ignoreCase = true) -> {
                            studentName = text.toString()
                        }
                        tagName.equals("marks", ignoreCase = true) -> {
                            studentGrade = text!!.toFloat()
                            studentList.add(Students(studentName, studentGrade))
                        }
                    }
                }

                eventType = parser.next()
            }

        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return studentList
    }
}