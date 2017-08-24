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

import org.scalatest.{FlatSpec, Matchers}

class TextMatcherSpec extends FlatSpec with Matchers {

  "text matcher creation" should
    "parse prefix if present" in {
    TextMatcher("literal:foobar") shouldBe Literal("foobar")
    TextMatcher("glob:foobar") shouldBe Glob("foobar")
    TextMatcher("regex:foobar") shouldBe Reg("foobar")

    TextMatcher("boom", Reg) shouldBe Reg("boom")
  }
  it should "use default type if no type specified" in {
    TextMatcher("b{4}", defaultType = Glob) shouldBe Glob("b{4}")
    TextMatcher("b{4}", defaultType = Literal) shouldBe Literal("b{4}")
  }
  it should "use parsed type if present" in {
    TextMatcher("literal:b{4}", defaultType = Glob) shouldBe Literal("b{4}")
    TextMatcher("glob:b{4}", defaultType = Literal) shouldBe Glob("b{4}")
  }
  it should "quote content of literal expression in generated regex" in {
    "what was b4 this?" should include regex TextMatcher("literal:b4").r

    "what was b{4} this?" should include regex TextMatcher("literal:b{4}").r
    "what was bbbb this?" should not include regex(TextMatcher("literal:b{4}").r)

    "what was bbbb this?" should include regex TextMatcher("regex:b{4}").r
  }
}