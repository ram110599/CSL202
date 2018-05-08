Name = Ram Krishna
Entry No = 2016csb1053
Course = CSL202
========================================================================================================================================================
For running:
    $python3 p1.py abc.html

Here "abc.html" is sample html file to be passed through command line. "p1.py" is the name of the main file in which program is written. The code is in python 3.
========================================================================================================================================================
A snapshot of a sample run:

Output:
Found 6 duplicates
1 http://example.com/elsie   " Elsie " at line no 6
2 http://example.com/elsie   " Easlsie " at line no 6
3 http://example.com/elsie   " Elsie " at line no 9
4 http://example.com/elsie   " Easlsie " at line no 11
5 http://example.com/lacie   " Lacie " at line no 7
6 http://example.com/lacie   " Lacie " at line no 10
Select hyperlinks that you want to keep.
		Enter A to keep all, OR
		Enter F to keep the first one in a set of duplicates, OR
		Enter the serial numbers (separated by commas) of the links to keep.
Your response: 3,4
Removed 4 hyperlinks. Output file written to abc.html.dedup
========================================================================================================================================================
