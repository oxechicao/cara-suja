#!/bin/bash
folder_path="./jsons"
files_array=("$folder_path"/*)
array_size=${#files_array[@]}

function printFiles() {
	index=0
	for filename in "${files_array[@]}"; do
	  # Use basename to get just the filename without the path
	  echo "[$index] $(basename "$filename")"
		((index+=1))
	done
}

function print_no_file_found() {
	echo "No file defined. Chose one of them:"
	printFiles
	exit 1
}

option=$1
filename=""

if [ -n "$option" ]; then
	if [ "$option" = "-h" ]; then
		echo "Chose of them:"
		printFiles
	fi

	if [ "$option" -lt 0 ]; then
		echo "a"
		print_no_file_found
	else
		if [ "$option" -gt "$array_size" ]; then
			echo "b"
			print_no_file_found
		fi
	fi

	if [[ $option =~ ^[0-9]+$ ]]; then
      	filename=${files_array[$option]}
    else
		echo "c"
      	print_no_file_found
    fi
else
	echo "d"
	print_no_file_found
fi

curl http://localhost:8080/api/v1/users \
	-X POST \
	-H "Content-type: application/json" \
	-d @$filename
