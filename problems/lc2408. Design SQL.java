2408. Design SQL - Medium

You are given two string arrays, names and columns, both of size n. The ith table is represented by the name names[i] and contains columns[i] number of columns.

You need to implement a class that supports the following operations:

Insert a row in a specific table with an id assigned using an auto-increment method, where the id of the first inserted row is 1, and the id of each new row inserted into the same table is one greater than the id of the last inserted row, even if the last row was removed.
Remove a row from a specific table. Removing a row does not affect the id of the next inserted row.
Select a specific cell from any table and return its value.
Export all rows from any table in csv format.

Implement the SQL class:

SQL(String[] names, int[] columns)
Creates the n tables.

bool ins(String name, String[] row)
Inserts row into the table name and returns true.
If row.length does not match the expected number of columns, or name is not a valid table, returns false without any insertion.

void rmv(String name, int rowId)
Removes the row rowId from the table name.
If name is not a valid table or there is no row with id rowId, no removal is performed.

String sel(String name, int rowId, int columnId)
Returns the value of the cell at the specified rowId and columnId in the table name.
If name is not a valid table, or the cell (rowId, columnId) is invalid, returns "<null>".

String[] exp(String name)
Returns the rows present in the table name.
If name is not a valid table, returns an empty array. Each row is represented as a string, with each cell value (including the row's id) separated by a ",".
 

Example 1:

Input:

["SQL","ins","sel","ins","exp","rmv","sel","exp"]
[[["one","two","three"],[2,3,1]],["two",["first","second","third"]],["two",1,3],["two",["fourth","fifth","sixth"]],["two"],["two",1],["two",2,2],["two"]]
Output:

[null,true,"third",true,["1,first,second,third","2,fourth,fifth,sixth"],null,"fifth",["2,fourth,fifth,sixth"]]
Explanation:

// Creates three tables.
SQL sql = new SQL(["one", "two", "three"], [2, 3, 1]);

// Adds a row to the table "two" with id 1. Returns True.
sql.ins("two", ["first", "second", "third"]);

// Returns the value "third" from the third column
// in the row with id 1 of the table "two".
sql.sel("two", 1, 3);

// Adds another row to the table "two" with id 2. Returns True.
sql.ins("two", ["fourth", "fifth", "sixth"]);

// Exports the rows of the table "two".
// Currently, the table has 2 rows with ids 1 and 2.
sql.exp("two");

// Removes the first row of the table "two". Note that the second row
// will still have the id 2.
sql.rmv("two", 1);

// Returns the value "fifth" from the second column
// in the row with id 2 of the table "two".
sql.sel("two", 2, 2);

// Exports the rows of the table "two".
// Currently, the table has 1 row with id 2.
sql.exp("two");
Example 2:

Input:

["SQL","ins","sel","rmv","sel","ins","ins"]
[[["one","two","three"],[2,3,1]],["two",["first","second","third"]],["two",1,3],["two",1],
["two",1,2],["two",["fourth","fifth"]],["two",["fourth","fifth","sixth"]]]
Output:

[null,true,"third",null,"<null>",false,true]
Explanation:

// Creates three tables.
SQL sQL = new SQL(["one", "two", "three"], [2, 3, 1]); 

// Adds a row to the table "two" with id 1. Returns True. 
sQL.ins("two", ["first", "second", "third"]); 

// Returns the value "third" from the third column 
// in the row with id 1 of the table "two".
sQL.sel("two", 1, 3); 

// Removes the first row of the table "two".
sQL.rmv("two", 1); 

// Returns "<null>" as the cell with id 1 
// has been removed from table "two".
sQL.sel("two", 1, 2); 

// Returns False as number of columns are not correct.
sQL.ins("two", ["fourth", "fifth"]); 

// Adds a row to the table "two" with id 2. Returns True.
sQL.ins("two", ["fourth", "fifth", "sixth"]); 
 

Constraints:

n == names.length == columns.length
1 <= n <= 104
1 <= names[i].length, row[i].length, name.length <= 10
names[i], row[i], and name consist only of lowercase English letters.
1 <= columns[i] <= 10
1 <= row.length <= 10
All names[i] are distinct.
At most 2000 calls will be made to ins and rmv.
At most 104 calls will be made to sel.
At most 500 calls will be made to exp.
 

Follow-up: Which approach would you choose if the table might become sparse due to many deletions, 
and why? Consider the impact on memory usage and performance.

******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

	-	

每个table有
- unique string names
- list of current valid row ids, check before insert, select exp,等等


每个 table 存三样东西：
	1. columns 列名数组 → 方便列找索引
	2. rows: { rowId -> rowArray }
	3. autoIncrement rowId （从 1 开始）

select 仅仅是：
	row = rows[rowId]
	colIndex = columnIndexMap[columnName]
	return row[colIndex]
 
🔍 关键点解释（面试官想听的）
✔ 为什么不存二维数组？

因为 rowId 可以被删除/跳号（题目允许 select 任意 rowId），用 map（dict）更合适。

✔ 为什么需要 columnName → index 的 map？

为了使 select 操作为 O(1)，避免线性查找列名。

✔ 是否需要考虑行删除？

题目没有 deleteRow，所以 rows 的 map 就够了。

✔ 是否需要类型判断？

不需要，题目所有字段都是 string。


class SQL {

    // We can simplify things if we store table-specific data in a class.
    private class Table {
        int autoIncId;
        final int columnSize;
        final Map<Integer, List<String>> rows; // Key row id, value List<String>

        Table(int columnSize) {
            this.autoIncId = 1; // 1-indexed ids
            this.columnSize = columnSize;
            this.rows = new HashMap<>();
        }
    }

    // A HashMap to store all tables (key name, value Table)
    final Map<String, Table> tables;

    public SQL(List<String> names, List<Integer> columns) {
        // We can tell HashMap the capacity we need and the load factor. An
        // optimization.
        // Since we do not add any tables, we can have a load factor of 1, where the
        // HashMap will not increase its capacity unless we insert more than capacity
        // (default load factor of 0.75, which would cause an allocation)
        tables = new HashMap<>(names.size(), 1f);
        for (int i = 0; i < names.size(); i++) {
            tables.put(names.get(i), new Table(columns.get(i)));
        }
    }

    public boolean ins(String name, List<String> row) {
    	//Java 10 引入的局部变量类型推断（local variable type inference）。
		// var x = something;
		// 表示：
		// 让编译器自动推断变量的类型。最终类型在编译期就确定，比方说是 int / String / MyClass 等。
        var table = tables.get(name); 
        if (table == null || table.columnSize != row.size())
            return false;
        table.rows.put(table.autoIncId++, row);
        return true;
    }

    public void rmv(String name, int rowId) {
        var table = tables.get(name);
        if (table == null)
            return;
        table.rows.remove(rowId); // Won't do anything if does not exist
    }

    public String sel(String name, int rowId, int columnId) {
        var table = tables.get(name);
        if (table == null)
            return "<null>";
        var row = table.rows.get(rowId);
        // They don't tell you this but columnId is 1-indexed
        if (row == null || columnId > row.size())
            return "<null>";
        return row.get(columnId - 1);
    }

    public List<String> exp(String name) {
        var table = tables.get(name);
        if (table == null)
            return List.of();

        // EntrySet converts a HashMap into a list of K/V pairs
        var entrySet = table.rows.entrySet();

        // We choose a LinkedList since we will only ever append strings
        List<String> ret = new LinkedList<>();
        for (var row : entrySet) {
            ret.add(row.getKey().toString() + "," + String.join(",", row.getValue()));
        }
        return ret;
    }
}

/**
 * Your SQL object will be instantiated and called as such:
 * SQL obj = new SQL(names, columns);
 * boolean param_1 = obj.ins(name,row);
 * obj.rmv(name,rowId);
 * String param_3 = obj.sel(name,rowId,columnId);
 * List<String> param_4 = obj.exp(name);
 */

===================================================================================================
follow up：
Which approach would you choose if the table might become sparse due to many deletions, and why? Consider the impact on memory usage and performance.

回答：
- 如果表可能变得稀疏，我会选择使用 HashMap 来存储行。
- 因为数组会随着删除变得非常稀疏并浪费大量内存，而 HashMap 只存储真实存在的行，不会占用无意义空间。同时 HashMap 的增删查都是 O(1)，性能不受稀疏程度影响，因此在稀疏场景下明显优于数组结构。






使用 HashMap 或 Map 结构（不是数组）

如果表会变得非常稀疏（很多删除导致大量空位置），最推荐的实现方式是：

使用 HashMap（例如 Map<Integer, Map<String, Object>>）来存储行，而不是用数组或列表来索引行。

🚀 原因一：更少的内存占用
	❌ 数组 / ArrayList 的问题

	如果你用数组模拟数据库表（如 List<Row> 或 Row[] rows）：

	删除行后，会留下大量 null / 索引可能需要不断扩容 / 稀疏时，即使只有少量有效数据，也会占用 非常大的连续内存块


🚀 原因二：增删性能更好

	数组删除必须：

	删除元素、可能要移动后续元素（O(n)）、或者留下 null（造成稀疏）

	HashMap 删除：map.remove(id); // 平均 O(1)



🚀 原因三：随机访问仍然高效

	数组访问：

	rows[id]  // O(1)


	HashMap：

	table.get(id)  // 平均 O(1)


	虽然常数因子略大，但总体仍是 O(1)，对功能没有影响。

🚀 原因四：稀疏表天然适合“键值存储”模型

	稀疏表其实更像：

	NoSQL key-value 数据库

	或稀疏矩阵

	或文件系统 inode table

	这类数据结构都是靠 Map 存储实际存在的行，不存不存在的位置。






