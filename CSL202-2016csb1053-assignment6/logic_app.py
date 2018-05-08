import subprocess

query = raw_input("Enter 1 for query1 and 2 for query2.\n")
if query == "1":
	app_id = raw_input("Enter the application Id.\n")
	
	#app_id = app_id.split("\n")
	command = "swipl -q -s facts_and_rules.pl -g " +"check2(" + app_id +"),halt."
	#print command
	command = command.split(" ")
	p = subprocess.Popen(command, stdout=subprocess.PIPE)
	output = p.communicate()[0]
	if len(output) > 3:
		print "The following machine Id are suitable."
		print(output)
	else:
		print "No machine found."

elif query == "2":
	app_id = raw_input("Enter the application Id.\n")
	mac_id = raw_input("Enter the machine Id.\n")
	#app_id = app_id.split("\n")
	command = "swipl -q -s facts_and_rules.pl -g " +"check1(" + app_id + "," + mac_id + "),halt."
	#print command
	command = command.split(" ")
	p = subprocess.Popen(command, stdout=subprocess.PIPE)
	output = p.communicate()[0]
	if len(output) > 3:
		print "Yes the software can run on that machine."
		print(output)
	else:
		print "No machine can't run that software."
