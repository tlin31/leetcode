631. Design Excel Sum Formula - Hard

Design the basic function of Excel and implement the function of the sum formula.

Implement the Excel class:

Excel(int height, char width) Initializes the object with the height and the width of the sheet. The sheet is an integer matrix mat of size height x width with the row index in the range [1, height] and the column index in the range ['A', width]. All the values should be zero initially.
void set(int row, char column, int val) Changes the value at mat[row][column] to be val.
int get(int row, char column) Returns the value at mat[row][column].
int sum(int row, char column, List<String> numbers) Sets the value at mat[row][column] to be the sum of cells represented by numbers and returns the value at mat[row][column]. This sum formula should exist until this cell is overlapped by another value or another sum formula. numbers[i] could be on the format:
"ColRow" that represents a single cell.
For example, "F7" represents the cell mat[7]['F'].
"ColRow1:ColRow2" that represents a range of cells. The range will always be a rectangle where "ColRow1" represent the position of the top-left cell, and "ColRow2" represents the position of the bottom-right cell.
For example, "B3:F7" represents the cells mat[i][j] for 3 <= i <= 7 and 'B' <= j <= 'F'.
Note: You could assume that there will not be any circular sum reference.

For example, mat[1]['A'] == sum(1, "B") and mat[1]['B'] == sum(1, "A").
 

Example 1:

Input
["Excel", "set", "sum", "set", "get"]
[[3, "C"], [1, "A", 2], [3, "C", ["A1", "A1:B2"]], [2, "B", 2], [3, "C"]]
Output
[null, null, 4, null, 6]

Explanation
Excel excel = new Excel(3, "C");
 // construct a 3*3 2D array with all zero.
 //   A B C
 // 1 0 0 0
 // 2 0 0 0
 // 3 0 0 0
excel.set(1, "A", 2);
 // set mat[1]["A"] to be 2.
 //   A B C
 // 1 2 0 0
 // 2 0 0 0
 // 3 0 0 0
excel.sum(3, "C", ["A1", "A1:B2"]); // return 4
 // set mat[3]["C"] to be the sum of value at mat[1]["A"] and the values sum of the rectangle range whose top-left cell is mat[1]["A"] and bottom-right cell is mat[2]["B"].
 //   A B C
 // 1 2 0 0
 // 2 0 0 0
 // 3 0 0 4
excel.set(2, "B", 2);
 // set mat[2]["B"] to be 2. Note mat[3]["C"] should also be changed.
 //   A B C
 // 1 2 0 0
 // 2 0 2 0
 // 3 0 0 6
excel.get(3, "C"); // return 6
 

Constraints:

1 <= height <= 26
'A' <= width <= 'Z'
1 <= row <= height
'A' <= column <= width
-100 <= val <= 100
1 <= numbers.length <= 5
numbers[i] has the format "ColRow" or "ColRow1:ColRow2".
At most 100 calls will be made to set, get, and sum.
 

******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************

fastest solution:



当前节点的值 = children 的值之和

例如：C1 = SUM(A1, B1)

图结构：C1 ──> A1， C1 ──> B1

C1.children = [A1, B1] 	// 因为children != null，说明这是一个公式单元格
A1.children = null   	// children是null，A1.value就是之前被set的值
B1.children = null
A1.parents = [C1]
B1.parents = [C1]

invalidate举例

	C1 = SUM(A1, B1)
	D1 = SUM(C1)

	修改 A1：

	A1.invalidate()
	→ C1.invalidate()
	→ D1.invalidate()

	这样下次 get(D1) 时：会重新算 C1 & 再重新算 D1

	👉 这是典型的“反向依赖失效传播”




class Excel {
    public class Node{ 
	    List<Node> parents;   // 所有“依赖我”的节点（反向边）
	    List<Node> children;  // 我依赖的节点（正向边）
	    int val;              // 当前缓存的值
	    boolean valCheck;     // val 是否是最新的（缓存是否有效）
        
        public Node(){
            this.parents = new ArrayList<>();
        }
        public int getValue(){
            if(children == null)
            	return val; 

            // 是公式单元格，且缓存有效
            if(valCheck)
            	return val; 


            int total = 0;
            // 是公式单元格，需要重新计算，递归计算所有依赖节点，本质是 DFS on DAG
            for(Node child: children){
                total += child.getValue();
            }
            valCheck = true;
            return val = total; 
        }

        //当某个节点被修改时，所有“依赖它的节点”都变脏 (set & sum only)
        public void invalidate(){
            valCheck = false; 
            if(parents != null){
                for(Node p: parents){ 
                	p.invalidate(); 
                }
            }
        }
    }

    Node[][] sheet;

    public Excel(int height, char width) {
        sheet = new Node[height][width - 'A' + 1];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < sheet[0].length; j++){
            	//fill with null nodes
                sheet[i][j] = new Node();
            } 
        }
    }

    public void set(int row, char column, int val) {
        sheet[row - 1][column - 'A'].invalidate(); 		//因为已经被改变了
        sheet[row - 1][column - 'A'].val = val; 		//reset value for cell
        sheet[row - 1][column - 'A'].children = null; 	//all previous references erased
    }

    public int get(int row, char column) { 
        return sheet[row - 1][column - 'A'].getValue();
    }

    public int sum(int row, char column, String[] numbers) {
        Node n = sheet[row - 1][column - 'A'];

        n.children = new ArrayList<>();// list of beneficiaries

        n.invalidate();//reset this cell if previously set

        for(String cell: numbers){ 
            int colonIdx = cell.indexOf(":");
            if(colonIdx == -1){ 
            	//":" doesn't exist
                int r = Integer.parseInt(cell.substring(1));
                int c = cell.charAt(0) - 'A';
                Node checkCell = sheet[r - 1][c];
                checkCell.parents.add(n); //reverse edge add 
                n.children.add(checkCell);
            }

            else{ 
            	//":" exists
            	//for all cells in XX:YY range add to children for n
                int r1 = Integer.parseInt(cell.substring(1,colonIdx));
                int r2 = Integer.parseInt(cell.substring(colonIdx + 2)); 
                int c1 = cell.charAt(0) - 'A';
                int c2 = cell.charAt(colonIdx + 1) - 'A';
                for(int i = r1 - 1; i < r2; i++){
                    for(int j = c1; j <= c2; j++){
                        Node checkCell = sheet[i][j];
                        checkCell.parents.add(n);
                        n.children.add(checkCell);
                    }
                }
            }
        }
        return n.getValue();
    }
}


/**
 * Your Excel object will be instantiated and called as such:
 * Excel obj = new Excel(height, width);
 * obj.set(row,column,val);
 * int param_2 = obj.get(row,column);
 * int param_3 = obj.sum(row,column,numbers);
 */

===================================================================================================
Method 1:

Method:

❓1. 如何表示公式？

	用 Map<Cell, count>

	表示：这个单元格 = 若干其他单元格 × 次数

	例子：

	SUM(A1:B2)
	= A1 + A2 + B1 + B2

❓2. 如何处理更新？

	👉 依赖反向传播

	一个单元格变化

	所有「引用它的公式单元格」都要更新


class Cell {
    int val; // 当前值
    Map<Cell, Integer> formula; // 依赖的单元格及次数
}
formula == null → 普通值

formula != null → SUM 公式

Stats:

	- 
	- 

public class Excel {
    Cell[][] table;

    public Excel(int H, char W) {
        table = new Cell[H+1][W-'A'+1];
    }
    
    public void set(int r, char c, int v) {
        if(table[r][c-'A'] == null) table[r][c-'A'] = new Cell (v); 
        else table[r][c-'A'].setValue(v); 
    }
    
    public int get(int r, char c) {
        if( table[r][c-'A'] == null) return 0;
        else return table[r][c-'A'].val;       
    }
    
    public int sum(int r, char c, String[] strs) {
        if (table[r][c-'A'] == null) table[r][c-'A'] = new Cell(strs);
        else {
            table[r][c-'A'].setFormula(strs);
        }
        return table[r][c-'A'].val;
    }
    
    
    private class Cell{
        int val=0;
        HashMap<Cell, Integer> formula=new HashMap<>();
        HashSet<Cell> dependentCells=new HashSet<>();
        
        public Cell(int val){
            setValue(val); 
        }
        public Cell(String[] formulaStr){
            setFormula(formulaStr);
        }
        
        public void setValue(int val) {           
            removeFormulaCells();
            formula.clear();
            updateDependentCells(val);            
            this.val = val;
        }
        
        public void setFormula(String[] formulaStr){
            removeFormulaCells();
            formula.clear();            
            int newVal = 0;
            for(String str : formulaStr){
                if (str.indexOf(":")<0){
                    int[] pos = getPos(str);
                    newVal += addFormulaCell(pos[0], pos[1]);  
                } else {
                    String[] pos = str.split(":");
                    int[] startPos = getPos(pos[0]);
                    int[] endPos = getPos(pos[1]);
                    for(int r = startPos[0]; r<=endPos[0]; r++){
                        for(int c = startPos[1]; c<=endPos[1]; c++){
                            newVal += addFormulaCell(r, c);
                        }
                    }
                }
            }
            updateDependentCells(newVal);
            this.val=newVal;
        }
        
        private int[] getPos(String str){
            int[] pos = new int[2];
            pos[1]=str.charAt(0)-'A';
            pos[0]=Integer.parseInt(str.substring(1));
            return pos;
        }
        
        private int addFormulaCell(int r, int c){
            if(table[r][c] == null) table[r][c] = new Cell(0);
            Cell rangeCell = table[r][c];                            
            formula.put(rangeCell, (formula.containsKey(rangeCell)? formula.get(rangeCell) : 0)+1);
            rangeCell.dependentCells.add(this);
            return rangeCell.val;
        }
        
        private void removeFormulaCells(){
            if (!this.formula.isEmpty()) {
                for(Cell rangeCell : this.formula.keySet()){
                    rangeCell.dependentCells.remove(this);
                }
            }    
        }
        //recursive method
        private void updateDependentCells(int newVal){
            int delta = newVal-this.val;
            for(Cell cell : dependentCells){
                int cellNewVal =  cell.val + delta*cell.formula.get(this);
                cell.updateDependentCells(cellNewVal);
            }
            this.val = newVal;
        }
    }
}


六、复杂度分析
操作	复杂度
set	O(1)
get	O(1)
sum	O(引用的 cell 数量)

Excel 规模小（≤26×100），可接受


面试 Follow-up（非常重要）
❓1. 如果存在循环依赖怎么办？

👉 本题 保证无循环
现实中要：

拓扑排序

DFS + onPath 检测环

❓2. 如果单元格变化要实时通知下游？

👉 建立 反向依赖表

Cell → 被谁引用

类似 Excel 实时刷新

❓3. 如果公式很多，如何优化？

缓存

DAG + lazy update

批量 recompute

❓4. 如果要支持 AVG / MAX / MIN？

抽象成 Expression Tree

Strategy Pattern