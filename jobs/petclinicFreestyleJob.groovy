String basePath = 'job-dsl-test'

folder(basePath) {
    description 'This example shows basic folder/job creation.'
}


freeStyleJob("$basePath/petclinic-freestyle-by-job-dsl") {
    scm {
        github('spring-projects/spring-petclinic')
    }
    triggers {
        scm 'H/5 * * * *'
    }
    steps {
        shell('$WORKSPACE/mvnw clean package')
    }
}