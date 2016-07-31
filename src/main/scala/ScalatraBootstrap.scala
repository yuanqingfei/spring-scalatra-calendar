import javax.servlet.ServletContext

import ch.qos.logback.classic.Level
import me.yuanqingfei.calendar.MyRestApplication
import me.yuanqingfei.calendar.rest.CalendarServlet
import org.apache.log4j.LogManager
import org.scalatra.LifeCycle
import org.springframework.context.annotation.AnnotationConfigApplicationContext

/**
  * Created by aaron on 16-6-3.
  */
class ScalatraBootstrap extends LifeCycle {

  override def init(context: ServletContext){

    val springContext = new AnnotationConfigApplicationContext
    springContext.register(classOf[MyRestApplication])
    springContext.refresh()

    context.mount(springContext.getBean[CalendarServlet]("calendarServlet", classOf[CalendarServlet]), "/api/*")
  }
}
