import com.cyrilleleclerc.jenkins.jobs.MavenApplicationJobsBuilder
import javaposse.jobdsl.dsl.Job

println "before create root folder"
folder("maven-applications") {
    description("Maven application")
}

println "before MavenApplicationJobsBuilder"

new MavenApplicationJobsBuilder(
        applicationName: "spring-petclinic",
        githubOrgName: "spring-projects",
        rootFolder: "maven-applications"
).build(this)

