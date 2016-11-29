package com.cyrilleleclerc.jenkins.jobs

import javaposse.jobdsl.dsl.Job
import javaposse.jobdsl.dsl.JobParent
import spock.lang.Specification

@Mixin(JobSpecMixin)
class MavenApplicationJobsBuilderSpec extends Specification {

    JobParent jobParent = createJobParent()
    MavenApplicationJobsBuilder builder

    def setup() {
        builder = new MavenApplicationJobsBuilder(
                applicationName: 'spring-petclinic',
                githubOrgName: 'spring-projects',
                rootFolder: 'test-applications'
        )
    }

    void 'test XML output'() {
        when:
        List<Job> jobs = builder.build(jobParent)

        then:
        Job buildJob = jobs.get(0)
        buildJob.name == "test-applications/spring-petclinic/build"
        with(buildJob.node) {
            name() == 'project'
            triggers.'com.cloudbees.jenkins.GitHubPushTrigger'.spec
            scm.userRemoteConfigs.'hudson.plugins.git.UserRemoteConfig'.url.text() == 'https://github.com/spring-projects/spring-petclinic.git'
        }

        Job releaseJob = jobs.get(1)
        releaseJob.name == "test-applications/spring-petclinic/release"
        with(releaseJob.node) {
            name() == 'project'
            scm.userRemoteConfigs.'hudson.plugins.git.UserRemoteConfig'.url.text() == 'https://github.com/spring-projects/spring-petclinic.git'
        }
    }
}
