182. Duplicate Emails - Easy

SQL Schema
Write a SQL query to find all duplicate emails in a table named Person.

+----+---------+
| Id | Email   |
+----+---------+
| 1  | a@b.com |
| 2  | c@d.com |
| 3  | a@b.com |
+----+---------+
For example, your query should return the following for the above table:

+---------+
| Email   |
+---------+
| a@b.com |
+---------+
Note: All emails are in lowercase.

******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- 

select Email
from Person
group by Email
having count(*) > 1



=======================================================================================================
method 2:

method:

	- self join
	- 

SELECT DISTINCT p1.Email
FROM Person p1, Person p2
WHERE p1.Email = p2.Email and p1.id != p2.id;

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



