import sys
#name of the file to be imported from command line
filename = sys.argv[1]

newfileName = "facts_and_rules.pl"
newfile = open(newfileName,"w")

dict1 = {'OS' : 'os', 'Machine' : 'machine', 'SoftwareApp' : 'softwareapp'}
flag = 0			#OS - 1, Machine - 2, SoftwareApp - 3	
count = 0
currentWord = 'a'
id1 = 0

content = ":-style_check(-discontiguous).\n"
newfile.write(content)

with open(sys.argv[1]) as fp:
	#print sys.argv[1].name

	for line in fp:
		line = line.strip('\t')						#removing all tabs in a line 
		line = line.strip('\n')						#removing all new line characters in a line
		words = line.split(':')						#spliting the line according to colon
		words = filter(None, words)					#removing empty strings from the lists
		if len(words) == 1:							
			if words[0] == 'OS':					#checking for OS
				flag = 1
				currentWord = words[0].lower()

			elif words[0] == 'Machine':				#checking for machine in the file
				flag = 2
				currentWord = words[0].lower()

			elif words[0] == 'SoftwareApp':			#checking for SoftwareApp
				flag = 3
				currentWord = words[0].lower()

			count = count + 1
		elif len(words) > 1:
			count = count + 1
			if flag == 1:
				word = words[1].lstrip()
				word = word.lower()
				if count == 2:
					id1 = word

				elif count == 5:
					word = word.split()
					word = word[0]

			elif flag == 2:		#for machine
				word = words[1].lstrip()
				word = word.lower()
				if count == 2:
					id1 = word

				elif count == 5:					#Converting RAM into MB
					word = word.split()
					word = word[0]
					word = int(word)
					word = word * 1024
					word = str(word)
					
				elif count == 6:
					word = word.split()
					if word[1].upper()=='TB':				#Converting disk in GB if it is given in TB
						word = word[0]
						word = int(word)
						word = word * 1024
						word = str(word)
					else:
						word = word[0]

				elif count == 7:
					word = word.split()
					word = word[0]

			elif flag == 3:		#For softwareapp
				word = words[1].lstrip()
				word = word.lower()
				if count == 2:
					id1 = word

				elif count == 3:
					word = word.replace(" ","_")

				elif count == 5 or count == 6 or count == 7:
					word = word.split()
					word = word[0]

					#print count
					#print word

			if count > 2:
				content = currentWord + "_" + words[0].lower() + "(" + id1 + "," + word + ").\n"
				newfile.write(content)
					

				#print word
				#print count


		else:
			count = 0
#For writing query in prolog file

content = "ram_check(ID1,ID2) :- softwareapp_min_ram(ID1,X), machine_ram(ID2,Y),X=<Y.\n"
newfile.write(content)
content = "disk_check(ID1,ID2) :- softwareapp_min_disk(ID1,X), machine_disk(ID2,Y), X=<Y.\n"
newfile.write(content)
content = "cpu_check(ID1,ID2) :- softwareapp_min_cpu(ID1,X), machine_cpu(ID2,Y), X=<Y.\n"
newfile.write(content)
content = "all_from_first_in_second(List1, List2) :- forall(member(Element,List1), member(Element,List2)).\n"
newfile.write(content)
content = "check_lib(ID1,ID2) :- softwareapp_libs(ID1,X),machine_os(ID2,Y),os_provides_libs(Y,Z),all_from_first_in_second(X,Z).\n"
newfile.write(content)
content = "check_os(ID1,ID2) :- softwareapp_os(ID1,X), machine_os(ID2,Y), member(Y,X).\n"
newfile.write(content)
content = "check(ID1,ID2) :- ram_check(ID1,ID2), disk_check(ID1,ID2), cpu_check(ID1,ID2), check_os(ID1,ID2), check_lib(ID1,ID2).\n"
newfile.write(content)
content = "check1(ID1,ID2) :-findall(ID2,check(ID1,ID2),Z),writeln(Z).\n"
newfile.write(content)
content = "check2(ID1) :- findall(ID2,check(ID1,ID2),Z),writeln(Z).\n"
newfile.write(content)
