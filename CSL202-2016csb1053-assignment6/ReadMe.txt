Name = Ram Krishna
Entry No = 2016csb1053
Course = CSL202
========================================================================================================================================================
For running:
    $python facts_gen.py machine_info.yaml
    $python logic_app.py

Here "machine_info.yaml" is sample yaml file to be passed through command line. "fact_gen.py" is the name of the file to generate the rules and facts in prolog. The code is in python 2. "logic_app.py" is the program to invoke prolog file.
========================================================================================================================================================
A snapshot of a sample run:

Enter 1 for query1 and 2 for query2.
2
Enter the application Id.
301
Enter the machine Id.
120
No machine can't be found.

========================================================================================================================================================
A snapshot of a sample run:
Enter 1 for query1 and 2 for query2.
1
Enter the application Id.
300
The following machine Id are suitable.
[120]

=========================================================================================================================================================
Assumption:
1- Machine RAM will always be in GB and will be of int type
2- Software app RAM will be in MB always and integer 
3- Run facts_gen.py first. Then logic_app.py
4- Don't change the format of the yaml file as sir has specified.
=========================================================================================================================================================
Logic of the program:

I have made the facts_gen.py file to create the facts and rules for query. For this I have read the yaml file line by line and then converted into facts. I have stored the id with all the attributes of the OS, Machine, SoftwareApp. After that I used the subprocess to invoke the prolog file. I have written in python2.7.
