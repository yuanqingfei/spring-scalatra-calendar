package me.yuanqingfei.calendar.service

import com.ibm.icu.text.{DateFormat, SimpleDateFormat}
import com.ibm.icu.util._
import org.springframework.stereotype.Component

/**
  * Created by aaron on 16-5-21.
  *
  * Assume  date format: YYYYMMdd
  */
@Component
class CalendarService() {

  def getLunarPair(date: String) = {
    val locale = new ULocale("zh-CN@calendar=chinese")

    val gregCal = new GregorianCalendar(locale)
    gregCal.set(date.substring(0, 4).toInt, date.substring(4, 6).toInt - 1, date.substring(6, 8).toInt)
//    val format =DateFormat.getInstanceForSkeleton(gregCal, "yyyyMMdd", locale)
//    println(format.format(gregCal.getTime))


    val chinaCal = new ChineseCalendar(locale)
    chinaCal.setTime(gregCal.getTime)
    val monthDayFormat = DateFormat.getInstanceForSkeleton(chinaCal, "MMdd", locale)
    val monthDay = monthDayFormat.format(chinaCal.getTime)

    val yearFormat = DateFormat.getInstanceForSkeleton(chinaCal, "yyyy", locale)
    val year = yearFormat.format(chinaCal.getTime)

//    val totalFormat = DateFormat.getInstanceForSkeleton(chinaCal, "yyyyMMdd", locale)
//    val total = totalFormat.format(chinaCal.getTime)
//    println(total)

    (year, monthDay)
  }

  def getYourYearsFromSolor(date: String): java.util.List[String] = {
    val year = date.substring(0, 4)
    val rest = date.substring(4)
    val monthDay = getLunarPair(date)._2
//    println(monthDay)

    val yearInt = year.toInt
    val years = (yearInt + 1 to yearInt + 100) map (newYear => getLunarPair(newYear + rest)) filter (monthDay == _._2) map (_._1)
    val result = new java.util.ArrayList[String]()
    for(y <- years){
      result.add(y)
    }
    result
  }


  def getSolorDate(lunarDate: String): String={
    val gregLocale = new ULocale("zh-CN")
    val gregCal = new GregorianCalendar(gregLocale)
    gregCal.set(lunarDate.substring(0, 4).toInt, lunarDate.substring(4, 6).toInt - 1, lunarDate.substring(6, 8).toInt)

    for(i <- 1 to 60){
      gregCal.add(Calendar.DAY_OF_MONTH, 1)

      val totalFormat = new SimpleDateFormat("yyyyMMdd")
      val total = totalFormat.format(gregCal.getTime)

      val lunarMonthDay = lunarDate.substring(4, 6)+"-"+lunarDate.substring(6, 8)
      if(lunarMonthDay == getLunarPair(total)._2){
        return total
      }
    }
    null
  }

  def getYourYearsFromLunar(date: String) ={
    getYourYearsFromSolor(getSolorDate(date))
  }

}

//object CalendarService extends App{
//  val service = new CalendarService
////  println(service.getYourYearsFromSolor("19810323"))
//
//  println(service.getYourYearsFromLunar("19810218"))
//
////  println(service.getSolorDate("19810218"))
//}
