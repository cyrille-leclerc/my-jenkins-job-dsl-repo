package com.cyrilleleclerc.jenkins.jobs

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Folder
import javaposse.jobdsl.dsl.Job


class MavenApplicationJobsBuilder {
    String applicationName
    String githubOrgName
    String rootFolder

    def build(DslFactory factory) {

        String basePath = "$rootFolder/$applicationName"

        List<Job> jobs = new ArrayList<>()

        Folder groupingFolder = factory.folder(basePath) {
            description "CD Jobs for application $applicationName - hello harpreet"
        }

        // BUILD JOB
        Job buildJob = factory.freeStyleJob("$basePath/build") {
            scm {
                git {
                    remote {
                        github("$githubOrgName/$applicationName")
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
        jobs.add(buildJob)

        // RELEASE JOB
        Job releaseJob = factory.freeStyleJob("$basePath/release") {
            scm {
                git {
                    remote {
                        github("$githubOrgName/$applicationName")
                        credentials("github-credentials")
                    }
                }
            }
            steps {
                shell('$WORKSPACE/mvnw clean release:prepare && $WORKSPACE/mvnw clean release:perform')
            }
            publishers {
                archiveArtifacts('target/**/*.jar')
                archiveJunit 'target/surefire-reports/**/TEST-*.xml'
            }
        }
        jobs.add(releaseJob)

        return jobs
    }
}