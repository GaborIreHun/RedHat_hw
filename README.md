Prerequisites

Before running the script, ensure you have the following installed:

    - Groovy

    Installation Instructions:
        Windows: Download Groovy and follow the installation guide. (https://groovy.apache.org/download.html)
        macOS: `brew install groovy`
        Linux:
            `curl -s "https://get.sdkman.io" | bash
            source "$HOME/.sdkman/bin/sdkman-init.sh"
            sdk install groovy`

Running the program

    Run: `groovy recentImages.groovy` command.

Implementation Steps

    Assessed the Swagger Base URL:
        Identified the appropriate endpoint to retrieve image information from the rhbk/keycloak-rhel9 repository in the Red Hat registry.
        Selected the /repositories/registry/{registry}/repository/{repository}/images endpoint based on requirements.

    Configured API Parameters:
        Defined the registry as registry.access.redhat.com.
        Set the repository path to rhbk/keycloak-rhel9.

    Initial API Call and Response Assessment:
        Executed a GET request with page_size=1 to inspect the response structure.
        Downloaded the JSON response to identify relevant fields for data extraction.

    Mapped JSON Fields to Requirements:
        contentStream: Mapped to the tags.name field.
        vcsRef: Located within parsed_data.labels as vcs-ref.
        publishedDate: Found under repositories.published_date.
        freshnessGrade: Located in freshness_grades.grade.

    Developed the Initial Groovy Script:
        Created a script to make the API call.
        Parsed the JSON response to extract necessary data fields.

    Optimized Data Retrieval:
        Ordered the query by published_date in descending order to prioritize the most recent images for easier data processing.

    Implemented Data Processing Mechanism:
        Parsed the JSON response.
        Iterated through images and repositories to collect required information.

    Resolved Data Formatting Issues:
        Identified duplication caused by multiple repositories per image.
        Modified the data processing approach to handle multiple tags effectively by introducing an additional level of iteration.

    Eliminated Duplicates:
        Implemented validation to store data in a map (imagesByTag) to ensure each contentStream (tag) is unique, regardless of differing architectures or repository attributes.

    Generated the JSON Report:
        Converted the processed data into a list.
        Serialized the data into a pretty-printed JSON format.

    Implemented JSON File Output:
        Enhanced the script to write the JSON report to a file (keycloak_images_report.json) for persistent storage and future reference.