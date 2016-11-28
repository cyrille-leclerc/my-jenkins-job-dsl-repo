import com.cyrilleleclerc.jenkins.jobs.MavenApplicationJobsBuilder

folder("maven-applications") {
    description("Maven application")
}

folder("maven-applications/spring-projects") {
    description("spring-projects org")
}

new MavenApplicationJobsBuilder(
        applicationName: "spring-petclinic",
        githubOrgName: "spring-projects",
        rootFolder: "maven-applications/spring-projects"
).build(this)

