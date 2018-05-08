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

				elif count == 9:
					for w in word:
						

			elif flag == 2:
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

			elif flag == 3:
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

