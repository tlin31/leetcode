import java.io.*;
import java.util.*;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import java.util.Objects;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;


/*
* register({
*    funA:{["Boolean", "Integer"], isVariadic:false},
*    funB:{["Integer"], isVariadic:false},
*    funC:{["Integer"], isVariadic:true}
 
* })
*
* findMatches(["Boolean", "Integer"])            -> [funA]
* findMatches(["Integer"])                       -> [funB, funC]
* findMatches(["Integer", "Integer", "Integer"]) -> [funC]
*/
 
 
/*
* More Examples:
*
*    funD:{["String", "Integer", "Integer", "Integer"], isVariadic:true},
*    funE:{["String", "Integer", "Integer"], isVariadic:false}
*
* findMatches(["String", "Integer"])             -> []
* findMatches(["String", "Integer", "Integer", "Integer", "Integer"])  -> [funD]
*/


 
class Function {
   public final List<String> argumentTypes; // e.g. ["Integer", "String", "PersonClass"]
   public final String name;
   public final boolean isVariadic;
 
   Function(String name, List<String> argumentTypes, Boolean isVariadic) {
      this.name = name;
      this.argumentTypes = argumentTypes;
      this.isVariadic = isVariadic;
   }
 
   public String toString() {
      return this.name;
   }
}
 
class FunctionLibrary {
  HashMap<List<String>, List<Function>> notVariadicMap;
  HashMap<List<String>, List<Function>> isVariadicMap;
  
  void register(Set<Function> functions) {
    // implement me
    
    notVariadicMap = new HashMap<>();
    isVariadicMap = new HashMap<>();
    
    
    for (Function function: functions) {
       if (function.isVariadic) {
         if (isVariadicMap.containsKey(function.argumentTypes)){
           //same argument, add it to list
           List<Function> updateList = isVariadicMap.get(function.argumentTypes);
           updateList.add(function);
           isVariadicMap.put(function.argumentTypes,updateList);
         } else {
           List<Function> newList = new ArrayList<Function>();
           newList.add(function);
           isVariadicMap.put(function.argumentTypes,newList);
         }

       } else {
         if (notVariadicMap.containsKey(function.argumentTypes)){
           //same argument, add it to list
           List<Function> updateList = notVariadicMap.get(function.argumentTypes);
           updateList.add(function);
           notVariadicMap.put(function.argumentTypes,updateList);
         } else {
           List<Function> newList = new ArrayList<Function>();
           newList.add(function);
           notVariadicMap.put(function.argumentTypes,newList);
         }
       } 
    }
  }
 
  List<Function> findMatches(List<String> argumentTypes) {
    // implement me
     
    List<Function> matchFunc = new ArrayList<Function>();
    
    if (notVariadicMap.containsKey(argumentTypes)) {
        //check in nonvariadicmap
        matchFunc.addAll(notVariadicMap.get(argumentTypes));
    }
    
    if (isVariadicMap.containsKey(argumentTypes)) {
        //check in nonvariadicmap
        matchFunc.addAll(isVariadicMap.get(argumentTypes));
    }
    
    int endIndex = argumentTypes.size()-1;
    int secToLast = endIndex-1;
    
    while (secToLast >= 0) {

      if (!(argumentTypes.get(endIndex)).equals(argumentTypes.get(secToLast))) {
        break;
      }
      
      List<String> modifiedArgs = argumentTypes.subList(0,endIndex);
      if (isVariadicMap.containsKey(modifiedArgs)) {
        matchFunc.addAll(isVariadicMap.get(modifiedArgs));

      }
      
      // if not, delete last arg
      endIndex--;
      secToLast--;

      
    }
    
    
  
    // always check for variadic
    return matchFunc;
  }
}
 





/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

class Solution {
  public static void main(String[] args) {

    FunctionLibrary fl = new FunctionLibrary();

    fl.register(ImmutableSet.of(
        new Function( "funA", Arrays.asList("String", "Integer"), false ),
        new Function( "funB", Arrays.asList("Integer"), false ),
        new Function( "funC", Arrays.asList("Integer"), true )
    ));

    System.out.println(fl.findMatches(Arrays.asList("String", "Integer")));
    System.out.println(fl.findMatches(Arrays.asList("Integer")));
    System.out.println(fl.findMatches(Arrays.asList("Integer", "Integer", "Integer")));
  }
}
