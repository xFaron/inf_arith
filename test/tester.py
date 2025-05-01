import sys
import subprocess
import os

def main():
	os.chdir("../")
	os.system("ant compile")

	input_type = input("Enter input type <int/float>: ")
	operation = input("Enter operation <add/sub/mul/div>")
	num1 = input("Enter number 1: ")
	num2 = input("Enter number 2: ")

	os.chdir("./build")
	os.system(f"java MyInfArith {input_type} {operation} {num1} {num2}")

if __name__ == '__main__':
	main()
