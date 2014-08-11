scala-textmatching
==================

A small Scala veneer over text-matching that unifies globs, regexs and literals.

```
scala> import com.madgag.textmatching._
import com.madgag.textmatching._

scala> val textMatchers = Seq(
     | "glob:b?t",
     | "glob:b*t",
     | "glob:*.log",
     | "regex:b+",
     | "literal:b*t",
     | "glob:id_{dsa,rsa}"
     | ).map(TextMatcher(_))
textMatchers: Seq[com.madgag.textmatching.TextMatcher] = List(glob:<b?t>, glob:<b*t>, glob:<*.log>, regex:<b+>, literal:<b*t>, glob:<id_{dsa,rsa}>)

scala> val bitsOfText = Seq("bit", "bat", "bot", "boot", "b*t", "id_dsa", "id_rsa", "app.log")
bitsOfText: Seq[String] = List(bit, bat, bot, boot, b*t, id_dsa, id_rsa, app.log)

scala> bitsOfText.map(s => s -> textMatchers.filter(_.r.matches(s)).mkString(" ")).toMap
res7: scala.collection.immutable.Map[String,String] = Map(
  app.log -> glob:<*.log>,
  bat -> glob:<b?t> glob:<b*t>,
  bit -> glob:<b?t> glob:<b*t>,
  id_dsa -> glob:<id_{dsa,rsa}>,
  boot -> glob:<b*t>,
  id_rsa -> glob:<id_{dsa,rsa}>,
  bot -> glob:<b?t> glob:<b*t>,
  b*t -> glob:<b?t> glob:<b*t> literal:<b*t>
)
```
