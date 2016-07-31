package me.yuanqingfei.calendar.rest

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializerFeature
import me.yuanqingfei.calendar.service.CalendarService
import org.scalatra.ScalatraServlet
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
  * Created by aaron on 16-5-28.
  */
@Component
class CalendarServlet @Autowired()(calendarService: CalendarService) extends ScalatraServlet {

  val logger =  LoggerFactory.getLogger(getClass)

  get("/yangli/:birthday") {
    val yourYears= calendarService.getYourYearsFromSolor(params("birthday"))
    JSON.toJSONString(yourYears, SerializerFeature.PrettyFormat)
  }


  get("/yinli/:birthday") {
    val yourYears= calendarService.getYourYearsFromLunar(params("birthday"))
    JSON.toJSONString(yourYears, SerializerFeature.PrettyFormat)
  }
  notFound {
    // remove content type in case it was set through an action
    contentType = "text/html"
    serveStaticResource() getOrElse resourceNotFound()
  }

//  notFound {
//    // remove content type in case it was set through an action
//    contentType = null
//    // Try to render a ScalateTemplate if no route matched
//    findTemplate(requestPath) map { path =>
//      contentType = "text/html"
//      layoutTemplate(path)
//    } orElse serveStaticResource() getOrElse resourceNotFound()
//  }

}
