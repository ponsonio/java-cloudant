#!groovy

/*
 * Copyright © 2016 IBM Corp. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

stage('Build') {
    // Checkout, build and assemble the source and doc
    node {
        checkout scm
        sh './gradlew clean assemble'
        stash name: 'built'
    }
}

stage('QA') {
    node {
        unstash name: 'built'
        // findBugs
        try {
            sh './gradlew -Dfindbugs.xml.report=true findbugsMain'
        } finally {
            step([$class: 'FindBugsPublisher', pattern: '**/build/reports/findbugs/*.xml'])
        }
        // tests
        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'clientlibs-test', usernameVariable: 'DB_USER', passwordVariable: 'DB_PASSWORD']]) {
            try {
                sh './gradlew -Dtest.with.specified.couch=true -Dtest.couch.username=$DB_USER -Dtest.couch.password=$DB_PASSWORD -Dtest.couch.host=clientlibs-test.cloudant.com -Dtest.couch.port=443 -Dtest.couch.http=https -Dtest.couch.ignore.compaction=true -Dtest.couch.auth.headers=true cloudantServiceTest'
            } finally {
                junit '**/build/test-results/*.xml'
            }
        }
    }
}
