#!/bin/bash
#*******************************************************************************
# Copyright (c) 2021 The Eclipse Foundation
#
# This program and the accompanying materials
# are made available under the terms of the Eclipse Public License 2.0
# which accompanies this distribution, and is available at
# https://www.eclipse.org/legal/epl-2.0/
#
# SPDX-License-Identifier: EPL-2.0
#
# Contributors:
#     phkrief - initial API and implementation
#*******************************************************************************

#
#--------------------------------------------------------------
# Runs the Eclipse DASH dependency analysis on a (Maven, Yarn or Nodes) repository 
#		$1 	: To select between "Maven" | "Nodes" | "Yarn"
#		$2	: Path of the folder containing the file to parse. The repo is supposed to be located under the $GIT_REPOS folder
#		$3	: Optional - If the path of the folder is not the name of the repo then this third argument can specify the name of the repo
#--------------------------------------------------------------

MAVEN="mvn"
DASH_LIB="libs/org.eclipse.dash.licenses-0.0.1-SNAPSHOT.jar"

if [[ -z GIT_REPOS ]]; then
	echo "Please define the variable env. GIT_REPOS with the path to the folder containing all your GIT clones!"
	exit
fi 

if [[ -z $1 ]]; then
	echo "Syntax: dashAnalysis.sh <type> <repo-name> [<destination>]"
	echo '  with:   <type>         ="Maven" or "Nodes" or "Yarn"'
	echo '          <repo-name>    = Name of the git repo or path to the folder containing the pom.xml file'
	echo '          <destination>  = If the path of the folder is not the name of the repo then this third argument specifies the name of the repo'
	exit
fi

RES="./analysis"

ECHO
ECHO "+=====================================================+"
ECHO "|                                                     |"
ECHO "|                    DASH Analysis                    |"
ECHO "|                                                     |"
ECHO "+=====================================================+"
ECHO

# Grab arguments
KIND_OF=$1 	# To select between "Maven" | "Nodes" | "Yarn"
REPO=$2		# Path of the folder containing the file to parse. The repo is supposed to be located under the $GIT_REPOS folder
DEST=$3		# If the path of the folder is not the name of the repo then this third argument can specify the name of the repo

# Set source directory
SRC_DIR=$GIT_REPOS"/"$REPO

# Set Destination directory
if [[ -n $3 ]]; then
	DEST=$3
else
	DEST=$REPO
fi
DEST_DIR=$RES"/"$DEST

# Start Analysis
if [[ -n $1 ]]; then
	ECHO ":"
	ECHO ": DASH analysis of:......."$SRC_DIR
	ECHO ": Type:..................."$KIND_OF
	ECHO ": Results are available:.."$DEST_DIR
	ECHO ":"
	ECHO ":"
	LOG_FILE=$DEST_DIR"/log.txt"
	ANALYSIS_RESULT_FILE=$DEST_DIR"/03-"$DEST"-results.csv"
	
	# Create the destination Directory
	MKDIR -p $DEST_DIR 
	
	if [ $KIND_OF = "Maven" ]; then
		# Analyse Maven project
		DEP_LIST_FILE=$DEST_DIR"/01-"$DEST"-dependency_list.txt"
		DEP_TREE_FILE=$DEST_DIR"/01-"$DEST"-dependency_tree.txt"
		GREP_RESULT_FILE=$DEST_DIR"/02-grep.txt"
		
		ECHO ": Starting analysis of:"$SRC_DIR
		ECHO ": mvn clean install >> "$LOG_FILE
		$MAVEN clean install -DskipTests -fae -f $SRC_DIR >> $LOG_FILE
		
		ECHO ": mvn dependency:list > "$DEP_LIST_FILE
		$MAVEN dependency:list -f $SRC_DIR > $DEP_LIST_FILE
		
		ECHO ": mvn dependency:tree > "$DEP_TREE_FILE
		$MAVEN dependency:tree -f $SRC_DIR > $DEP_TREE_FILE
		
		ECHO ": grep filter and sort > "$GREP_RESULT_FILE
		grep -ohE '\S+:(system|provided|compile)' $DEP_LIST_FILE | sort | uniq > $GREP_RESULT_FILE
		
		ECHO ": DASH Analysis > " $ANALYSIS_RESULT_FILE
		java -jar $DASH_LIB $GREP_RESULT_FILE -summary $ANALYSIS_RESULT_FILE >> $LOG_FILE
		
	elif [ $KIND_OF = "Nodes" ]; then
		# Analyse Nodes project
		JSON_FILE=$SRC_DIR"/package-lock.json"
		ECHO ": Starting analysis of:"$JSON_FILE
		
		ECHO ": DASH Analysis > " $ANALYSIS_RESULT_FILE
		java -jar $DASH_LIB $JSON_FILE -summary $ANALYSIS_RESULT_FILE >> $LOG_FILE
		
	elif [ $KIND_OF = "Yarn" ]; then
		# Analyse Yarn project
		YARN_FILE=$SRC_DIR"/yarn.lock"
		ECHO ": Starting analysis of:"$YARN_FILE
		
		ECHO ": DASH Analysis > " $ANALYSIS_RESULT_FILE
		java -jar $DASH_LIB $YARN_FILE -summary $ANALYSIS_RESULT_FILE >> $LOG_FILE
	fi
	
	ECHO ":"
	ECHO ": End of Analysis of:"$SRC_DIR
fi


