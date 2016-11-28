import com.cyrilleleclerc.jenkins.jobs.MavenApplicationJobsBuilder

folder("maven-applications") {
    description("Maven application")
}

folder("maven-applications/cyrille-leclerc") {
    description("cyrille-leclerc org")
}

new MavenApplicationJobsBuilder(
        applicationName: "my-spring-boot-app",
        githubOrgName: "cyrille-leclerc",
        rootFolder: "maven-applications/cyrille-leclerc"
).build(this)

new MavenApplicationJobsBuilder(
        applicationName: "spring-petclinic",
        githubOrgName: "cyrille-leclerc",
        rootFolder: "maven-applications/cyrille-leclerc"
).build(this)
