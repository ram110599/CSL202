#Playing with dictionary

dictionary = {}
l = ['a','b','c','a','b','c']
count = 0
for item in l:
	if item in dictionary:
		dictionary[item].append(count)
	else:
		dictionary[item] = [count]
		print item
	count = count + 1

for key,val in dictionary.items():
	print key, " --> "
	for items in val:
		print items, " ",
	print