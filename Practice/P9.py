#Program for finding duplicates

l = ['ram','kr','ram','kr','shyam','kr','no','com','shyam','no','ram','ram','Krishna','Krishna','shyam']
w = []			#sorted list of l
dup = []		#for storing duplicates

for item in sorted(l):
	w.append(item)

'''for item in w:
	print item'''
#print "Item in duplicate"

my_list_len = len(w)

for i in range(0,my_list_len):
	flag = 0
	if w[i] == "00_00":
		continue
	for j in range(i+1,my_list_len):
		if w[i] == w[j]:
			dup.append(w[j])
			flag = 1
			w[j] = "00_00"
		else:
			break
	if flag == 1:
		dup.append(w[i])
		flag = 0

#for printing items in duplicate list
for item in dup:
	print item


