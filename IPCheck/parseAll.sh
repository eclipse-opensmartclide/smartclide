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
./dashAnalysis.sh "Maven" 	"smartclide-api-gateway"
./dashAnalysis.sh "Maven" 	"smartclide-broker"
./dashAnalysis.sh "Maven" 	"smartclide-cicd"
./dashAnalysis.sh "Maven" 	"smartclide-context"
#./dashAnalysis.sh "Yarn" 	"smartclide-design-pattern-selection-theia"
#./dashAnalysis.sh "Nodes" 	"smartclide-ide-front-end"
#./dashAnalysis.sh "Nodes" 	"smartclide-perftestgen-theia"
./dashAnalysis.sh "Maven" 	"smartclide-security/Theia-BackEnd" "smartclide-security"
./dashAnalysis.sh "Maven" 	"smartclide-service-creation"
#./dashAnalysis.sh "Yarn" 	"smartclide-service-creation-theia"
./dashAnalysis.sh "Maven" 	"smartclide-TD-Interest"
./dashAnalysis.sh "Maven" 	"smartclide-TD-Reusability-Index"
./dashAnalysis.sh "Maven" 	"smartclide-TD-Principal"
#./dashAnalysis.sh "Yarn" 	"smartclide-td-reusability-theia"
