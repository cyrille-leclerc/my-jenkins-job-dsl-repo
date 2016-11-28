

def applicationName = inputTextParameter(
        label:"Application Name",
        maxLength:50,
        tip:"The name of your application",
        mandatory:true)

def githubOrgName = inputTextParameter(
        label:"GitHub Org Name",
        maxLength:50,
        tip:"The name of your GitHub Org",
        mandatory:true)

new  createApplication(applicationName, githubOrgName).build(this)

