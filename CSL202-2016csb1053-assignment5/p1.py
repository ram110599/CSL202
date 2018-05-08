import sys
from bs4 import BeautifulSoup
#from __future__ import print_function

#For containing links
dictionary = {}

#for containing duplicates
dict1 = {}

#variable for counting line
lineCount = 0

#name of the file to be imported from command line
filename = sys.argv[1]

#reading file line by line
with open(sys.argv[1]) as fp:
	#print sys.argv[1].name
	for line in fp:
		lineCount = lineCount + 1					#incrementing the line number
		soup = BeautifulSoup(line,'html.parser')	#using beautifulSoup to find links
		for link in soup.find_all('a'):
			l = []									#appending the useful results in list 
			var = link.get('href')
			l.append(var)
			l.append(link.text)
			l.append(lineCount)
			l.append(link)
			if var in dictionary:					#appending the result in dictionary for same key
				dictionary[var].append(l)
			else:
				dictionary[var] = [l]

 
#variable to count the number of duplicates link
duplicates = 0			
for key,val in dictionary.items():
    #print key," ==> ",
    for items in val:
    	if len(val) > 1:
    		duplicates = duplicates + 1				#increamenting the duplicates variable
    		dict1[key] = 0							#putting the duplicate key in the dictionary 
    		#print ("key is ",key)

#printing how many duplicates found  
#for key,val in dict1.items():
#	print (key, "==>", val)  		
print ("Found", duplicates ,"duplicates")

#printing the href links which are duplicates and its texts and its line number
i = 1
for key,val in dictionary.items():
    #print key," ==> ",
    for items in val:
    	if len(val) > 1:
    		print (i, items[0], " ", '\"',items[1], '\"', "at line no",items[2])	#, items[3]
    		i = i + 1

#for deleting file 
print ("Select hyperlinks that you want to keep.")
print ("		Enter A to keep all, OR")
print ("		Enter F to keep the first one in a set of duplicates, OR")
print ("		Enter the serial numbers (separated by commas) of the links to keep.")

#user input will be stored in response
response = input("Your response: ")

#when response is A
if response == "A":
	print ("Your response is A. So no duplicates have been removed.")

#When response is F
elif response == "F":
	#lineCount1 = 0
	newfileName = filename + ".dedup"
	newfile = open(newfileName,"w")
	with open(filename) as fp:
		for line in fp:
			#lineCount1 = lineCount1 + 1
			soup = BeautifulSoup(line,'html.parser')			#using BeautifulSoup for finding links
			for link in soup.find_all('a'):
				var = link.get('href')
				if var in dict1:
					if dict1[var] == 0:							#if the duplicate link has been encountered 
						dict1[var] = 1							# first time then it is not removed
					else:
						link.extract()							#when the link is encountered more than one times
			content = str(soup)									#then it removes it
			newfile.write(content)
	newfile.close()
	print("Output file is written in ",newfileName)
else:
	l = response.split(",")                                     #splitted the input string
	w = []
	for item in l:
		item = item.replace(" ","")                             #checking for spaces if any by mistake
		w.append(item)
	#print (w)
	line_set = set(w)                                           #converting it into set
	#print("the list is ",l)
	#print("the set is ",line_set)
	i = 1                                                       #variable for looping
	count = 0                                                   #variable for counting number of duplicate links removed
	dict2 = {}                                                  #dictionary for keeping the duplicates which we want to preserve
	for key,val in dictionary.items():
		for items in val:
			if len(val) > 1:                                    #checking for duplicates
				i1 = str(i)
				if i1 in line_set:
					dict2[(items[2],items[3])] = 1              #storing the link and line number as tuple in the form of key in dictionary
					#print (items[2])
				else:
					count = count + 1                           #incrementing the count variable
				i = i + 1

	lineCount1 = 0
	newfileName = filename + ".dedup"
	newfile = open(newfileName,"w")
	with open(filename) as fp:                                  #just the same thing for rread and write
		for line in fp:
			lineCount1 = lineCount1 + 1
			soup = BeautifulSoup(line,'html.parser')
			for link in soup.find_all('a'):
				if (lineCount1,link) in dict2:                  #if the link and the line number is same as in the dict2 then we will keep it
					#print(link,lineCount1)
					pass
				else:                                           #else we will check whether it is duplicate link or not
					var = link.get('href')
					if var in dict1:                            #if duplicate link the remove the anchor tag
						#print (var,lineCount1)
						link.extract()
			content = str(soup)                                 #else no need to do anything
			newfile.write(content)                              #writing it in the output file
	newfile.close()
	print("Removed",count, "hyperlinks. Output file written to",newfileName)
	#print (dict1)
	#print (dict2)
