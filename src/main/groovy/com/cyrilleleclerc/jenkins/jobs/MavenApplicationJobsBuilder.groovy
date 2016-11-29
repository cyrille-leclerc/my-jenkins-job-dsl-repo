package com.cyrilleleclerc.jenkins.jobs

import javaposse.jobdsl.dsl.DslFactory


class MavenApplicationJobsBuilder {
    String applicationName
    String githubOrgName
    String rootFolder

    def build(DslFactory factory) {

        String basePath = "$rootFolder/$applicationName"

        factory.folder(basePath) {
            description "CD Jobs for $applicationName"
        }

        // BUILD JOB
        factory.freeStyleJob("$basePath/build") {
            scm {
                git {
                    remote {
                        github("$githubOrgName/$applicationName", 'master')
                        credentials("github-credentials")
                    }
                }
            }
            triggers {
                githubPush()
            }
            steps {
                shell('$WORKSPACE/mvnw clean package')
            }
            publishers {
                archiveArtifacts('target/**/*.jar')
                archiveJunit 'target/surefire-reports/**/TEST-*.xml'
            }
        }

        // RELEASE JOB
        factory.freeStyleJob("$basePath/release") {
            scm {
                github("$githubOrgName/$applicationName")
            }
            steps {
                shell('$WORKSPACE/mvnw clean release:prepare && $WORKSPACE/mvnw clean release:perform')
            }
            publishers {
                archiveArtifacts('target/**/*.jar')
                archiveJunit 'target/surefire-reports/**/TEST-*.xml'
            }
        }
    }
}