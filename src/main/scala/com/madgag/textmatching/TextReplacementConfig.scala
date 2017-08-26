/*
 * Copyright (c) 2017 Roberto Tyley
 *
 * This file is part of 'scala-textmatching'.
 *
 * scala-textmatching is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * scala-textmatching is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/ .
 */


package com.madgag.textmatching

object TextReplacementConfig {
  val lineRegex = "(.+?)(?:==>(.*))?".r

  def apply(configLines: Traversable[String], defaultReplacement: String): Option[String=>String] =
    configLines.map(apply(_, defaultReplacement)).reduceLeftOption((f, g) => Function.chain(Seq(f, g)))

  def apply(configLine: String, defaultReplacement: String): (String=>String) = {
    val (matcherText, replacementText) = configLine match {
      case lineRegex(matcherText, null) => (matcherText, defaultReplacement)
      case lineRegex(matcherText, replacementText) => (matcherText, replacementText)
    }

    val textMatcher = TextMatcher(matcherText, defaultType = Literal)

    textMatcher.r --> textMatcher.typ.implicitReplacementTextEscaping(replacementText)
  }

}
