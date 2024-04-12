## Regex

```java
import java.util.regex.*!!!!

 	String regex = "<[A-Z]{0,9}>([^<]*(<((\\/?[A-Z]{1,9}>)|(!\\[CDATA\\[(.*?)]]>)))?)*";
 	
 	if (!Pattern.matches(regex, code))

```

Note: for x? where x is a char/number and ? is a regex char, it follows the char before it.

- ?: zero or one occurrences of the preceding element --> [0,1]. For example, colou?r matches both "color" and "colour".

- *: zero or more occurrences of the preceding element --> [0, n]. For example, ab*c matches "ac", "abc", "abbc", "abbbc", and so on.

- +: one or more occurrences of the preceding element --> [1, n]. For example, ab+c matches "abc", "abbc", "abbbc", and so on, but not "ac".

- {n}: The preceding item is matched exactly n times.

- {min,}: The preceding item is matched min or more times.

- {min,max}: The preceding item is matched at least min times, but not more than max times.

- |: A vertical bar separates alternatives. For example, gray|grey can match "gray" or "grey".

- (): 有更高priority的group，scope and precedence of the operators (among other uses). For example, gray|grey and gr(a|e)y are equivalent patterns which both describe the set of "gray" or "grey".

- [...] Matches any single character in brackets.

- [^...] Matches any single character not in brackets.

- 如果想打 "/", 需要写成 "\/"
