String basePath = 'job-dsl-test'

folder(basePath) {
    description 'This example shows basic folder/job creation.'
}

pipelineJob("$basePath/petclinic-pipeline-by-job-dsl") {
  definition {
    cps {
      sandbox()
      script(readFileFromWorkspace('src/main/groovy/com/cyrilleleclerc/jenkins/jobs/petclinic-pipeline-script.groovy'))
    }
  }
}
