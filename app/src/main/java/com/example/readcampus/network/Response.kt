package com.example.readcampus.network

import com.example.readcampus.bean.BookBean
import com.example.readcampus.bean.ChapterBean

data class ResponseBookInfo(val bookBean: BookBean)

data class ResponseChapter(val chapterBeans:List<ChapterBean>, val cacheContents:Boolean=false)

data class ResponseSearchPageByKeyword(val keyword:String="", val currentPage:Int=1, val totalPage:Int=1, val bookBeans: ArrayList<BookBean> = ArrayList())

data class ResponseSearchPageByType(val typeName:String="",val currentPage:Int=1, val totalPage:Int=1, val bookBeans: ArrayList<BookBean> = ArrayList())