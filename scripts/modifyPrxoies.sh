#!/bin/bash
# Usage Example :  
# ./transform.sh --zipFolderPath /path/to/zip/folder --entryPath file.zip --xpath /path/to/xml/element --newValue "<new>value</new>" --destFolder /path/to/dest/folder

# Parse the command line arguments
	while [[ $# -gt 0 ]]; do
		key="$1"

		case $key in
			--zipFolderPath)
			zipFolderPath="$2"
			shift # past argument
			shift # past value
			;;
			--entryPath)
			entryPath="$2"
			shift # past argument
			shift # past value
			;;
			--xpath)
			xpath="$2"
			shift # past argument
			shift # past value
			;;
			--newValue)
			newValue="$2"
			shift # past argument
			shift # past value
			;;
			--destFolder)
			destFolder="$2"
			shift # past argument
			shift # past value
			;;
			*)    # unknown option
			echo "Unknown option: $1"
			exit 1
			;;
		esac
	done

	# Loop over the zip files in the folder
	for file in "$zipFolderPath"/*.zip; do
		# Extract the zip file to a temporary directory
		tempDir=$(mktemp -d)
		unzip -q "$file" -d "$tempDir"

		# Check if the entryPath exists in the zip file
		if [ -f "$tempDir/$entryPath" ]; then
			# Use xmlstarlet to update the xml file
			xmlstarlet ed -L -u "$xpath" -v "$newValue" "$tempDir/$entryPath"

		fi
	done
	# Create a new zip file with the updated xml file
	zip -qr "$destFolder" "$tempDir"

	# Remove the temporary directory
	rm -rf "$tempDir"
	
	
