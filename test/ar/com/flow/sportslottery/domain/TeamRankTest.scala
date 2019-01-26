package ar.com.flow.sportslottery.domain

import java.time.LocalDate

import org.specs2.mutable.Specification

import scala.util.Sorting

class TeamRankTest extends Specification with TestObjects {
  "Can't add result to rank for a different team" >> {
    val croatiaRank = new TeamRank("Croatia")

    croatiaRank.addMatchResult(argentinaIcelandMatchResult) must
      throwA(new IllegalArgumentException("requirement failed: Match result should correspond to team"))
  }

  "Sum match results" >> {
    val argentinaRank = new TeamRank("Argentina")

    argentinaRank.addMatchResult(argentinaIcelandMatchResult)
    argentinaRank.addMatchResult(argentinaCroatiaMatchResult)
    argentinaRank.addMatchResult(argentinaNigeriaMatchResult)

    val argentinaPoints = argentinaIcelandMatchResult.forTeam("Argentina").points +
                          argentinaCroatiaMatchResult.forTeam("Argentina").points +
                          argentinaNigeriaMatchResult.forTeam("Argentina").points

    argentinaRank.matchesPlayed must be equalTo 3
    argentinaRank.points must be equalTo argentinaPoints
  }

  "Order" >> {
    "Won matches goes first" >> {
      val ranks = Array(argentinaRank, croatiaRank)

      Sorting.quickSort(ranks)

      ranks must be equalTo Array(croatiaRank, argentinaRank)
    }

    "When teams have same match points then goals difference goes first" >> {
      val jakartaChinaMatchSchedule = new MatchSchedule("Jakarta", "China", LocalDate.of(2018, 1, 1))
      val jakartaChinaMatchResult = new MatchResult(jakartaChinaMatchSchedule, 0, 1)
      val jakartaKamchatkaMatchSchedule = new MatchSchedule("Jakarta", "Kamchatka", LocalDate.of(2018, 1, 1))
      val jakartaKamchatkaMatchResult = new MatchResult(jakartaKamchatkaMatchSchedule, 1, 3)

      val chinaRank = new TeamRank("China")
      chinaRank.addMatchResult(jakartaChinaMatchResult)

      val kamchatkaRank = new TeamRank("Kamchatka")
      kamchatkaRank.addMatchResult(jakartaKamchatkaMatchResult)

      val ranks = Array(chinaRank, kamchatkaRank)

      Sorting.quickSort(ranks)

      ranks must be equalTo Array(kamchatkaRank, chinaRank)
    }
  }
}
