/*
 * Copyright (c) 2014 Roberto Tyley
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

import util.matching.Regex
import com.madgag.globs.openjdk.Globs

import RegexReplacer._
import java.util.regex.Pattern

object TextMatcher {

  private val allPrefixes = TextMatcherTypes.all.keys mkString ("|")

  val prefixedExpression = s"($allPrefixes):(.*)".r

  def apply(possiblyPrefixedExpr: String, defaultType: TextMatcherType = Literal): TextMatcher = possiblyPrefixedExpr match {
    case prefixedExpression(typ, expr) => TextMatcher(TextMatcherTypes.all(typ), expr)
    case unprefixedExpression => TextMatcher(defaultType, unprefixedExpression)
  }
}

case class TextMatcher(typ: TextMatcherType, expression: String) extends (String => Boolean) {
  lazy val r = typ.regexFor(expression)

  override def apply(s: String) = r.matches(s)
}

object TextMatcherTypes {
  val all = Seq(Glob, Literal, Reg).map(rs => rs.expressionPrefix -> rs).toMap
}

sealed trait TextMatcherType {
  val expressionPrefix: String

  def apply(expression: String) = TextMatcher(this, expression)

  def regexFor(expression: String): Regex

  def implicitReplacementTextEscaping(replacementText: String) = replacementText
}

object Glob extends TextMatcherType {
  val expressionPrefix = "glob"

  def regexFor(expression: String) = Globs.toUnixRegexPattern(expression).r
}

object Literal extends TextMatcherType {
  val expressionPrefix = "literal"

  def regexFor(expression: String) = Pattern.quote(expression).r

  override def implicitReplacementTextEscaping(replacementText: String) = Regex.quoteReplacement(replacementText)
}

object Reg extends TextMatcherType {
  val expressionPrefix = "regex"

  def regexFor(expression: String) = expression.r
}

