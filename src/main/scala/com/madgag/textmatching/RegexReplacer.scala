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
import util.matching.Regex.Match

object RegexReplacer {

  implicit class RichRegex(regex: Regex) {
    def matches(s: String) = regex.pattern.matcher(s).matches

    def -->(replacer: Match => String): String => String = regex.replaceAllIn(_, replacer)

    def -->(replacement: String): String => String = regex.replaceAllIn(_, replacement)
  }

}
