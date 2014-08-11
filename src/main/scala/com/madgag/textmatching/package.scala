package com.madgag

import scala.util.matching.Regex
import scala.util.matching.Regex.Match

package object textmatching {
  implicit class RichRegex(regex: Regex) {
    def matches(s: String) = regex.pattern.matcher(s).matches

    def -->(replacer: Match => String): String => String = regex.replaceAllIn(_, replacer)

    def -->(replacement: String): String => String = regex.replaceAllIn(_, replacement)
  }
}
