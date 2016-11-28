package com.cyrilleleclerc.jenkins.jobs

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job


class MavenApplicationJobsBuilder {
    String applicationName
    String githubOrgName
    String rootFolder

    List<Job> build(DslFactory factory) {

        String basePath = "$rootFolder/$applicationName"

        List<Job> jobs = new ArrayList<>()

        factory.println("BEFORE add folder $basePath")

        jobs.add(factory.folder(basePath) {
            description "CD Jobs for $applicationName"
        })

        factory.println("BEFORE add build job")


        jobs.add(factory.freeStyleJob("$basePath/build") {
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

        factory.println("BEFORE add release job")


        jobs.add(factory.freeStyleJob("$basePath/release") {
            scm {
                github("$githubOrgName/$applicationName")
            }
            steps {
                shell('$WORKSPACE/mvnw clean release:prepare && $WORKSPACE/mvnw clean release:perform')
            }
        })

        return jobs
    }
}