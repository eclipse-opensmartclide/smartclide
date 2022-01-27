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
# Runs a dependency analysis on each SmartCLIDE repo
#--------------------------------------------------------------
#
#--------------------------------------------------------------
# Runs the Eclipse DASH dependency analysis on all SmartCLIDE repositories
#		$1 	: The Path to the folder where the git repos are cloned
#		$2	: The Path to the folder where the results of the analysis will be stored
#--------------------------------------------------------------
#
if [[ $# < 2 ]]; then
	echo "ERROR: Wrong number of arguments"
	echo "Syntax: parseAll.sh <source> <destination>"
	echo '  with:   <source>       = The Path to the folder where the git repos are cloned'
	echo '          <destination>  = The Path to the folder where the results of the analysis will be stored'
	exit
fi

# Grab arguments
SRC_REPO=$1
DST_REPO=$2

./dashAnalysis.sh "Maven" 	"$SRC_REPO/smartclide-api-gateway"						"$DST_REPO/smartclide-api-gateway"
./dashAnalysis.sh "Maven" 	"$SRC_REPO/smartclide-broker"							"$DST_REPO/smartclide-broker"
./dashAnalysis.sh "Maven" 	"$SRC_REPO/smartclide-cicd"								"$DST_REPO/smartclide-cicd"
./dashAnalysis.sh "Docker"	"$SRC_REPO/smartclide-cicd-gitlab"						"$DST_REPO/smartclide-cicd-gitlab"
./dashAnalysis.sh "Maven" 	"$SRC_REPO/smartclide-context"							"$DST_REPO/smartclide-context"
./dashAnalysis.sh "Maven" 	"$SRC_REPO/smartclide-context/context-core"				"$DST_REPO/smartclide-context-core"
./dashAnalysis.sh "Maven" 	"$SRC_REPO/smartclide-context/context-extraction"		"$DST_REPO/smartclide-context-extraction"
./dashAnalysis.sh "Maven" 	"$SRC_REPO/smartclide-context/context-monitoring"		"$DST_REPO/smartclide-context-monitoring"
./dashAnalysis.sh "Maven" 	"$SRC_REPO/smartclide-context/smartclide-monitoring"	"$DST_REPO/smartclide-smartclide-monitoring"
./dashAnalysis.sh "Yarn" 	"$SRC_REPO/smartclide-deployment-extension"				"$DST_REPO/smartclide-deployment-extension"
./dashAnalysis.sh "Docker"	"$SRC_REPO/smartclide-deployment-service"				"$DST_REPO/smartclide-deployment-service"
./dashAnalysis.sh "Yarn" 	"$SRC_REPO/smartclide-design-pattern-selection-theia"	"$DST_REPO/smartclide-design-pattern-selection-theia"
./dashAnalysis.sh "Other" 	"$SRC_REPO/smartclide-docs"								"$DST_REPO/smartclide-docs"
./dashAnalysis.sh "Nodes" 	"$SRC_REPO/smartclide-ide-front-end"					"$DST_REPO/smartclide-ide-front-end"
./dashAnalysis.sh "Other" 	"$SRC_REPO/smartclide-ide-front-end-theme"				"$DST_REPO/smartclide-ide-front-end-theme"
./dashAnalysis.sh "Docker"	"$SRC_REPO/smartclide-jbpm"								"$DST_REPO/smartclide-jbpm"
./dashAnalysis.sh "Yarn" 	"$SRC_REPO/smartclide-perftestgen-theia"				"$DST_REPO/smartclide-perftestgen-theia"
./dashAnalysis.sh "Other" 	"$SRC_REPO/smartclide-RMV"								"$DST_REPO/smartclide-RMV"#
./dashAnalysis.sh "Maven" 	"$SRC_REPO/smartclide-security/Theia-BackEnd" 			"$DST_REPO/smartclide-security"
./dashAnalysis.sh "Maven" 	"$SRC_REPO/smartclide-service-creation"					"$DST_REPO/smartclide-service-creation"
./dashAnalysis.sh "Other" 	"$SRC_REPO/smartclide-Service-Creation-Testing"			"$DST_REPO/smartclide-Service-Creation-Testing"
./dashAnalysis.sh "Yarn" 	"$SRC_REPO/smartclide-service-creation-theia"			"$DST_REPO/smartclide-service-creation-theia"
./dashAnalysis.sh "Other" 	"$SRC_REPO/smartclide-service-discovery-poc"			"$DST_REPO/smartclide-service-discovery-poc"
./dashAnalysis.sh "Maven" 	"$SRC_REPO/smartclide-service-registry-poc/demo" 		"$DST_REPO/smartclide-service-registry-poc"
./dashAnalysis.sh "Docker"	"$SRC_REPO/smartclide-smart-assistant"					"$DST_REPO/smartclide-smart-assistant"
./dashAnalysis.sh "Other" 	"$SRC_REPO/smartclide-task-service-discovery"			"$DST_REPO/smartclide-task-service-discovery"
./dashAnalysis.sh "Maven" 	"$SRC_REPO/smartclide-TD-Interest"						"$DST_REPO/smartclide-TD-Interest"
./dashAnalysis.sh "Maven" 	"$SRC_REPO/smartclide-TD-Principal"						"$DST_REPO/smartclide-TD-Principal"
./dashAnalysis.sh "Maven" 	"$SRC_REPO/smartclide-TD-Reusability-Index"				"$DST_REPO/smartclide-TD-Reusability-Index"
./dashAnalysis.sh "Yarn" 	"$SRC_REPO/smartclide-td-reusability-theia"				"$DST_REPO/smartclide-td-reusability-theia"
