166. Fraction to Recurring Decimal -  Medium

Given two integers representing the numerator and denominator of a fraction, return the fraction 
in string format.

If the fractional part is repeating, enclose the repeating part in parentheses.

Example 1:

Input: numerator = 1, denominator = 2
Output: "0.5"
Example 2:

Input: numerator = 2, denominator = 1
Output: "2"
Example 3:

Input: numerator = 2, denominator = 3
Output: "0.(6)"

******************************************************
key:
	- 
	- edge case:
		1) negative number!!!!
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- remember to check for negative number!
	- for long number, need to check it equals to "0L"

stats:

	- Runtime: 1 ms, faster than 99.96% of Java online submissions for Fraction to Recurring Decimal.
	- Memory Usage: 34.2 MB, less than 100.00% of Java online submissions for Fraction to Recurring Decimal.



    public String fractionToDecimal(int numerator, int denominator) {
        return helper(numerator, denominator);
    }

    private String helper(long numerator, long denominator) {
        if (numerator == 0L) return "0";

        StringBuilder builder = new StringBuilder();

        if (numerator < 0) {
            numerator = -numerator;
            if (denominator < 0) {
                denominator = -denominator;
            } else {
                builder.append('-');
            }
        } else if (denominator < 0) {
            denominator = -denominator;
            builder.append('-');
        }

        // 整数
        builder.append(numerator / denominator);
        long mod = numerator % denominator;
        if (mod == 0) 
        	return builder.toString();
        builder.append('.');

        // puts in mod & it's position in the builder
        Map<Long, Integer> map = new HashMap<>();
        map.put(mod, builder.length());

        while (mod > 0) {

        	// same as mod *= 10
            mod = (mod << 3) + (mod << 1);
            builder.append(mod / denominator);
            mod = mod % denominator;
            if (map.containsKey(mod)) 
            	break;
            else 
            	map.put(mod, builder.length());
        }

        // break when see a repetitive mod, meaning there'll be 循环小数
        if (mod > 0) {
            int index = map.get(mod);
            builder.insert(index, '(');
            builder.append(')');
        }

        return builder.toString();
    }





