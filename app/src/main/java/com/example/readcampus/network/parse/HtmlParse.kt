package com.example.readcampus.network.parse

import com.example.readcampus.bean.BookBean
import com.example.readcampus.bean.ChapterBean
import com.example.readcampus.network.HtmlService
import com.example.readcampus.network.ResponseSearchPageByKeyword
import com.example.readcampus.network.ResponseSearchPageByType

open class HtmlParse(protected val htmlService: HtmlService) {
    /**
     * 类型
     */
    open val typeMap: Map<String, String> = mapOf()

    /**
     * 获取书籍信息
     */
    open suspend fun getBookInfo(bookUrl: String): BookBean? {
        return null
    }

    /**
     * 根据关键字搜索
     */
    open suspend fun getSearchByKeyword(keyword: String, page: Int): ResponseSearchPageByKeyword {
        return ResponseSearchPageByKeyword()
    }

    /**
     * 根据类型搜索
     */
    open suspend fun getSearchByType(
        typeName: String,
        page: Int
    ): ResponseSearchPageByType {
        return ResponseSearchPageByType()
    }


    /**
     * 获取章节列表
     */
    open suspend fun getChapterList(
        bookUrl: String,
        chaptersUrl: String,
        startCharter:Int,
        limitCharter:Int
    ): ArrayList<ChapterBean> {
        return ArrayList()
    }


    /**
     * 获取章节内容
     */
    open suspend fun getChapterContent(chapterUrl: String): String? {
        return ""
    }
}