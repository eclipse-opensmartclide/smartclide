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
#		$2	: The Path to the repository/folder where the config file is located
#		$3	: Name to use (usually the name of the repo) 
#--------------------------------------------------------------

MAVEN="mvn"
DASH_LIB="./libs/org.eclipse.dash.licenses-0.0.1-SNAPSHOT.jar"

if [[ $# < 3 ]]; then
	echo "ERROR: Wrong number of arguments"
	echo "Syntax: dashAnalysis.sh <type> <source> <destination>"
	echo '  with:   <type>         ="Maven" or "Nodes" or "Yarn"'
	echo '          <source>       = Path to the folder containing the config file'
	echo '          <destination>  = Path of the folder where the results will be stored'
	exit
fi

echo
echo "+=====================================================+"
echo "|                                                     |"
echo "|         Eclipse DASH License Tool Analysis          |"
echo "|                                                     |"
echo "+=====================================================+"
echo

# Grab arguments
KIND_OF=$1 		# To select between "Maven" | "Nodes" | "Yarn"
SRC_DIR=$2		# Path to the folder containing the config file
DEST_DIR=$3		# Path of the folder where the results will be stored

echo ":"
echo ": DASH analysis of:.........."$SRC_DIR
echo ": Type:......................"$KIND_OF
echo ": Results are available in:.."$DEST_DIR
echo ":"
echo ":"
mkdir -p $DEST_DIR 

LOG_FILE="$DEST_DIR/log.txt"
ANALYSIS_RESULT_FILE="$DEST_DIR/03-dash_analysis.csv"

# Pull changes from a remote repo
echo ": Pull changes from a remote repo into:"$SRC_DIR
echo ": Pull changes from a remote repo into:"$SRC_DIR > $LOG_FILE
git pull >> $LOG_FILE

# Create the destination Directory

if [ $KIND_OF = "Maven" ]; then
	# Analyse Maven project
	DEP_LIST_FILE="$DEST_DIR/01-dependency_list.txt"
	DEP_TREE_FILE="$DEST_DIR/01-dependency_tree.txt"
	GREP_RESULT_FILE="$DEST_DIR/02-grep.txt"
	
	echo ": Starting analysis of: "$SRC_DIR
	echo ": Starting analysis of: "$SRC_DIR >> $LOG_FILE
	
	echo ": mvn clean install >> "$LOG_FILE
	echo ": mvn clean install >> "$LOG_FILE >> $LOG_FILE
	$MAVEN clean install -DskipTests -fae -f $SRC_DIR >> $LOG_FILE
	
	echo ": mvn dependency:list > "$DEP_LIST_FILE
	echo ": mvn dependency:list > "$DEP_LIST_FILE >> $LOG_FILE
	$MAVEN dependency:list -f $SRC_DIR > $DEP_LIST_FILE
	
	echo ": mvn dependency:tree > "$DEP_TREE_FILE
	echo ": mvn dependency:tree > "$DEP_TREE_FILE >> $LOG_FILE
	$MAVEN dependency:tree -f $SRC_DIR > $DEP_TREE_FILE
	
	echo ": grep filter and sort > "$GREP_RESULT_FILE
	echo ": grep filter and sort > "$GREP_RESULT_FILE >> $LOG_FILE
	grep -ohE '\S+:(system|provided|compile)' $DEP_LIST_FILE | sort | uniq > $GREP_RESULT_FILE
	
	echo ": DASH Analysis > " $ANALYSIS_RESULT_FILE
	echo ": DASH Analysis > " $ANALYSIS_RESULT_FILE >> $LOG_FILE
	java -jar $DASH_LIB $GREP_RESULT_FILE -summary $ANALYSIS_RESULT_FILE >> $LOG_FILE
	
elif [ $KIND_OF = "Nodes" ]; then
	# Analyse Nodes project
	JSON_FILE="$SRC_DIR/package-lock.json"
	echo ": Starting analysis of: "$JSON_FILE
	echo ": Starting analysis of: "$JSON_FILE >> $LOG_FILE
	
	echo ": DASH Analysis > " $ANALYSIS_RESULT_FILE
	echo ": DASH Analysis > " $ANALYSIS_RESULT_FILE >> $LOG_FILE
	java -jar $DASH_LIB $JSON_FILE -summary $ANALYSIS_RESULT_FILE >> $LOG_FILE
	
elif [ $KIND_OF = "Yarn" ]; then
	# Analyse Yarn project
	YARN_FILE="$SRC_DIR/yarn.lock"
	echo ": Starting analysis of: "$YARN_FILE
	echo ": Starting analysis of: "$YARN_FILE >> $LOG_FILE
	
	echo ": DASH Analysis > " $ANALYSIS_RESULT_FILE
	echo ": DASH Analysis > " $ANALYSIS_RESULT_FILE >> $LOG_FILE
	java -jar $DASH_LIB $YARN_FILE -summary $ANALYSIS_RESULT_FILE >> $LOG_FILE
fi

echo ":"
echo ": End of Analysis of:"$SRC_DIR
echo ": End of Analysis of:"$SRC_DIR >> $LOG_FILE
 