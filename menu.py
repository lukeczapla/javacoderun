#!/usr/bin/python

# Below we have a function which returns a single specific piece of info on the food.
# The 2 input parameters are: 1) The food we want to examine, and 2) the info we
# want to find out about the food.
# NOTE: That this function must be defined BEFORE we ever try to use it.  So it appears
#       first in our code file.
def getFoodInfo (foodItem, infoDesired):
	# This function will use a hash for the database of foods which is created in the
	# main part of the program below.  So in order for this function to "see" this hash
	# (to make it available to this function) we need to define it here as a "global"
	global menuDataBase
	
	# Determine if the food is in the hash at all using the hash function: has_key
	# Also, change all test to lower case, as we stored the database in lowercase in the
	# main part of the program below.
	if (menuDataBase.has_key(foodItem.lower())):
		# The requested food DOES exist in the database... process it
		[food, price, calories, type] = menuDataBase[foodItem.lower()].split('/')
		if   (infoDesired == "p"): return price
		elif (infoDesired == "c"): return calories
		elif (infoDesired == "t"): 
			if   type == "M": return "Meat"
			elif type == "D": return "Dairy"
			elif type == "V": return "Vegetarian"
			else:             return "Unknown type"
		elif (infoDesired == "f"): return food
		else:                      return "ERROR"
		
	# If the "has_key" fails, then the food is NOT in the database
	return "NA"  # Not Available

#========================================
#===== The main program starts here =====
#========================================
# Here, we open the file of the food data, read it in, and close the file.
filePointer=open("menu.txt")
data = filePointer.read()
filePointer.close

print "----- Data as it was read in from the file"
print "      NOTE: All the info from the file was read into a single"
print "            variable named 'data' of type 'string'."
print data, "\n"

lines = data.split('\n')
print "----- Data after split on carriage return"
print "      NOTE: Output is enclosed in square brackets denoting an array"
print "            and that there are 4 string items seperated by commas"
print lines, "\n"

print "----- Data printed as each line via a loop"
# NOTE: While printing each line we will also create a complete DataBase 
#       of the menu which will be stored and used within the program.
#       The database will be a HASH named menuDataBase
#       The KEYS for the hash will be the name of the food.
#       The VALUE of each hash item will be the string of "food/price/calories/type"

# The next line defines the empty dictionary (hash).
# Curly brackets denote a dict (hash) type
menuDataBase = {}  

# We use the variable "i" simply to print the number of the line we are processing.
i = 0

# NOTE: the syntax of the "for" statement below.
#       When you use the keyword "in" the computer expects an "array" in the parenthesis.
#       The "for" loop will walk each item of the array, one at a time, from the first 
#       item to the last.  For each loop over the body of code, the for loop will assign
#       each array item to the variable defined between the "for" and the "in" (ie., line)
for line in (lines):
	print "line[",i,"]  =  ", line
        value = line.split('/')	
	# Here we build our menu database for use in the rest of the program
	# So first, let's extract out the 4 pieces of information for each food
	if (len(value) == 4):
            [food, price, calories, type] = line.split('/')
	# NOTE: Now let's think ahead about a simple problem.  
	#       The text "Burger" is NOT the same as "burger" to a computer.  The capital "B"
	#       is NOT the same as the lower case "b".
	#       But we want to make it easy on the user.  If the user types in "burger" we 
	#       want our program to find Burger in the database.  A simple way to do this is
	#       to store ALL food items in the same case (i.e., say lower case).  THEN, 
	#       whatever the user types in... change THAT also to lower case.  Then our 
	#       compares will always find the food.   All variables of type "string" have a
	#       built-in function named "lower" which will change ALL the characters in the
	#       string to lower case.  (There is also "upper").
	    menuDataBase[food.lower()] = line
	
	# Here we increment "i" simply for use in the print statement.
	i += 1

# IMPORTANT: The next line is no longer indented and thus ends the body of the "for" loop
print ""
print "WELCOME TO OUR CAFE MENU SYSTEM"
print "Please enter the name of a food you wish to examine."
print "Enter 'quit' when you are done."

# NOTE: Remember, the "while" statement is a loop which keeps looping while the
#       expression is TRUE.  Here we set the expression to the static value of 'True'
#       So this will loop F O R E V E R......... Until the user types "quit"
while True:
	f = raw_input('\nEnter food: ')
	if f == "quit":
		print "Bye Bye"
		exit()
		
	if getFoodInfo(f, "f") != "NA":
		print "Yup... ", getFoodInfo(f, "f"), " is in the cafe for $", getFoodInfo(f, "p")
		print "        Calories  = ", getFoodInfo(f, "c")
		print "        Food Type = ", getFoodInfo(f, "t")
	else:
		print "Sorry. ", f, " is not available in the cafe"


