# Use this file to override properties in the Rakefile that are unique
# to this specific machine. Anything ending in LocalSettings.rb will not
# be checked back into source control.
#
# For more information, see Rakefile or Build/ClientSettings.rb

set(
  # Change this to 'Your Name'
  # Ensure that you have single quotes ' around Your Name
  git_user_name: 'winthropgutmann',

  # Change this to 'your.name@arcadiasolutions.com'
  # You also need single quotes around your email
  git_user_email: 'winthrop.gutmann@arcadiasolitons.com',

  # ssis packages deployed will not use folders based on the tag names
  # developers should uncomment this and set it to false
  # implementation teams should keep the default of true
  # use_ssis_tag_folder?: false,

  # There are two security modes: password and integrated (Windows)
  # You do not need username or password for integrated security.
  # mssql_securitymode: :integrated,
  # mssql_securitymode: :password,
  # mssql_username: "insecure_user",
  # mssql_password: "password123",

  # nightly_job_user: 'YOURPCNAME\Nightly_Job_Account',
  # nightly_job_password: 'p@ssword1',

  uts_username: 'arcadiasolutions',
  uts_password: 'UMTS Arcadia @!~',

  # If you are a QDW developer, and not just managing a client implementation,
  # you can deploy directly from your working copies by uncommenting these three
  # settings and updating them to point to your local development directories.
  # The mpi repo is optional for deploying to your local machine, so those
  # references can be removed.
  #

  deploy_layers: %w(Staging WAREHOUSE), #comment out to deploy web_warehouse_dev
  #
  ###REPOSITORIES
  core_repo: 'C:\Users\Winthrop\Dev\qdw',
  client_repo: 'C:\Users\Winthrop\dev\qdw-client',
  mpi_repo: 'C:\Users\Winthrop\dev\qdw-mpi',

  #
  provisioning_server: "localhost",
  provisioning_env: "dev",
  provisioning_client: "mpi",
  ssis_catalog_password: "Al1gators!AreAliv3",

  cache_dir: 'c:\windows\temp\$(provisioning_client)_WAREHOUSE_$(provisioning_env)',
  overwrite_gac?: true,


###SETTINGS FOR 4.7 VERSION OR EARLIER
# repositories: [:core], #comment out when deploying <=v4.8

###SETTINGS FOR 4.8 VERSION OR LATER
# repositories: [:core, :client], #uncomment for deploy from MPI repo

### SETTING FOR MPI DEPLOY FOR VERSIONS 4.8 OR LATER
repositories: [:core, :mpi, :client], #uncomment for deploy from MPI repo




### Settings for nightly job
### Uncomment to run the nightly job as part of the deploy
  # nightly_job_user: 'WGUTMANN-PC\Winthrop',
  # nightly_job_proxy_name: 'winthrop_proxy',

  # operator_name: 'Winthrop',

  # client_nightlyjob_dir: 'C:\Users\Winthrop\Desktop\nightly_job\Git\Clients\$(provisioning_client)_$(provisioning_env)',
  # client_nightlyjob_target_dir: 'C:\Users\Winthrop\Desktop\nightly_job\Git\Clients\$(provisioning_client)_$(provisioning_env)',

  # informatica_username: "",
  # informatica_password: "",

)
