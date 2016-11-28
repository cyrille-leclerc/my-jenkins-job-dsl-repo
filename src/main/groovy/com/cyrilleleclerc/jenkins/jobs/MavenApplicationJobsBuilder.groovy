package com.cyrilleleclerc.jenkins.jobs

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job


class MavenApplicationJobsBuilder {
    String applicationName
    String githubOrgName
    String rootFolder

    def build(DslFactory factory) {

        String basePath = "$rootFolder/$applicationName"

        factory.println("BEFORE add folder $basePath")

        factory.folder(basePath) {
            description "CD Jobs for $applicationName"
        }

        factory.println("BEFORE add build job")


        factory.freeStyleJob("$basePath/build") {
            scm {
                github("$githubOrgName/$applicationName")
            }
            triggers {
                scm 'H/5 * * * *'
            }
            steps {
                shell('$WORKSPACE/mvnw clean package')
            }
        }

        factory.println("BEFORE add release job")


        factory.freeStyleJob("$basePath/release") {
            scm {
                github("$githubOrgName/$applicationName")
            }
            steps {
                shell('$WORKSPACE/mvnw clean release:prepare && $WORKSPACE/mvnw clean release:perform')
            }
        }
    }
}