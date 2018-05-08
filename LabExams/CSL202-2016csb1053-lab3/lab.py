import sys

#global l 
l = []																			#list to store all the words 
flag = 1																		#for checking well formatted or not
flag1 = 0																		#for checking opening tag
flag2 = 0																		#for checking closing tag
count = 0																		#variable for checking root element is present or not

try:
	with open(sys.argv[1],"r") as fileobj:										#Opening file
		for line in fileobj:													#Reading file line by line
			w = []																#list to store all the characters
			
			for ch in line:														#reading file character by character
				if ch=="<":
					flag1 = 1													#starting of anchor tag
					
				elif flag1==1 and (ch==" " or ch==">" or ch=="?"):				#if space or closing tag occurs
					flag1 = 0
					#print l
					if len(w)==0:												#if it contains empty list
						pass
					else:
						word = ''.join(w)										#joining the characters and converting it into strings
						if word.isalpha():										#Checking whether word contains only alphabets or not					
							w[:] = []
							if flag2 == 1:										#if closing tag occurs
								flag2 = 0										# Making flag = 0
								length = len(l)
								if length == 0:									#If a closing tag occurs before opening tag
									#print "The length of the list is zero."
									flag = 0									#if flag2 ==1 and list doesn't contain any elements 
								else:
									word2 = l[length-1]
									
									if word2 == word:							# if the last opening tag is equal to closing tag or not
										l.pop()									#removing that element from the main list
									else:
										flag = 0								#if last opening tag does not meet closing tag then not well formated
							else:
								if len(l) == 0 and count == 0: 	
									count = 1
								elif len(l)==0 and count > 0:					#Checking for root element in the xml file
									flag = 0
								l.append(word)									#appending the word to the list if it is opening tag
						else:
							flag = 0											#Word contain other letter than alphabets	
											
					
				elif flag1==1:
					if ch=="/":
						flag2 = 1												#Closing tag found
						pass
					#elif flag2 = 1:	
					else:
						w.append(ch)
				
				else:
					pass
					#print "the else character is ", ch
				
	if flag == 1:																#If all the conditions are fine then 
		print "Well formated."
	
	else:
		print "Not well formated."
				
		       

except IndexError:
	print "Please give sample xml file."
	
except:
	print "Please check the sample file is present or not."  
