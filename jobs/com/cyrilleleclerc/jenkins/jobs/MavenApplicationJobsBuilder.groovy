package com.cyrilleleclerc.jenkins.jobs

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job


class MavenApplicationJobsBuilder {
    String applicationName
    String githubOrgName
    String rootFolder

    Job build(DslFactory factory) {

        String basePath = "$rootFolder/$applicationName"

        List<Job> jobs = new ArrayList<>()

        println("BEFORE add folder $basePath")

        jobs.add(factory.folder(basePath) {
            description "CD Jobs for $applicationName"
        })

        println("BEFORE add build job")


        jobs.add(freeStyleJob("$basePath/build") {
            scm {
                github("$githubOrgName/$applicationName")
            }
            triggers {
                scm 'H/5 * * * *'
            }
            steps {
                shell('$WORKSPACE/mvnw clean package')
            }
        })

        println("BEFORE add release job")


        jobs.add(freeStyleJob("$basePath/release") {
            scm {
                github("$githubOrgName/$applicationName")
            }
            steps {
                shell('$WORKSPACE/mvnw clean release:prepare && $WORKSPACE/mvnw clean release:perform')
            }
        })

        return jobs.last()
    }
}