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

import org.specs2.mutable._

class RegexReplacerSpec extends Specification {

  "regex replacer" should {
    "support a simple closure" in {
      val replacer = """f\w*d""".r --> (_.group(0).length.toString)

      replacer("bing fod feed") mustEqual("bing 3 4")
    }
    "support Java appendReplacement syntax" in {
      val replacer = """f(\w*)d""".r --> "x$1y"

      replacer("bing fod feed") mustEqual("bing xoy xeey")
    }
  }
}