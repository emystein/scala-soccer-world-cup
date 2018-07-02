package ar.com.flow.sportslottery.domain

import java.util.Calendar

import org.specs2.mutable.Specification

class MatchScheduleTest extends Specification with TestObjects {
  "Create a Match Schedule" >> {
    "Assign home and visitor" >> {
      argentinaIcelandMatch.homeTeam must be equalTo "Argentina"
      argentinaIcelandMatch.visitorTeam must be equalTo "Iceland"
    }

    "Assign date as separate year, month, day" >> {
      val matchEvent = new MatchSchedule(groupA, "Argentina", "Nigeria", 2018, 6, 16)

      val day3 = new Calendar.Builder().setDate(2018, Calendar.JUNE, 16).build().getTime

      matchEvent.date must be equalTo day3
    }

    "Home should be in the group" >> {
      new MatchSchedule(groupA, "Germany", "Iceland", day1) must
        throwA(new IllegalArgumentException("requirement failed: Home should be in the group"))
    }

    "Visitor should be in the group" >> {
      new MatchSchedule(groupA, "Argentina", "Germany", day1) must
        throwA(new IllegalArgumentException("requirement failed: Visitor should be in the group"))
    }

    "Home and visitor should be different teams" >> {
      new MatchSchedule(groupA, "Argentina", "Argentina", day1) must
        throwA(new IllegalArgumentException("requirement failed: Home and visitor should be different teams"))
    }

  }
}