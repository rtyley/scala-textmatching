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

class TextMatcherSpec extends Specification {

  "text matcher creation" should {
    "parse prefix if present" in {
      TextMatcher("literal:foobar") mustEqual Literal("foobar")
      TextMatcher("glob:foobar") mustEqual Glob("foobar")
      TextMatcher("regex:foobar") mustEqual Reg("foobar")

      TextMatcher("boom", Reg) mustEqual Reg("boom")
    }
    "quote content of literal expression in generated regex" in {
      "what was b4 this?" must =~ (TextMatcher("literal:b4").r)

      "what was b{4} this?" must =~ (TextMatcher("literal:b{4}").r)
      "what was bbbb this?" must not =~ (TextMatcher("literal:b{4}").r)

      "what was bbbb this?" must =~ (TextMatcher("regex:b{4}").r)
    }
  }
}