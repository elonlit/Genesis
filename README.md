# Genesis ***𐤁***
[![Open Source Love svg2](https://badges.frapsoft.com/os/v2/open-source.svg?style=for-the-badge)](https://github.com/ellerbrock/open-source-badges/)

> "*My frame was not hidden from you, when I was being made in secret, intricately woven in the depths of the earth. Your eyes saw my unformed substance; in your book were written, every one of them, the days that were formed for me, when as yet there was none of them*" [^1]

Genesis is an interpreted, procedural, and Turing-complete Paleo-Hebrew programming language. Diacritical signs are forgone for simplification, though maybe Nikud can be used in prospect as a means for more reserved keywords.

<p align="center">
    <img src="https://img.shields.io/tokei/lines/github/elonlit/Genesis" />
    <img src="https://img.shields.io/powershellgallery/p/DNS.1.1.1.1" />
  <a href="https://www.gnu.org/licenses/gpl-3.0">
    <img src="https://img.shields.io/badge/License-GPLv3-blue.svg" />
  </a>
</p>

<p align="center">
  <a href="#valid-keywords">Keywords</a> •
  <a href="#operations-punctuation-elements--identifiers">Operators</a> •
  <a href="#data-types--literals">Types</a> •
  <a href="#control-flow">Control Flow</a> •
  <a href="#subroutines">Subroutines</a> •
  <a href="#data-structures">Data Structures</a> •
  <a href="#math-library--native-utilities">Utilities</a> •
  <a href="#faq">FAQ</a>
</p>

---

## Valid Keywords
| Lexeme  | 𐤁 Equivalent(s) |
| ------------- | ------------- |
| Print  | 𐤄𐤃𐤐𐤎 |
| Print Line  | 𐤄𐤃𐤐𐤎𐤇 |
| Declare/Initialize Variable  | 𐤄𐤂𐤃𐤓 |
| Declare Subroutine  | 𐤐𐤅𐤍𐤒𐤑𐤉𐤄 |
| If  | 𐤀𐤌 |
| Then  | 𐤀𐤆 |
| While  | 𐤁𐤏𐤅𐤃 |
| For  | 𐤏𐤁𐤅𐤓 |
| For Each  | 𐤏𐤁𐤅𐤓𐤊𐤋 |
| Sleep  | 𐤉𐤔𐤍 |
| Consecrate  | 𐤒-𐤃-𐤔 |

The `𐤒-𐤃-𐤔` keyword, meaning literally "to consecrate" or "to purify," denotes when the scope of a subroutine or loop terminates.

## Operations, Punctuation Elements, & Identifiers
Java-style syntax and precedence are preserved for most operators:

`+` - addition (numbers, strings)<br />
`-` - subtraction (numbers)<br />
`/` - division (numbers)<br />
`*` - multiplication (numbers)<br />
`^` - power (numbers)<br />
`=` - assignment (numbers, strings)<br />
`==` - logical equals (numbers, strings)<br />
`=!` - not equal to (numbers, strings)<br />
`<` - less than (numbers)<br />
`>` - greater than (numbers)<br />
`=>` - greater than or equal to (numbers)<br />
`=<` - less than or equal to (numbers)<br />
`&&` - logical and (booleans)<br />
`||` - logical or (booleans)<br />

However, the associativity of most operators is from right-to-left:

<pre dir="rtl" align="right">
𐤄𐤂𐤃𐤓 𐤐𐤅 = 𐤊״𐤇 - 𐤄׳ // 23
</pre>

Identifiers can be represented by alphanumeric text (including `_`) and do not have to start with an alphabetic character.

## Data Types & Literals 
Genesis is weakly and dynamically typed, so casting between primitives is handled implicitly by the interpreter. There are three data types:

1. Number
      - Encompasses `Bytes`, `Shorts`, `Integers`, `Longs`, `Doubles`, and `Floats`. 
2. Boolean
      - Supports literals `𐤀𐤌𐤕` or `𐤔𐤒𐤓`, which correspond to `True` or `False`, respectively.
3. String
      - Delimited by quotation marks, e.g. `"!𐤔𐤋𐤅𐤌 𐤏𐤅𐤋𐤌"`.

The Paleo-Hebrew alphabet may have used gematria to denote cardinal values, although there is only evidence of this on the Samaria Ostraca and Dead Sea Scroll 4Q252. This quasi-decimal isopsephic number system is adopted for a lack of an academic consensus. 

In this paradigm of numerology, there is no notation for zero, and the numeric values for individual letters are added together. Each unit (`1`, `2`, ..., `9`) is assigned a separate letter, each tens (`10`, `20`, ..., `90`) a separate letter, and the first four hundreds (`100`, `200`, `300`, `400`) a separate letter. The later hundreds (`500`, `600`, `700`, `800`, and `900`) are represented by the sum of two or three letters representing the first four hundreds. To represent numbers from `1,000` to `999,999`, the same letters are reused to serve as thousands, tens of thousands, and hundreds of thousands. Biblical pseudepigrapha use these transformations extensively. Standard (normative value) encoding per the conventional affine Mispar Hechrachi method of gematria is as follows:
		
| Decimal | Hebrew | 𐤁 Glyph |
| --- | --- | --- |
| 1 | Alep | 𐤀 |
| 2 | Bet | 𐤁 |
| 3 | Gimel | 𐤂 |
| 4 | Dalet | 𐤃 |
| 5 | He | 𐤄 |
| 6 | Waw | 𐤅 |
| 7 | Zayin | 𐤆 |
| 8 | Het | 𐤇 |
| 9 | Tet | 𐤈 |
| 10 | Yod | 𐤉 |
| 20 | Kaf | 𐤊 |
| 30 | Lamed | 𐤋 |
| 40 | Mem | 𐤌 |
| 50 | Nun | 𐤍 |
| 60 | Samek | 𐤎 |
| 70 | Ayin | 𐤏 |
| 80 | Pe | 𐤐 |
| 90 | Sade | 𐤑 |
| 100 | Qop | 𐤒 |
| 200 | Res | 𐤓 |
| 300 | Sin | 𐤔 |
| 400 | Taw | 𐤕 |

Gershayim `״` (U+05F4 in Unicode, and resembling a double quote mark) (sometimes erroneously referred to as merkha'ot, which is Hebrew for double quote) are inserted before (to the right of) the last (leftmost) letter to indicate that the sequence of letters represents a gematric sequence of at least two Hebrew numerals (e.g., `28` → `𐤊״𐤇` and `5782` → `𐤕𐤕𐤕𐤕𐤕𐤕𐤕𐤕𐤕𐤕𐤕𐤕𐤕𐤕𐤕𐤒𐤐״𐤁`).

Similarly, a single geresh `׳` (U+05F3 in Unicode, and resembling a single quote mark) is appended after (to the left of) a single letter in the case where a number is represented by a single Hebrew numeral (e.g. `100` → `𐤒׳`).

## Control Flow
> "*Seek the Lord while he may be found; call on him while he is near*" [^2]

The standard suite of loop constructs is supported. An iterative implementation for generating the first ten terms of the Fibonacci sequence using a `𐤁𐤏𐤅𐤃` loop is formulated as an example:

<pre dir="rtl" align="right">
𐤄𐤂𐤃𐤓 𐤌𐤎𐤐𐤓 = 𐤉׳
𐤄𐤂𐤃𐤓 𐤓𐤀𐤔𐤅𐤍 = 𐤀׳
𐤄𐤂𐤃𐤓 𐤔𐤍𐤉𐤄 = 𐤀׳ - 𐤀׳
𐤄𐤂𐤃𐤓 𐤃𐤋𐤐𐤒 = 𐤀׳ - 𐤀׳
𐤄𐤂𐤃𐤓 𐤆𐤌𐤍𐤉 = 𐤀׳ - 𐤀׳

𐤁𐤏𐤅𐤃 𐤃𐤋𐤐𐤒 <= 𐤌𐤎𐤐𐤓:
    𐤄𐤃𐤐𐤎𐤇 𐤔𐤍𐤉𐤄
    𐤆𐤌𐤍𐤉 = 𐤓𐤀𐤔𐤅𐤍 + 𐤔𐤍𐤉𐤄
    𐤓𐤀𐤔𐤅𐤍 = 𐤔𐤍𐤉𐤄
    𐤔𐤍𐤉𐤄 = 𐤆𐤌𐤍𐤉
    𐤃𐤋𐤐𐤒 = 𐤃𐤋𐤐𐤒 + 𐤀׳
𐤒-𐤃-𐤔
</pre>

The following `𐤏𐤁𐤅𐤓` loop prints out the first ten natural numbers:

<pre dir="rtl" align="right">
𐤏𐤁𐤅𐤓 𐤌𐤎𐤐𐤓=𐤉׳,𐤌𐤎𐤐𐤓>=𐤀׳,𐤌𐤎𐤐𐤓=𐤌𐤎𐤐𐤓-𐤀׳:
    𐤄𐤃𐤐𐤎𐤇 𐤌𐤎𐤐𐤓
𐤒-𐤃-𐤔
</pre>

To accomplish nested operations or anamorphism, it is recommended to do a composition of subroutines.

## Subroutines
> "*'I AM THAT I AM'*" [^3]

Functions in Genesis are declared using the `𐤐𐤅𐤍𐤒𐤑𐤉𐤄` keyword. Being void and non-parameterized, however, they are actually subroutines. There is recursion insomuch that making a self-referential call from within a subroutine is possible, but there is no means to exit that recursion to express the irrevocable danger of pride and egoism. This design follows the contention that recursion, as Peter Deutsch identified, is divine and not encompassed by the domain of human programmers, as evidenced by God identifying himself recursively.  

<!--- > "*For if, after they have returned from the defilements of the world by the knowledge of the Lord and Savior Jesus Christ, they are again entangled in them and are overcome, the last state has become worse for them than the first*"-->

To call on a subroutine, use the reference name with which it was defined. The following subroutine `𐤇𐤉𐤁𐤅𐤓` approximates the gravitational force of a 290-gram KJV Compact Ultraslim Bible one meter from a 70-kg human being:

<pre dir="rtl" align="right">
𐤄𐤂𐤃𐤓 𐤊𐤅𐤇 = (𐤀׳ / (𐤉׳ ^ 𐤉״𐤀)) * (𐤕𐤓𐤎״𐤆 / 𐤒׳)
𐤄𐤂𐤃𐤓 𐤕𐤅𐤓𐤄 = 𐤊״𐤈 / 𐤒׳
𐤄𐤂𐤃𐤓 𐤀𐤃𐤌 = 𐤏׳
𐤄𐤂𐤃𐤓 𐤌𐤓𐤇𐤒 = 𐤀׳

𐤐𐤅𐤍𐤒𐤑𐤉𐤄 𐤇𐤉𐤁𐤅𐤓:
	 𐤄𐤂𐤃𐤓 𐤄𐤇𐤉𐤁𐤅𐤓 = (𐤊𐤅𐤇 * 𐤕𐤅𐤓𐤄 * 𐤀𐤃𐤌) / (𐤌𐤓𐤇𐤒 * 𐤌𐤓𐤇𐤒)
	 𐤄𐤃𐤐𐤎 𐤄𐤇𐤉𐤁𐤅𐤓
𐤒-𐤃-𐤔

𐤇𐤉𐤁𐤅𐤓
</pre>

Other examples can be found in the respository.

## Data Structures

Genesis provides fixed-length untyped array data structures. Curly braces are used to initialize arrays, and elements can be accessed or mutated through square bracket index operators:

<pre dir="rtl" align="right">
𐤄𐤂𐤃𐤓 𐤌𐤎𐤐𐤓 = {𐤀׳, 𐤁׳, 𐤂׳}

𐤌𐤎𐤐𐤓[𐤈׳/𐤈׳] = 𐤔𐤒𐤓

𐤏𐤁𐤅𐤓𐤊𐤋 𐤀𐤋𐤌𐤍𐤈, 𐤌𐤎𐤐𐤓:
	𐤄𐤃𐤐𐤎𐤇 𐤀𐤋𐤌𐤍𐤈
𐤒-𐤃-𐤔
</pre>

As denoted, `𐤏𐤁𐤅𐤓` or `𐤏𐤁𐤅𐤓𐤊𐤋` looping an array will yield its values.

## Math Library & Native Utilities

| Function | Description | 𐤁 Equivalent(s) |
| :-- | --- | --: |
| Sqrt(#) | Returns the correctly rounded positive square root of a number value. | 𐤔𐤅𐤓𐤔(𐤍) |
| Sin(∠) | Returns the trigonometric sine of an angle. | 𐤎𐤉𐤍(𐤈) |
| Cos(∠) | Returns the trigonometric cosine of an angle. | 𐤒𐤅𐤎(𐤈) |
| Tan(∠) | Returns the trigonometric tangent of an angle. | 𐤈𐤍(𐤈) |
| ToDegrees(C) | Converts an angle measured in radians to degrees. | 𐤋𐤃(𐤒) |
| ToRadians(∠) | Converts an angle measured in degrees to radians. | 𐤋𐤓(𐤈) |
| Absolute(#) | Returns the absolute value of a number value. | 𐤏𐤂𐤋(𐤍) |
| Log(#) | Returns the natural logarithm (base *e*) of a number value. | (𐤍)𐤋𐤅𐤂 |
| Exp(#) | Returns Euler's number *e* raised to the power of a number value. | (𐤍)𐤀𐤒𐤎𐤐 |
| Ulp(#) | Returns the size of an ulp of the argument. | (𐤍)𐤀𐤅𐤋𐤐 |
| PI() | Returns π rounded to double precision. | ()𐤐𐤉𐤉 |
| Random() | Returns a number value greater than or equal to 0.0 and less than 1.0. | ()𐤓𐤍𐤃 |
| Evince() | Returns a random Bible quote. | ()𐤁𐤓𐤀 |

Some calculations:

<pre dir="rtl" align="right">
𐤄𐤂𐤃𐤓 𐤔𐤈𐤇 = 𐤃׳ * 𐤐𐤉𐤉() * (𐤉״𐤁 ^ 𐤁׳)
𐤄𐤂𐤃𐤓 𐤌𐤔𐤅𐤋𐤔 = (𐤀׳/𐤁׳) * (𐤄׳ * 𐤎״𐤃 * 𐤎𐤉𐤍(𐤌״𐤄))
𐤄𐤂𐤃𐤓 𐤒𐤋 = 𐤔𐤅𐤓𐤔(𐤎״𐤃) * 𐤓𐤍𐤃()

𐤄𐤃𐤐𐤎𐤇 𐤔𐤈𐤇
𐤄𐤃𐤐𐤎𐤇 𐤌𐤔𐤅𐤋𐤔
𐤄𐤃𐤐𐤎𐤇 𐤒𐤋
</pre>

FAQ
------
### Why not use Modern Hebrew?
If you are able to program in this language, I have failed.

### Why are you running an interpreted language over an interpreted language?
> "*Wherefore, just as sin came into the world through one man, and death through sin, and so death spread to all men because all sinned*" [^4]

### Why not make an object-oriented language?
This suggestion makes me consternated. Genesis will never be object-oriented because the Bible explicitly forbids object worship:
> "*These prized objects are really worthless. The people who worship idols don’t know this, so they are all put to shame. []Their eyes are closed, and they cannot see. Their minds are shut, and they cannot think. The person who made the idol never stops to reflect, 'Why, it’s just a block of wood! I burned half of it for heat and used it to bake my bread and roast my meat. How can the rest of it be a god? Should I bow down to worship a piece of wood?'*" [^5]

[^1]: [Psalm 139:13-16](https://www.biblegateway.com/passage/?search=Psalm%20139%3A13-16&version=NIV)
[^2]: [Isaiah 55:6-7](https://www.biblegateway.com/passage/?search=Isaiah%2055%3A6-7&version=KJV)
[^3]: [Exodus 3:14](https://www.biblegateway.com/passage/?search=Exodus%203%3A14&version=KJV)
[^4]: [Romans 5:12-13](https://biblia.com/bible/esv/romans/5/12-13)
[^5]: [Isaiah 44:9-20](https://www.biblestudytools.com/nlt/isaiah/passage/?q=isaiah+44:9-20)
